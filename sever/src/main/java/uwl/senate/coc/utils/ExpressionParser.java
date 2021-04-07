package uwl.senate.coc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ExpressionParser {
	private StringTokenizer tok;
	private List<String> tokens;
	private int next = 0;

	public ExpressionParser( String txt ) {
		//定界符为'（'和'）'
		this.tok = new StringTokenizer( txt, "() ", true);
		fullyParse();
	}

	private void fullyParse() {
		this.tokens = new ArrayList<>();
		while( this.tok.hasMoreElements() ) {
			String next = this.tok.nextToken();
			if( !isSpace( next ) ) {
				this.tokens.add( next );
			}
		}
	}
	
	private boolean isSpace(String t) {
		return " ".equals(t);
	}

	public String peek( int ahead ) {
		int index = this.next + ahead;
		if( index < this.tokens.size() ) {
			return this.tokens.get(index);
		} else {
			return "";
		}
	}
	
	public String nextToken() {
		if( !hasNext() ) throw new NoSuchElementException();
		return this.tokens.get( this.next++ );
	}
	
	public boolean hasNext() {
		return this.next < this.tokens.size();
	}
	
	public void advance( int i ) {
		this.next += i;
	}	
}
