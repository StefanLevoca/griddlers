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
import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;
import sk.upjs.paz1c.griddlers.persistentna.PolickoHryDao;

class MysqlPolickoHryDaoTest {

	private PolickoHryDao polickoHryDao = DaoFactory.INSTANCE.getPolickoHryDao();
	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();
	private Krizovka krizovka;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	public MysqlPolickoHryDaoTest() {
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
	void ulozitTest1() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setCasRiesenia(1L);
		hra.setZaciatok(LocalDateTime.now());
		hra.setKrizovkaId(idK);
		hra.setPocetTahov(1500);
		hra.setUkoncena(false);
		Long idH = hraDao.ulozit(hra).getId();
		PolickoHry polickoHry = new PolickoHry();
		polickoHry.setIdHry(idH);
		polickoHry.setStav(false);
		polickoHry.setSurX(10);
		polickoHry.setSurY(15);
		polickoHry.setPozadovanyStav(false);
		// vytvorenie
		polickoHry = polickoHryDao.ulozit(polickoHry);
		assertNotNull(polickoHry.getId());
		polickoHryDao.vymazat(idH);
		krizovkaDao.vymazat(idK);
		hraDao.vymazat(idH);
	}

	@Test
	void ulozitTest2() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setCasRiesenia(1L);
		hra.setZaciatok(LocalDateTime.now());
		hra.setKrizovkaId(idK);
		hra.setPocetTahov(1500);
		hra.setUkoncena(false);
		Long idH = hraDao.ulozit(hra).getId();
		PolickoHry pol1 = new PolickoHry(null, 10, 4, true);
		pol1.setIdHry(idH);
		PolickoHry pol2 = new PolickoHry(true, 5, 6, false);
		pol2.setIdHry(idH);
		List<PolickoHry> polickaHry = new ArrayList<>();
		polickaHry.add(pol1);
		polickaHry.add(pol2);
		List<PolickoHry> policka = polickoHryDao.ulozit(polickaHry);
		assertTrue(policka.size() == 2);
		krizovkaDao.vymazat(idK);
		hraDao.vymazat(idH);
	}

	@Test
	void getPodlaHraIdTest() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setCasRiesenia(1L);
		hra.setZaciatok(LocalDateTime.now());
		hra.setKrizovkaId(idK);
		hra.setPocetTahov(1500);
		hra.setUkoncena(false);
		Long idH = hraDao.ulozit(hra).getId();
		List<PolickoHry> polickaHryNaUlozenie = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			PolickoHry policko = new PolickoHry(null, i, i, false);
			policko.setIdHry(idH);
			polickaHryNaUlozenie.add(policko);		
		}
		polickoHryDao.ulozit(polickaHryNaUlozenie);
		List<PolickoHry> polickaHry = polickoHryDao.getPodlaHraId(idH);
		
		assertEquals(polickaHryNaUlozenie.size(), polickaHry.size());
		hraDao.vymazat(idH);
		krizovkaDao.vymazat(idK);
		polickoHryDao.vymazat(idH);
	}

	@Test
	void vymazatTest() {
		Long idK = krizovkaDao.ulozit(krizovka).getId();
		Hra hra = new Hra();
		hra.setCasRiesenia(1L);
		hra.setZaciatok(LocalDateTime.now());
		hra.setKrizovkaId(idK);
		hra.setPocetTahov(1500);
		hra.setUkoncena(false);
		Long idH = hraDao.ulozit(hra).getId();
		PolickoHry polickoHry = new PolickoHry(null, 10, 5, true);
		polickoHry.setIdHry(idH);
		polickoHryDao.ulozit(polickoHry);
		int velkostPred = polickoHryDao.getPodlaHraId(idH).size();
		polickoHryDao.vymazat(idH);
		int velkostPo = polickoHryDao.getPodlaHraId(idH).size();
		assertTrue(velkostPred > velkostPo);
		
		hraDao.vymazat(idH);
		krizovkaDao.vymazat(idK);
	}
}
