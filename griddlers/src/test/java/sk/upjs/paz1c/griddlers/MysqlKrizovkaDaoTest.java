package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Narocnost;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

class MysqlKrizovkaDaoTest {

	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	@Test
	void getVsetkyTest() {
		List<Krizovka> zoznam = krizovkaDao.getVsetky();
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void ulozitTest() {
		Krizovka krizovka = new Krizovka();
		krizovka.setNarocnost(Narocnost.LAHKA);
		krizovka.setNazov("Lahka krizovka");
		krizovka.setSirka(15);
		krizovka.setVyska(15);
		List<Policko> riesenie = new ArrayList<>();
		riesenie.add(new Policko(true, 8,9));
		krizovka.setRiesenie(riesenie);
		List<Legenda> legendaH = new ArrayList<>();
		List<Legenda> legendaL = new ArrayList<>();
		legendaH.add(new Legenda(true, 1,1,2));
		legendaL.add(new Legenda(false, 1,1,4));
		krizovka.setLegendaH(legendaH);
		krizovka.setLegendaL(legendaL);
		int velkost = krizovkaDao.getVsetky().size();
		krizovkaDao.ulozit(krizovka);
		int velkostNova = krizovkaDao.getVsetky().size();
		assertEquals(velkost + 1, velkostNova);
	}

	@Test
	void vymazatTest() {
		List<Krizovka> vsetky = krizovkaDao.getVsetky();
		int size = vsetky.size();
		System.out.println(size);
		Krizovka krizovka = vsetky.get(0);
		Long id = krizovka.getId();
		krizovkaDao.vymazat(id);
		for (Krizovka k : krizovkaDao.getVsetky()) {
			assertNotEquals(id, k.getId());
		}
	}

	

}
