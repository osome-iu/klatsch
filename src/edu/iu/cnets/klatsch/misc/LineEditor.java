package edu.iu.cnets.klatsch.misc;

import java.io.IOException;
import java.io.Reader;


/**
 * This class takes care of line editing in the console version of the interpreter.
 * 
 * Note that it's not capable of making standard input unbuffered on its own -- that's
 * why the klatsch wrapper calls 'stty -icanon' before firing up Java.
 */
public class LineEditor
{
	/** our source of data */
	Reader  in;
	
	/** the prompt to use for the hapless user */
	String  prompt;
	
	/** true to suppress VT100 escape codes */
	boolean noEscape;

	/** the history associated with the editor */
	History history;
	
	/** our current line number of input */
	int     lineNumber;
	
	
	
	/**
	 * Initialize with a source of character data, a prompt, and the noEscape flag.
	 * 
	 * @param in        the character-based data source
	 * @param prompt    the prompt
	 * @param noEscape  suppress VT100 escape codes?
	 */
	public LineEditor(Reader in, String prompt, boolean noEscape)
	{
		this.in         = in;
		this.prompt     = prompt;
		this.noEscape   = noEscape;
		this.history    = new History();
		this.lineNumber = 1;
	}
	
	
	/**
	 * Reads another line of input from our data source.  The returned string does not have the
	 * trailing newline attached.
	 * 
	 * @return the next line of input
	 * @throws IOException if a full line could not be read
	 */
	public String readLine()
	throws IOException
	{
		StringBuffer buffer = new StringBuffer();
		int          point  = 0;
		int          ch;

		System.out.println();
		redraw(buffer, point);
		
		do {
			ch = in.read();
			switch (ch) {

				// ^A: beginning of line
				case 0x01:
					point = 0;
					redraw(buffer, point);
					break;
					
				// ^D: EOF
				case 0x04:
					System.out.println();
					System.out.println("End of input");
					System.exit(0);
					
			  // ^E: end of line
				case 0x05:
					point = buffer.length();
					redraw(buffer, point);
					break;
					
				// ^K: erase to end of line
				case 0x0b:
					buffer.delete(point, buffer.length());
					redraw(buffer, point);
					break;
					
				// ^L: redraw
				case 0x0c:
					redraw(buffer, point);
					break;
				
				// backspace and DEL: erase previous character
				case 0x08: case 0x7f:
					if (point > 0) {
						buffer.deleteCharAt(--point);
						redraw(buffer, point);
					}
					break;
					
				// CR + LF: finish input
				case 0x0a: case 0x0d:
					break;
					
				// ESC: escape code -- read further
				case 0x1b:
					ch = in.read();  if (ch != '[') break;
					ch = in.read();
					switch (ch) {

						// cursor up
						case 'A':
							history.prev();
							buffer = new StringBuffer(history.get());
							point  = buffer.length();
							redraw(buffer, point);
							break;
							
						// cursor down
						case 'B':
							history.next();
							buffer = new StringBuffer(history.get());
							point  = buffer.length();
							redraw(buffer, point);
							break;
							
						// cursor right
						case 'C':
							if (point < buffer.length())
								redraw(buffer, ++point);
							break;

						// cursor left
						case 'D':
							if (point > 0)
								redraw(buffer, --point);
							break;
					}
					break;
					
			  // default: insert a character
				default:
					if (point == buffer.length()) {
						buffer.append((char) ch);
						++point;
						System.out.print((char) ch);
						System.out.flush();
					} else {
						buffer.insert(point++, (char) ch);
						redraw(buffer, point);
					}
					break;
			}
		} while (ch != '\n');

		System.out.println();
		
		++lineNumber;
		String line = buffer.toString();
		history.add(line);
		history.reset();
		return line;
	}
		
		
	void redraw(StringBuffer buffer, int point)
	{
		String msg = String.format(prompt, lineNumber);
		
		if (noEscape)
			System.out.print(msg + buffer.toString());
		else {
			System.out.print(VT100.moveStart() + VT100.clearEol());
			System.out.print(VT100.textBold()  + msg + VT100.textNormal());
			System.out.print(buffer.toString());
			System.out.print(VT100.moveColumn(msg.length() + point + 1));
			System.out.flush();
		}
	}
}
