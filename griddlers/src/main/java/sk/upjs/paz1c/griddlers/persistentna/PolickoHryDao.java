package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;

public interface PolickoHryDao {
	
	List<PolickoHry> getPodlaHraId(Long hraId);

	PolickoHry ulozit(PolickoHry polickoHry);

	void vymazat(Long hraId);

	List<PolickoHry> ulozit(List<PolickoHry> polickaHry);
}
