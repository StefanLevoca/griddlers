package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Obdobie;

public interface HraDao {
	public List<Hra> getVsetky();

	Hra ulozit(Hra hra);

	void vymazat(long hra_id);

	public List<Hra> getPodlaObdobia(Obdobie obdobie);

	
}