/**
 * 
 */
package bxt.unilectures.vorkurs.visitenkarte;

/**
 * Generiert Visitenkarten
 * @author bxt
 */
public class Visitenkarte {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CliInputFactory builder= new CliInputFactory();
		Kontaktdaten k=builder.create(Kontaktdaten.class);
		KontaktdatenRenderer r= new KontaktdatenRenderer();
		r.render(k);
	}

}