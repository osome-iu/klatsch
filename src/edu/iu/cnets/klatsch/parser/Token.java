package edu.iu.cnets.klatsch.parser;


/**
 * This class represents a single token within the Klatsch language.
 */
class Token
{
	/** any strings defined here are automatically recognized as language keywords */
	static final String[] KEYWORDS  = { "BEGIN", "DO", "ELSE", "END", "FOR", "IF", "IN", "PROC", "THEN", "WHILE" };

	public enum Type { EOF,      AND,    ASSIGN,   COLON,  COMMA,
										 COMMENT,  DIVIDE, DOT,      EQ,     GEQ,
										 GT,       ID,     KEYWORD,  LEQ,    LBRACE,
										 LBRACKET, LPAREN, LT,       MINUS,  MOD,
										 NEQ,      NOT,    NUMBER,   OR,     PLUS,
										 POWER,    RBRACE, RBRACKET, RPAREN, SEMICOLON,
										 STRING,   TIMES };
	  
	Type   type;   // the type of the token
	Object value;  // the value of the token (if any)
	  

	/**
	 * Initializes a new token of a type that does not take a value.
	 * 
	 * @param type  the type of the token
	 */
	Token(Type type)
	{
		this.type  = type;
		this.value = null;
	}
	  	
	 
	/**
	 * Initializes a new token of a type that does take a value.
	 * 
	 * @param type   the type of the token
	 * @param value  the value of the token
	 */
	public Token(Type type, Object value)
	{
		this.type  = type;
		this.value = value;
		
		// promote ID tokens if possible
		if (type == Type.ID) {
			
			String id = ((String) value).toUpperCase();
	      
			// see if it's a keyword
			for (String s : KEYWORDS)
				if (id.equals(s)) {
					this.type  = Type.KEYWORD;
					this.value = id;
					return;
	      }

			// if this is an actual ID, force it into lowercase
	    if (type == Type.ID)
	    	this.value = ((String) value).toLowerCase();
	  }
	}


	/**
	 * Returns the numeric value of the token.
	 *
	 * @return the floating-point value
	 */
	double numberValue()
	{	
		return ((Double) value).doubleValue();
	}
	  
	  
	/**
	 * Determine whether this token is a keyword of the given type.
	 * 
	 * @param keyword  the keyword to check
	 * @return true on a match, false otherwise
	 */
	boolean isKeyword(String keyword)
	{
		return (type == Type.KEYWORD) && ((String) value).equals(keyword);
	}
	  
	  
	/**
	 * Determine whether this token is of the given type.
	 * 
	 * @param type  the type to check
	 * @return true on a match, false otherwise
	 */
	boolean isType(Type type)
	{
		return (this.type == type);
	}
	  

	/**
	 * Returns the string value of the token.
	 * 
	 * @return the string value
	 */
  String stringValue()
  {
  	return (String) value;
	}

	  
  /**
   * Renders this token as a debugging string that identifies the type
   * of the token.
   * 
   * @return the token represented as a string
   */
	String toDebugString()
	{
    switch (type) {
      case EOF:        return "";
      case AND:        return "<AND>";
      case ASSIGN:     return "<ASSIGN>";
      case COLON:      return "<COLON>";
      case COMMA:      return "<COMMA>";
      case COMMENT:    return "<COMMENT>";
      case DIVIDE:     return "<DIVIDE>";
      case DOT:        return "<DOT>";
      case EQ:         return "<EQ>";
      case GEQ:        return "<GEQ>";
      case GT:         return "<GT>";
      case ID:         return "<ID:" + ((String) value) + ">";
      case KEYWORD:    return "<" + ((String) value) + ">";
      case LEQ:        return "<LEQ>";
      case LBRACE:     return "<LBRACE>";
      case LBRACKET:   return "<LBRACKET>";
      case LPAREN:     return "<LPAREN>";
      case LT:         return "<LT>";
      case MINUS:      return "<MINUS>";
      case MOD:        return "<MOD>";
      case NEQ:        return "<NEQ>";
      case NOT:        return "<NOT>";
      case NUMBER:     return "<NUMBER:" + ((Double) value).toString() + ">";
      case OR:         return "<OR>";
      case PLUS:       return "<PLUS>";
      case POWER:      return "<POWER>";
      case RBRACE:     return "<RBRACE>";
      case RBRACKET:   return "<RBRACKET>";
      case RPAREN:     return "<RPAREN>";
      case SEMICOLON:  return "<SEMICOLON>";
      case STRING:     return "<STRING:\"" + ((String) value) + "\">";
      case TIMES:      return "<TIMES>";
      default:         return "<ERROR>";
	  }
	}


	/**
	 * Renders this token as a string.
	 * 
	 * @return the token represented as a string
	 */
	public String toString()
	{
		switch (type) {
      case EOF:        return "";
      case AND:        return "&&";
      case ASSIGN:     return "=";
      case COLON:      return ":";
      case COMMA:      return ",";
      case COMMENT:    return "//" + ((String) value);
      case DIVIDE:     return "/";
      case DOT:        return ".";
      case EQ:         return "==";
      case GEQ:        return ">=";
      case GT:         return ">";
      case ID:         return ((String) value).toLowerCase();
      case KEYWORD:    return (String) value;
      case LEQ:        return "<=";
      case LBRACE:     return "{";
      case LBRACKET:   return "[";
      case LPAREN:     return "(";
      case LT:         return "<";
      case MINUS:      return "-";
      case MOD:        return "%";
      case NEQ:        return "!=";
      case NOT:        return "!";
      case NUMBER:     return ((Double) value).toString();
      case OR:         return "||";
      case PLUS:       return "+";
      case POWER:      return "^";
      case RBRACE:     return "}";
      case RBRACKET:   return "]";
      case RPAREN:     return ")";
      case SEMICOLON:  return ";";
      case STRING:     return "\"" + ((String) value) + "\"";
      case TIMES:      return "*";
      default:               return null;
	  }
	}
}
