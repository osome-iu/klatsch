package edu.iu.cnets.klatsch.misc;


/**
 * This is just a simple wrapper for basic VT100 terminal control codes. 
 */
public class VT100
{
	public static final char ESC = '\u001b';
	
	public static String clear        ()             { return ESC + "[2J";                   }
	public static String clearEol     ()             { return ESC + "[K";                    }

	public static String cursorRestore()             { return ESC + "[u";                    }
	public static String cursorSave   ()             { return ESC + "[s";                    }
	
	public static String moveColumn   (int n)        { return ESC + "[" + n + "G";           }
	public static String moveDown     ()             { return ESC + "[B";                    }
	public static String moveDown     (int n)        { return ESC + "[" + n + "B";           }
	public static String moveHome     ()             { return ESC + "[H";                    }
	public static String moveLeft     ()             { return ESC + "[D";                    }
	public static String moveLeft     (int n)        { return ESC + "[" + n + "D";           }
	public static String moveRight    ()             { return ESC + "[C";                    }
	public static String moveRight    (int n)        { return ESC + "[" + n + "C";           }
	public static String moveStart    ()             { return ESC + "[1G";                   }
	public static String moveUp       ()             { return ESC + "[A";                    }
	public static String moveUp       (int n)        { return ESC + "[" + n + "A";           }
	public static String moveYX       (int y, int x) { return ESC + "[" + y + ";" + x + "H"; }
	public static String moveXY       (int x, int y) { return ESC + "[" + y + ";" + x + "H"; }
	
	public static String textBlink    ()             { return ESC + "[5m";                   }
	public static String textBold     ()             { return ESC + "[1m";                   }
	public static String textNormal   ()             { return ESC + "[0m";                   }
	public static String textReverse  ()             { return ESC + "[7m";                   }
	public static String textUnderline()             { return ESC + "[4m";                   }
}
