package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Narocnost;

public interface KrizovkaDao {

	public List<Krizovka> getVsetky();
	
	public List<Krizovka> getPodlaNarocnosti(Narocnost narocnost);

	Krizovka ulozit(Krizovka krizovka);

	void vymazat(long krizovka_id);
	
}