package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Obdobie;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;

class MysqlHraDaoTest {

	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();

	@Test
	void getVsetkyTest() {
		List<Hra> zoznam = hraDao.getVsetky();
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void ulozitTest() {
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(4L);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52));
		hra.setPoslednyMedzicas(LocalDateTime.of(2018, 11, 18, 18, 59));
		hra.setKoniec(LocalDateTime.of(2018, 11, 18, 19, 52));
		hra.setKrizovkaId(34L);
		hraDao.ulozit(hra);
		assertNotNull(hra.getId());
	}

	@Test

	void ulozitTest2() {
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(34L);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52, 20));
		hra.setPoslednyMedzicas(LocalDateTime.of(2018, 11, 18, 14, 52, 20));
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
	}

	@Test
	void vymazatTest() {
		Hra hra = new Hra();
		hra.setPocetTahov(50);
		hra.setKrizovkaId(34L);
		hra.setCasRiesenia(60);
		hra.setUkoncena(true);
		hra.setZaciatok(LocalDateTime.of(2018, 11, 18, 18, 52));
		hra.setPoslednyMedzicas(LocalDateTime.of(2018, 11, 18, 18, 55, 5));
		hra.setKoniec(LocalDateTime.of(2018, 11, 18, 19, 52));
		hraDao.ulozit(hra);
		Long id = hra.getId();
		hraDao.vymazat(id);
		List<Hra> hry = hraDao.getVsetky();
		for (Hra hra1 : hry) {
			assertNotEquals(id, hra1.getId());
		}
	}

	@Test
	void getPodlaObdobiaTest1() {
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.DEN);
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void getPodlaObdobiaTest2() {
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.TYZDEN);
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void getPodlaObdobiaTest3() {
		List<Hra> zoznam = hraDao.getPodlaObdobia(Obdobie.MESIAC);
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}
}
