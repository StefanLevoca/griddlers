package sk.upjs.paz1c.griddlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import sk.upjs.paz1c.griddlers.biznis.RiesenieVyberManager;
import sk.upjs.paz1c.griddlers.biznis.StatistikaManager;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

public class StatistikaManagerTest {

	private Krizovka k1, k2, k3, k4;
	private Hra hra1, hra2, hra3, hra4;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();
	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();
	private RiesenieVyberManager riesenieManager;
	private StatistikaManager statistikaManager;

	public StatistikaManagerTest() {
		riesenieManager = new RiesenieVyberManager();
		statistikaManager = new StatistikaManager();

		// Krizovky
		k1 = new Krizovka();
		k2 = new Krizovka();
		k3 = new Krizovka();
		k4 = new Krizovka();

		k1.setNazov("abc");
		k1.setNarocnost(Narocnost.LAHKA);
		k1.setSirka(15);
		k1.setVyska(15);

		k2.setNazov("abc");
		k2.setNarocnost(Narocnost.STREDNA);
		k2.setSirka(15);
		k2.setVyska(15);

		k3.setNazov("abc");
		k3.setNarocnost(Narocnost.STREDNA);
		k3.setSirka(15);
		k3.setVyska(15);

		k4.setNazov("abc");
		k4.setNarocnost(Narocnost.TAZKA);
		k4.setSirka(15);
		k4.setVyska(15);
		List<Policko> riesenie = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			riesenie.add(new Policko(true, i, i + 2));
		}
		k1.setRiesenie(riesenie);
		k2.setRiesenie(riesenie);
		k3.setRiesenie(riesenie);
		k4.setRiesenie(riesenie);
		List<Legenda> legendaH = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++)
				legendaH.add(new Legenda(true, i, j, i + 5));
		}
		k1.setLegendaH(legendaH);
		k2.setLegendaH(legendaH);
		k3.setLegendaH(legendaH);
		k4.setLegendaH(legendaH);

		List<Legenda> legendaL = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++)
				legendaL.add(new Legenda(false, i, j, i + 5));
		}
		k1.setLegendaL(legendaL);
		k2.setLegendaL(legendaL);
		k3.setLegendaL(legendaL);
		k4.setLegendaL(legendaL);

		// Hry
		hra1 = new Hra();
		hra1.setPocetTahov(10);
		hra1.setCasRiesenia(100L);
		hra1.setUkoncena(true);
		hra1.setZaciatok(LocalDateTime.of(2018, 1, 14, 14, 00));
		hra1.setMedzicas(LocalDateTime.of(2018, 1, 14, 16, 00));
		hra1.setKoniec(LocalDateTime.now());

		hra2 = new Hra();
		hra2.setPocetTahov(20);
		hra2.setCasRiesenia(200L);
		hra2.setUkoncena(true);
		hra2.setZaciatok(LocalDateTime.of(2018, 1, 14, 15, 00));
		hra2.setMedzicas(LocalDateTime.of(2018, 1, 14, 17, 00));
		hra2.setKoniec(LocalDateTime.now().minusDays(2));

		hra3 = new Hra();
		hra3.setPocetTahov(30);
		hra3.setCasRiesenia(300L);
		hra3.setUkoncena(true);
		hra3.setZaciatok(LocalDateTime.of(2018, 1, 14, 16, 00));
		hra3.setMedzicas(LocalDateTime.of(2018, 1, 14, 18, 00));
		hra3.setKoniec(LocalDateTime.now().minusWeeks(2));

		hra4 = new Hra();
		//hra4.setId(4L);
		hra4.setPocetTahov(40);
		hra4.setCasRiesenia(400L);
		hra4.setUkoncena(true);
		hra4.setZaciatok(LocalDateTime.of(2018, 1, 14, 17, 00));
		hra4.setMedzicas(LocalDateTime.of(2018, 1, 14, 19, 00));
		hra4.setKoniec(LocalDateTime.now().minusMonths(2));
	}

	@Test
	void formatujCasTest1() {
		String formatovane = statistikaManager.formatujCas(60);
		assertEquals("0:1:0", formatovane);
	}

	@Test
	void formatujCasTest2() {
		String formatovane = statistikaManager.formatujCas(10);
		assertEquals("0:0:10", formatovane);
	}

	@Test
	void formatujCasTest3() {
		String formatovane = statistikaManager.formatujCas(3600);
		System.out.println(formatovane);
		assertEquals("1:0:0", formatovane);
	}

	@Test
	void formatujCasTest4() {
		String formatovane = statistikaManager.formatujCas(45896);
		System.out.println(formatovane);
		assertEquals("12:44:56", formatovane);
	}

	@Test
	void zmenHryTest1() {
		Long id1k = krizovkaDao.ulozit(k1).getId();
		Long id2k = krizovkaDao.ulozit(k2).getId();
		Long id3k = krizovkaDao.ulozit(k3).getId();
		Long id4k = krizovkaDao.ulozit(k4).getId();

		Obdobie obdobie = Obdobie.DEN;
		ObservableList<Hra> hryPred = statistikaManager.zmenHry(obdobie);
		int velkostPred = hryPred.size();
		
		
		hra1.setKrizovkaId(id1k);
		hra2.setKrizovkaId(id2k);
		hra3.setKrizovkaId(id3k);
		hra4.setKrizovkaId(id4k);

		Long id1h = hraDao.ulozit(hra1).getId();
		Long id2h = hraDao.ulozit(hra2).getId();
		Long id3h = hraDao.ulozit(hra3).getId();
		Long id4h = hraDao.ulozit(hra4).getId();
		
		ObservableList<Hra> hryPo = statistikaManager.zmenHry(obdobie);
		int velkostPo = hryPo.size();
		
		assertEquals(velkostPred, velkostPo - 1);

		
		hraDao.vymazat(id1h);
		hraDao.vymazat(id2h);
		hraDao.vymazat(id3h);
		hraDao.vymazat(id4h);
		
		krizovkaDao.vymazat(id1k);
		krizovkaDao.vymazat(id2k);
		krizovkaDao.vymazat(id3k);
		krizovkaDao.vymazat(id4k);

		
	}

	@Test
	void zmenHryTest2() {
		Long id1k = krizovkaDao.ulozit(k1).getId();
		Long id2k = krizovkaDao.ulozit(k2).getId();
		Long id3k = krizovkaDao.ulozit(k3).getId();
		Long id4k = krizovkaDao.ulozit(k4).getId();

		Obdobie obdobie = Obdobie.TYZDEN;
		ObservableList<Hra> hryPred = statistikaManager.zmenHry(obdobie);
		int velkostPred = hryPred.size();
		
		hra1.setKrizovkaId(id1k);
		hra2.setKrizovkaId(id2k);
		hra3.setKrizovkaId(id3k);
		hra4.setKrizovkaId(id4k);

		Long id1h = hraDao.ulozit(hra1).getId();
		Long id2h = hraDao.ulozit(hra2).getId();
		Long id3h = hraDao.ulozit(hra3).getId();
		Long id4h = hraDao.ulozit(hra4).getId();
		
		ObservableList<Hra> hryPo = statistikaManager.zmenHry(obdobie);
		int velkostPo = hryPo.size();

		assertEquals(velkostPred, velkostPo - 2);
		

		hraDao.vymazat(id1h);
		hraDao.vymazat(id2h);
		hraDao.vymazat(id3h);
		hraDao.vymazat(id4h);
		
		krizovkaDao.vymazat(id1k);
		krizovkaDao.vymazat(id2k);
		krizovkaDao.vymazat(id3k);
		krizovkaDao.vymazat(id4k);

		
	}

	@Test
	void zmenHryTest3() {
		Long id1k = krizovkaDao.ulozit(k1).getId();
		Long id2k = krizovkaDao.ulozit(k2).getId();
		Long id3k = krizovkaDao.ulozit(k3).getId();
		Long id4k = krizovkaDao.ulozit(k4).getId();

		hra1.setKrizovkaId(id1k);
		hra2.setKrizovkaId(id2k);
		hra3.setKrizovkaId(id3k);
		hra4.setKrizovkaId(id4k);
		
		Obdobie obdobie = Obdobie.MESIAC;
		ObservableList<Hra> hryPred = statistikaManager.zmenHry(obdobie);
		int velkostPred = hryPred.size();

		Long id1h = hraDao.ulozit(hra1).getId();
		Long id2h = hraDao.ulozit(hra2).getId();
		Long id3h = hraDao.ulozit(hra3).getId();
		Long id4h = hraDao.ulozit(hra4).getId();

		ObservableList<Hra> hryPo = statistikaManager.zmenHry(obdobie);
		int velkostPo = hryPo.size();
		
		assertEquals(velkostPred, velkostPo - 3);

		
		hraDao.vymazat(id1h);
		hraDao.vymazat(id2h);
		hraDao.vymazat(id3h);
		hraDao.vymazat(id4h);
		
		krizovkaDao.vymazat(id1k);
		krizovkaDao.vymazat(id2k);
		krizovkaDao.vymazat(id3k);
		krizovkaDao.vymazat(id4k);

		
	}

	@Test
	void zmenHryTest4() {
		Long id1k = krizovkaDao.ulozit(k1).getId();
		Long id2k = krizovkaDao.ulozit(k2).getId();
		Long id3k = krizovkaDao.ulozit(k3).getId();
		Long id4k = krizovkaDao.ulozit(k4).getId();

		hra1.setKrizovkaId(id1k);
		hra2.setKrizovkaId(id2k);
		hra3.setKrizovkaId(id3k);
		hra4.setKrizovkaId(id4k);

		Obdobie obdobie = Obdobie.VSETKY;
		ObservableList<Hra> hryPred = statistikaManager.zmenHry(obdobie);
		int velkostPred = hryPred.size();

		
		Long id1h = hraDao.ulozit(hra1).getId();
		Long id2h = hraDao.ulozit(hra2).getId();
		Long id3h = hraDao.ulozit(hra3).getId();
		Long id4h = hraDao.ulozit(hra4).getId();

		ObservableList<Hra> hryPo = statistikaManager.zmenHry(obdobie);
		int velkostPo = hryPo.size();
		
		assertEquals(velkostPred, velkostPo - 4);

		
		hraDao.vymazat(id1h);
		hraDao.vymazat(id2h);
		hraDao.vymazat(id3h);
		hraDao.vymazat(id4h);
		
		krizovkaDao.vymazat(id1k);
		krizovkaDao.vymazat(id2k);
		krizovkaDao.vymazat(id3k);
		krizovkaDao.vymazat(id4k);

		
	}
}
