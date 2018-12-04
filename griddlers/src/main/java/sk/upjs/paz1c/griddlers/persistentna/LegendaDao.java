package sk.upjs.paz1c.griddlers.persistentna;

import java.util.List;

import sk.upjs.paz1c.griddlers.entity.Legenda;

public interface LegendaDao {

	List<Legenda> getHornaPodlaId(Long id);

	List<Legenda> getLavaPodlaId(Long id);

	void ulozit(List<Legenda> legenda, Long idKrizovky);

	void vymazat(long krizovka_id);

}