package sk.upjs.paz1c.griddlers;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.biznis.RiesenieManager;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

public class RiesenieManagerTest {

	private Krizovka krizovka;
	private RiesenieManager manager;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	public RiesenieManagerTest() {
		krizovka = new Krizovka();
		krizovka.setNazov("abc");
		krizovka.setNarocnost(Narocnost.LAHKA);
		krizovka.setSirka(15);
		krizovka.setVyska(15);

		List<Policko> riesenie = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			riesenie.add(new Policko(true, i, i + 2));
		}
		krizovka.setRiesenie(riesenie);

		List<Legenda> legendaH = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++)
					legendaH.add(new Legenda(true, i, j, i + 5));
		}
		krizovka.setLegendaH(legendaH);

		List<Legenda> legendaL = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++)
					legendaL.add(new Legenda(false, i, j, i + 5));
		}
		krizovka.setLegendaL(legendaL);		
		manager = new RiesenieManager(krizovka);
	}

	@Test
	void inicializujPolickaHryTest() {
		Hra hra = new Hra();
		hra.setPolickaHry(manager.inicializujPolickaHry());
		List<PolickoHry> vypocitane = hra.getPolickaHry();
		List<PolickoHry> ocakavaneDobre = new ArrayList<>();
		List<PolickoHry> ocakavaneZle = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			ocakavaneDobre.add(new PolickoHry(null, i, i + 2, true));
			ocakavaneZle.add(new PolickoHry(null, i, i + 1, true));
		}
		assertEquals(vypocitane, ocakavaneDobre);
		assertNotEquals(ocakavaneZle, vypocitane);
	}
	
	@Test
	void zistiPocetPotrebnychTest1() {
		Long id = krizovkaDao.ulozit(krizovka).getId();
		int vypocitanyPocetH = manager.zistiPocetPotrebnych(true);
		int ocakavanyPocetH = 5;
		assertEquals(vypocitanyPocetH, ocakavanyPocetH);
		krizovkaDao.vymazat(id);
	}
	
	@Test
	void zistiPocetPotrebnychTest2() {
		Long id = krizovkaDao.ulozit(krizovka).getId();
		int vypocitanyPocetL = manager.zistiPocetPotrebnych(false);
		int ocakavanyPocetL = 6;
		assertEquals(vypocitanyPocetL, ocakavanyPocetL);
		krizovkaDao.vymazat(id);
	}
	
	@Test
	void overRiesenieTest() {
		List<PolickoHry> riesenie = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			riesenie.add(new PolickoHry(true, i, i, true));
			riesenie.add(new PolickoHry(null, i + 10, i + 10, false));
		}
		boolean overenie = manager.overRiesenie(riesenie);
		assertTrue(overenie);
	}
	
	@Test
	void novyCasRieseniaTest() {
		Long staryCasRiesenia = 10000L;
		Hra hra = new Hra();
		hra.setMedzicas(LocalDateTime.of(2018, 12, 14, 11, 00));
		hra.setCasRiesenia(staryCasRiesenia);
		Long novyCasRiesenia = manager.novyCasRiesenia(hra);
		assertTrue(staryCasRiesenia < novyCasRiesenia);
	}
}
