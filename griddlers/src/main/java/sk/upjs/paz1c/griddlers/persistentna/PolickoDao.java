package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.Policko;

public interface PolickoDao {

	public List<Policko> getPodlaId(Long id);

	void ulozit(List<Policko> policka, Long idKrizovky);

	void vymazat(Long krizovkaId);

}
