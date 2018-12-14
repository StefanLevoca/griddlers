package sk.upjs.paz1c.griddlers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import sk.upjs.paz1c.griddlers.biznis.RiesenieVyberManager;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

public class RiesenieVyberManagerTest {
	
	private Krizovka k1;
	private Krizovka k2;
	private Krizovka k3;
	private Krizovka k4;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();
	private RiesenieVyberManager manager;
	
	public RiesenieVyberManagerTest() {
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
		manager = new RiesenieVyberManager();
			
	}
	
	@Test
	void zmenKrizovkyTest1() {
		Long id1 = krizovkaDao.ulozit(k1).getId();
		Long id2 = krizovkaDao.ulozit(k2).getId();
		Long id3 = krizovkaDao.ulozit(k3).getId();
		Long id4 = krizovkaDao.ulozit(k4).getId();
		
		ObservableList<Krizovka> lahke = manager.zmenKrizovky(Narocnost.LAHKA);
		boolean ok = false;
		for(Krizovka k: lahke) {
			if(id1 == k.getId()) {
				ok = true;
			}
			if(id2 == k.getId() || id3 == k.getId() || id4 == k.getId()) {
				ok = false;
				break;
			}
		}
		assertTrue(ok);
		krizovkaDao.vymazat(id1);
		krizovkaDao.vymazat(id2);
		krizovkaDao.vymazat(id3);
		krizovkaDao.vymazat(id4);
	}
	
	@Test
	void zmenKrizovkyTest2() {
		Long id1 = krizovkaDao.ulozit(k1).getId();
		Long id2 = krizovkaDao.ulozit(k2).getId();
		Long id3 = krizovkaDao.ulozit(k3).getId();
		Long id4 = krizovkaDao.ulozit(k4).getId();
		
		ObservableList<Krizovka> lahke = manager.zmenKrizovky(Narocnost.STREDNA);
		boolean ok2 = false;
		boolean ok3 = false;
		boolean ok = true;
		for(Krizovka k: lahke) {
			if(id2 == k.getId()) {
				ok2 = true;
			}
			if(id3 == k.getId()) {
				ok3 = true;
			}
			if(id1 == k.getId() || id4 == k.getId()) {
				ok = false;
				break;
			}
		}
		assertTrue(ok2 && ok3 && ok);
		krizovkaDao.vymazat(id1);
		krizovkaDao.vymazat(id2);
		krizovkaDao.vymazat(id3);
		krizovkaDao.vymazat(id4);
	}
	
	
}
