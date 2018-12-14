package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.Obdobie;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;

public interface HraDao {
	public List<Hra> getVsetky();

	Hra ulozit(Hra hra);

	void vymazat(long hraId);

	public List<Hra> getPodlaObdobia(Obdobie obdobie);

	Krizovka getKrizovkaPodlaHraId(Long id);

}