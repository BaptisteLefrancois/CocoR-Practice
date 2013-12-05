

import java.io.*;
import java.util.ArrayList;



public class Parser {
	public static final int _EOF = 0;
	public static final int _ident = 1;
	public static final int _number = 2;
	public static final int _sep = 3;
	public static final int _indic = 4;
	public static final int _lpar = 5;
	public static final int _rpar = 6;
	public static final int _eol = 7;
	public static final int maxT = 12;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	ArrayList<PhoneEntry> phoneEntries = new ArrayList<PhoneEntry>();;


	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void PhoneBook() {
		PhoneEntry entry, entry1;	
		entry = PhoneEntry();
		phoneEntries.add(entry);	
		while (la.kind == 1) {
			entry1 = PhoneEntry();
			phoneEntries.add(entry1);	
		}
	}

	PhoneEntry  PhoneEntry() {
		PhoneEntry  entry;
		entry = new PhoneEntry(); 
		PhoneInfo pi, pi1;	
		Expect(1);
		entry.setLastName(t.val);		
		while (la.kind == 1) {
			Get();
			entry.setLastName(t.val);		
		}
		while (!(la.kind == 0 || la.kind == 3)) {SynErr(13); Get();}
		Expect(3);
		Expect(1);
		entry.setFirstName(t.val); 
		while (la.kind == 1) {
			Get();
			entry.setFirstName(t.val); 
		}
		if (la.kind == 7) {
			Get();
		}
		pi = PhoneInfo();
		entry.getPhones().add(pi); 	
		while (StartOf(1)) {
			while (!(StartOf(2))) {SynErr(14); Get();}
			pi1 = PhoneInfo();
			entry.getPhones().add(pi1);	
		}
		return entry;
	}

	PhoneInfo  PhoneInfo() {
		PhoneInfo  pi;
		pi = new PhoneInfo();
		String pt = "home";	
		while (!(StartOf(2))) {SynErr(15); Get();}
		if (la.kind == 8 || la.kind == 9 || la.kind == 10) {
			pt = PhoneType();
		}
		pi.setType(pt);		
		PhoneNumber(pi);
		return pi;
	}

	String  PhoneType() {
		String  pt;
		pt = "";	
		if (la.kind == 8) {
			Get();
			pt = "home";	
		} else if (la.kind == 9) {
			Get();
			pt = "office";	
		} else if (la.kind == 10) {
			Get();
			pt = "mobile";	
		} else SynErr(16);
		return pt;
	}

	void PhoneNumber(PhoneInfo pi) {
		String cc, ccp;	
		if (la.kind == 4) {
			cc = CountryCode();
			pi.setCountryCode(cc);		
			ccp = CityCodePrefix();
			Expect(2);
			pi.setCityCode(ccp + t.val);
			Expect(2);
			pi.setNumber(t.val);		
			while (la.kind == 2) {
				Get();
				pi.setNumber(t.val);		
			}
			if (la.kind == 7) {
				Get();
			} else if (la.kind == 0) {
				Get();
			} else SynErr(17);
		} else if (la.kind == 2) {
			pi.setCountryCode("+44"); 	
			pi.setCityCode("020");		
			Get();
			if(t.val.substring(0,1).equals("0")) pi.setCityCode(t.val); 
			else pi.setNumber(t.val);	
			while (la.kind == 2) {
				Get();
				pi.setNumber(t.val);		
			}
			if (la.kind == 7) {
				Get();
			} else if (la.kind == 0) {
				Get();
			} else SynErr(18);
		} else SynErr(19);
	}

	String  CountryCode() {
		String  cc;
		Expect(4);
		cc = t.val;		
		Expect(2);
		cc += t.val;	
		return cc;
	}

	String  CityCodePrefix() {
		String  ccp;
		Expect(5);
		Expect(11);
		Expect(6);
		ccp = "(0)";	
		return ccp;
	}



	public ArrayList<PhoneEntry> Parse() {
		la = new Token();
		la.val = "";		
		Get();
		PhoneBook();
		Expect(0);

		return phoneEntries;
	}

	private static final boolean[][] set = {
		{T,x,T,T, T,x,x,x, T,T,T,x, x,x},
		{x,x,T,x, T,x,x,x, T,T,T,x, x,x},
		{T,x,T,x, T,x,x,x, T,T,T,x, x,x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "ident expected"; break;
			case 2: s = "number expected"; break;
			case 3: s = "sep expected"; break;
			case 4: s = "indic expected"; break;
			case 5: s = "lpar expected"; break;
			case 6: s = "rpar expected"; break;
			case 7: s = "eol expected"; break;
			case 8: s = "\"home\" expected"; break;
			case 9: s = "\"office\" expected"; break;
			case 10: s = "\"mobile\" expected"; break;
			case 11: s = "\"0\" expected"; break;
			case 12: s = "??? expected"; break;
			case 13: s = "this symbol not expected in PhoneEntry"; break;
			case 14: s = "this symbol not expected in PhoneEntry"; break;
			case 15: s = "this symbol not expected in PhoneInfo"; break;
			case 16: s = "invalid PhoneType"; break;
			case 17: s = "invalid PhoneNumber"; break;
			case 18: s = "invalid PhoneNumber"; break;
			case 19: s = "invalid PhoneNumber"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
