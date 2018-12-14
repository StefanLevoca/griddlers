package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.Narocnost;
import sk.upjs.paz1c.griddlers.entity.Krizovka;

public interface KrizovkaDao {

	public List<Krizovka> getVsetky();

	public List<Krizovka> getPodlaNarocnosti(Narocnost narocnost);

	Krizovka ulozit(Krizovka krizovka);

	void vymazat(long krizovka_id);

}