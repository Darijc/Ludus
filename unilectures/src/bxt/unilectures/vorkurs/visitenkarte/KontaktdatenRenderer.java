package bxt.unilectures.vorkurs.visitenkarte;

import java.io.PrintStream;

public class KontaktdatenRenderer extends BoxBuilder {
	private PrintStream out=null;
	KontaktdatenRenderer() {
		this(40,System.out);
	}
	KontaktdatenRenderer(int width,PrintStream out) {
		this.width=width;
		this.out=out;
	}
	public void render(Kontaktdaten k) {
		this.outStr=new StringBuilder();
		renderLineBorder();
		renderLineLeftAligned("");
		renderLineLeftAligned(k.getNachname()+", "+k.getVorname());
		renderLineLeftAligned(k.getProfession());
		renderLineLeftAligned("");
		renderLineLeftAligned(k.getStrasse()+" "+k.getHausnummer());
		renderLineLeftAligned(k.getPlz()+" "+k.getWohnort());
		renderLineLeftAligned("");
		renderLineRightAligned("eMail: "+k.getMailadresse()+"");
		renderLineRightAligned("Handy: "+k.getHandynummer().getPhonenumber()+"");
		renderLineLeftAligned("");
		renderLineBorder();
		out.print(outStr);
	}
}