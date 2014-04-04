/*	Denne klassen er en Legepost som er subklassen av Person.
	Laget av Adrian Westlund
	Siste versjon 02-04-2014*/

import java.io.*;

public class Lege extends Person implements Serializable
{
	private static final long serialVersionUID = 1003L;

	String gateadresse, poststed, arbetssted, ePost;
	int postnummer;
	boolean a,b,c;
	//SortedSet<Pasient> Pasientliste;

	public Lege(String fnavn, String enavn, String g, String ps, String as,
				String ep, int pNr)
	{
		super(fnavn, enavn);

		gateadresse = g;
		poststed = ps;
		arbetssted = as;
		ePost = ep;
		postnummer = pNr;
		a = true;
		b = true;
		c = true;
		//Pasientliste = new TreeSet<>();
	}

	public String getGateadresse()
	{
		return gateadresse;
	}
	public String getPoststed()
	{
		return poststed;
	}
	public String getArbetssted()
	{
		return arbetssted;
	}
	public String getEPost()
	{
		return ePost;
	}
	public int getPostnummer()
	{
		return postnummer;
	}
	public boolean getA()
	{
		return a;
	}
	public boolean getB()
	{
		return b;
	}
	public boolean getC()
	{
		return c;
	}
	/*
	public TreeSet<Pasient> getPasientliste()
	{
		return Pasientliste;
	}
	*/

	public void setGateadresse(String g)
	{
		gateadresse = g;
	}
	public void setPoststed(String p)
	{
		poststed = p;
	}
	public void setArbetssted(String as)
	{
		arbetssted = as;
	}
	public void setEPost(String e)
	{
		ePost = e;
	}
	public void setPostnummer(int pNr)
	{
		postnummer = pNr;
	}
	public void setA(boolean nyA)
	{
		a = nyA;
	}
	public void setB(boolean nyB)
	{
		b = nyB;
	}
	public void setC(boolean nyC)
	{
		c = nyC;
	}

	public String toString()
	{
		String ut = super.toString() + "E-post: " + ePost + "\n";
					if(!gateadresse.equals(""))
						ut += "Gateadresse: " + gateadresse + "\n" + postnummer + " " + poststed + "\n";
					if(!arbetssted.equals(""))
		   				ut += "Arbetssted: " + arbetssted + "\n";
		   			ut += "Bevilning: ";
		   			if(a && (b || c))
		   				ut += "A, ";
		   			if(a && !(b || c))
		   				ut += "A.";
		   			if(b && c)
		   				ut += "B, ";
		   			if(b && !c)
		   				ut += "B.";
		   			if(c)
		   				ut += "C.";
		return ut;
	}
}