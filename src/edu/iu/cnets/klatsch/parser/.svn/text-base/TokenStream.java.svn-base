package edu.iu.cnets.klatsch.parser;

import edu.iu.cnets.klatsch.exception.TokenException;


/**
 * This class encapsulates a buffer and makes it possible to read a stream of tokens from it,
 * with one-token lookahead for the parser.  The class is public, but only {@link Parser} really
 * needs to interact with it.
 */
public class TokenStream
{
	/** the buffer we're converting */
	String buffer;
	
	/** the current offset within the buffer */
	int    offset;
	
	/** a buffered token to peek at, for ease of parsing */
	Token  peek;
	  
	 
	/**
	 * Creates a new token stream based on the given string data.
	 * 
	 * @param buffer  the string to tokenize
	 */
	public TokenStream(String buffer)
	{
		this.buffer = buffer;
		this.offset = 0;
		this.peek   = null;
	}
	  
	  
	/**
	 * Determines if the stream is at an expression terminator (EOF)
	 * 
	 * @return true if we've read to the end of a statement
	 */
	boolean atTerminator()
	throws TokenException
	{
		Token token = peek();
		return token.isType(Token.Type.EOF)    || token.isType(Token.Type.COMMA)    ||
				   token.isType(Token.Type.RBRACE) || token.isType(Token.Type.RBRACKET) || token.isType(Token.Type.RPAREN); 
	}
	  

	/**
	 * Determines if the stream is at its end.
	 * 
	 * @return true if EOF and false otherwise
	 */
	boolean eof()
	throws TokenException
	{
		return peek().isType(Token.Type.EOF);
	}
	  

	/**
	 * Returns the next token in the stream.  Use {@link #peek} instead for a
	 * read with no side-effects.
	 * 
	 * @return the next token
	 * @throws TokenException if an error was encountered
	 */
	Token get()
	throws TokenException
	{
		// if we have a buffered token, return that
		if (peek != null) {
			Token token = peek;
			peek        = null;
			return token;
	  }
	    
		// otherwise, actually do some work
		return readToken();
	}
	  
	  
	/**
	 * Returns the next token in the stream without actually advancing the
	 * pointer within the buffer.  Use {@link #get} instead for a read with
	 * side-effects.
	 * 
	 * @return the next token
	 * @throws TokenException if an error was encountered
	 */
	Token peek()
	throws TokenException
	{
		// if we don't have a buffered token, get one
		if (peek == null)
			peek = readToken();
	      
		// return the buffered token
		return peek;
	}
	  
	  
	/**
	 * Reads the next token in the stream, which must be a keyword of the given
	 * type to avoid causing an error.
	 * 
	 * @param keyword  the type of keyword required
	 * @return the next token
	 * @throws TokenException if an error or incorrect type was encountered
	 */
	Token requireKeyword(String keyword)
	throws TokenException
	{
		Token token = get();
		if ((token.type == Token.Type.KEYWORD) && token.stringValue().equals(keyword))
			return token;
	  else
	  	throw new TokenException("Unexpected input '" + token.toString() + "'");
	}
	  
	  
	/**
	 * Reads the next token in the stream, which must match the given type
	 * to avoid causing an error.
	 *  
	 * @param type  the type required
	 * @return the next token
	 * @throws TokenException if an error or incorrect type was encountered
	 */
	Token require(Token.Type type)
	throws TokenException
	{
		Token token = get();
		if (token.type == type)
			return token;
	  else
	  	throw new TokenException("Unexpected input '" + token.toString() + "'");
	}
	  
	  
	/**
	 * Resets the internal state of the token stream so that subsequent reads
	 * will start over from the beginning.
	 */
	void reset()
	{
		offset = 0;
		peek   = null;
	}
	  

	private enum State { INIT,    EOF, ERROR,  AND,     COMMENT,
											 DIVIDE,  DOT, EQ,     GT,      ID,
											 LT,      NOT, NUMBER, NUMBER1, NUMBER2,
											 NUMBER3, OR,  STRING, STRING1 };
	  
	/**
	 * Reads the next token from the buffer and returns it.  If no more tokens are
	 * available, this will be the EOF token.
	 * 
	 * @return the next token
	 * @throws TokenException if an error was encountered
	 */
	private Token readToken()
	throws TokenException
	{
		StringBuffer acc   = new StringBuffer();
		State        state = State.INIT;
		char         ch;
	    
		// operate the FSM to create a token
		while (true) {
	      
			// get a hold of the next character
			ch = (offset >= buffer.length()) ? '\0' : buffer.charAt(offset);
			//System.err.println("ch:'" + ch + "',state='" + state + "' ");
	        
			// dispatch based on the current state
			switch (state) {
	        
			  case INIT:
			  	     if (ch == '\0')                 {                             state = State.EOF;       }
	        else if (Character.isLetter(ch))     { acc.append(ch);  offset++;  state = State.ID;        }
	        else if (ch == '_')                  { acc.append(ch);  offset++;  state = State.ID;        }
          else if (Character.isDigit(ch))      { acc.append(ch);  offset++;  state = State.NUMBER;    }
          else if (ch == '.')                  { acc.append(ch);  offset++;  state = State.DOT;       }
          else if (ch == '&')                  {                  offset++;  state = State.AND;       }
          else if (ch == '=')                  {                  offset++;  state = State.EQ;        }
          else if (ch == ':')                  {                  offset++;  return new Token(Token.Type.COLON);     }
          else if (ch == ',')                  {                  offset++;  return new Token(Token.Type.COMMA);     }
          else if (ch == '/')                  {                  offset++;  state = State.DIVIDE;    }
          else if (ch == '>')                  {                  offset++;  state = State.GT;        }
          else if (ch == '<')                  {                  offset++;  state = State.LT;        }
          else if (ch == '{')                  {                  offset++;  return new Token(Token.Type.LBRACE);    }
          else if (ch == '[')                  {                  offset++;  return new Token(Token.Type.LBRACKET);  }
          else if (ch == '(')                  {                  offset++;  return new Token(Token.Type.LPAREN);    }
          else if (ch == '-')                  {                  offset++;  return new Token(Token.Type.MINUS);     }
          else if (ch == '%')                  {                  offset++;  return new Token(Token.Type.MOD);       }
          else if (ch == '!')                  {                  offset++;  state = State.NOT;       }
          else if (ch == '|')                  {                  offset++;  state = State.OR;        }
          else if (ch == '+')                  {                  offset++;  return new Token(Token.Type.PLUS);      }
          else if (ch == '^')                  {                  offset++;  return new Token(Token.Type.POWER);     }
          else if (ch == '*')                  {                  offset++;  return new Token(Token.Type.TIMES);     }
          else if (ch == '}')                  {                  offset++;  return new Token(Token.Type.RBRACE);    }
          else if (ch == ']')                  {                  offset++;  return new Token(Token.Type.RBRACKET);  }
          else if (ch == ')')                  {                  offset++;  return new Token(Token.Type.RPAREN);    }
          else if (ch == ';')                  {                  offset++;  return new Token(Token.Type.SEMICOLON); }
          else if (ch == '"')                  {                  offset++;  state = State.STRING;    }
          else if (Character.isWhitespace(ch)) {                  offset++;                           }
          else                                 {                             state = State.ERROR;     }
          break;

	      case AND:
	      	     if (ch == '&')                  {                  offset++;  return new Token(Token.Type.AND);       }
	        else                                 {                             state = State.ERROR;     }
	        break;

        case COMMENT:
        	     if (ch == '\0')                 {                             state = State.INIT;      }
        	else if (ch == '\n')                 {                  offset++;  state = State.INIT;      }
        	else                                 {                  offset++;                           }
        	break;

        case DIVIDE:
	      	     if (ch == '/')                  {                  offset++;  state = State.COMMENT;   }
	      	else                                 {                             return new Token(Token.Type.DIVIDE);    }
	      	break;
	      	
	      case EQ:
	      	     if (ch == '=')                  {                  offset++;  return new Token(Token.Type.EQ);        }
	      	else                                 {                             return new Token(Token.Type.ASSIGN);    }
	      	
	      case GT:
               if (ch == '=')                  {                  offset++;  return new Token(Token.Type.GEQ);       }
          else                                 {                             return new Token(Token.Type.GT);        }
   	      
	      case ID:
	             if (Character.isLetter(ch))     { acc.append(ch);  offset++;                           }
	        else if (ch == '_')                  { acc.append(ch);  offset++;                           }
	        else if (Character.isDigit(ch))      { acc.append(ch);  offset++;                           }
	        else                                 return new Token(Token.Type.ID, acc.toString());
	        break;

	      case LT:
	      	     if (ch == '=')                  {                  offset++;  return new Token(Token.Type.LEQ);       }
	      	else                                 {                             return new Token(Token.Type.LT);        }
          
	      case NOT:
	      	     if (ch == '=')                  {                  offset++;  return new Token(Token.Type.NEQ);       }
	      	else                                 {                             return new Token(Token.Type.NOT);       }
	      	
	      case DOT:
	      	     if (Character.isDigit(ch))      {                             state = State.NUMBER1;   }
	      	else                                 return new Token(Token.Type.DOT);
	      	     
	      case NUMBER:
	             if (Character.isDigit(ch))      { acc.append(ch);  offset++;                           }
	        else if (ch == '.')                  { acc.append(ch);  offset++;  state = State.NUMBER1;   }
	        else if ((ch == 'e') || (ch == 'E')) { acc.append(ch);  offset++;  state = State.NUMBER2;   }
	        else                                 return new Token(Token.Type.NUMBER, Double.parseDouble(acc.toString()));
	        break;

	      case NUMBER1:
	      	     if (Character.isDigit(ch))      { acc.append(ch);  offset++;                           }
	      	else if ((ch == 'e') || (ch == 'E')) { acc.append(ch);  offset++;  state = State.NUMBER2;   }
	      	else                                 return new Token(Token.Type.NUMBER, Double.parseDouble(acc.toString()));
	      	break;
	        		
	      case NUMBER2:
	      	     if ((ch == '+') || (ch == '-')) { acc.append(ch);  offset++;  state = State.NUMBER3;   }
	      	else if (Character.isDigit(ch))      { acc.append(ch);  offset++;  state = State.NUMBER3;   }
	       	else                                 {                             state = State.ERROR;     }
	       	break;
	        	     
	      case NUMBER3:
	      	     if (Character.isDigit(ch))      { acc.append(ch);  offset++;                           }
	       	else                                 return new Token(Token.Type.NUMBER, Double.parseDouble(acc.toString()));
	       	break;
	        	
	      case OR:
   	           if (ch == '|')                  {                  offset++;  return new Token(Token.Type.OR);        }
          else                                 {                             state = State.ERROR;     }
          break;
	        	
        case STRING:
	             if (ch == '\0')                 {                             state = State.ERROR;     }
	        else if (ch == '\\')                 {                  offset++;  state = State.STRING1;   }
	        else if (ch == '"')                  {                  offset++;  return new Token(Token.Type.STRING, acc.toString()); }
	        else                                 { acc.append(ch);  offset++;                           }
	        break;
	        
	      case STRING1:
	      	     if (ch == '\\')                 { acc.append(ch);    offset++;  state = State.STRING;  }
	      	else if (ch == '"')                  { acc.append(ch);    offset++;  state = State.STRING;  }
	      	else if (ch == 'n')                  { acc.append('\n');  offset++;  state = State.STRING;  }
	      	else if (ch == 'r')                  { acc.append('\r');  offset++;  state = State.STRING;  }
	      	else if (ch == 't')                  { acc.append('\t');  offset++;  state = State.STRING;  }
	      	else                                 {                               state = State.ERROR;   }
	      	break;

	      case EOF:
	      	return new Token(Token.Type.EOF);
	          
	      case ERROR:
	      	throw new TokenException("Unexpected char '" + ch + "' at column " + offset);
	    }
	  }
	}
}
