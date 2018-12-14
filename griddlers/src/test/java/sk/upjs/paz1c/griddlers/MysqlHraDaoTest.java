package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

class MysqlHraDaoTest {
	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();
	
	private Krizovka krizovka;
	
	public MysqlHraDaoTest() {
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
		
	}

	@Test
	void getVsetkyTest() {
		List<Hra> zoznam = hraDao.getVsetky();
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void ulozitTest() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(idK);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52));
		hra.setKoniec(LocalDateTime.of(2018, 11, 18, 19, 52));
		Long idH = hraDao.ulozit(hra).getId();
		assertNotNull(idH);
		hraDao.vymazat(idH);
		krizovkaDao.vymazat(idK);
	}

	@Test

	void ulozitTest2() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(idK);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52, 20));
		hra.setMedzicas(LocalDateTime.of(2018, 11, 18, 14, 52, 20));
		hra.setKoniec(LocalDateTime.of(2018, 11, 18, 19, 52, 18));
		hraDao.ulozit(hra);
		hra.setPocetTahov(55);
		hraDao.ulozit(hra);
		List<Hra> hry = hraDao.getVsetky();
		for (Hra hra1 : hry) {
			if (hra1.getId() == hra.getId()) {
				assertEquals(55, hra.getPocetTahov());
				hraDao.vymazat(hra1.getId());
				return;
			}
		}
		krizovkaDao.vymazat(idK);
	}

	@Test
	void vymazatTest() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(idK);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52));
		hra.setMedzicas(LocalDateTime.of(2018, 11, 18, 18, 55, 5));
		hra.setKoniec(LocalDateTime.of(2018, 11, 18, 19, 52));
		hraDao.ulozit(hra);
		Long id = hra.getId();
		hraDao.vymazat(id);
		List<Hra> hry = hraDao.getVsetky();
		for (Hra hra1 : hry) {
			assertNotEquals(id, hra1.getId());
		}
		krizovkaDao.vymazat(idK);
	}

	@Test
	void getPodlaObdobiaTest1() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(100);
		hra.setCasRiesenia(199L);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 12, 17, 12, 00));
		hra.setKoniec(LocalDateTime.of(2018, 12, 17, 12, 30));
		hra.setKrizovkaId(idK);
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.DEN);
		Long id = hraDao.ulozit(hra).getId();
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
		hraDao.vymazat(id);
		krizovkaDao.vymazat(idK);
	}

	@Test
	void getPodlaObdobiaTest2() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(100);
		hra.setCasRiesenia(199L);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 12, 15, 12, 00));
		hra.setKoniec(LocalDateTime.of(2018, 12, 15, 12, 30));
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.TYZDEN);
		hra.setKrizovkaId(idK);
		Long id = hraDao.ulozit(hra).getId();
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
		hraDao.vymazat(id);
		krizovkaDao.vymazat(idK);
	}

	@Test
	void getPodlaObdobiaTest3() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(100);
		hra.setCasRiesenia(199L);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 27, 12, 00));
		hra.setKoniec(LocalDateTime.of(2018, 11, 27, 12, 30));
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.MESIAC);
		hra.setKrizovkaId(idK);
		Long id = hraDao.ulozit(hra).getId();

		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
		hraDao.vymazat(id);
		krizovkaDao.vymazat(idK);
	}

	@Test
	void getKrizovkaPodlaHraIdTest() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setPocetTahov(100);
		hra.setCasRiesenia(199L);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 27, 12, 00));
		hra.setKoniec(LocalDateTime.of(2018, 11, 27, 12, 30));
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.MESIAC);
		hra.setKrizovkaId(idK);
		Long hraId = hraDao.ulozit(hra).getId();
		Long id = hraDao.ulozit(hra).getId();
		Krizovka krizovka = hraDao.getKrizovkaPodlaHraId(hraId);
		assertEquals(krizovka.getId(), idK);
		krizovkaDao.vymazat(idK);
	}
}
