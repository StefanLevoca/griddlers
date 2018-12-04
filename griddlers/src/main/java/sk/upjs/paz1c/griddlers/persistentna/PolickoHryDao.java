package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;

public interface PolickoHryDao {
	public List<PolickoHry> getVsetky();

	PolickoHry ulozit(PolickoHry polickoHry);

	void vymazat(long polickoHry_id);
}
