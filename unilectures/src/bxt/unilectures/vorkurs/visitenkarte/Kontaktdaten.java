package bxt.unilectures.vorkurs.visitenkarte;

@CliPrompt("Geben sie nachfolgend die Kontaktdaten ein. ")
public class Kontaktdaten {
	@LanguageGender(LanguageGender.Gender.MALE)
	private String vorname=null;
	@LanguageGender(LanguageGender.Gender.MALE)
	private String nachname=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private String profession=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private String strasse=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private Integer hausnummer=null;
	@LanguageGender(LanguageGender.Gender.MALE)
	private String wohnort=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private Integer plz=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private String mailadresse=null;
	@LanguageGender(LanguageGender.Gender.FEMALE)
	private Phonenumber handynummer=null;
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public Integer getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(Integer hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getWohnort() {
		return wohnort;
	}
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
	public Integer getPlz() {
		return plz;
	}
	public void setPlz(Integer plz) {
		this.plz = plz;
	}
	public String getMailadresse() {
		return mailadresse;
	}
	public void setMailadresse(String mailadresse) {
		this.mailadresse = mailadresse;
	}
	public Phonenumber getHandynummer() {
		return handynummer;
	}
	public void setHandynummer(Phonenumber handynummer) {
		this.handynummer = handynummer;
	}
	
	
}