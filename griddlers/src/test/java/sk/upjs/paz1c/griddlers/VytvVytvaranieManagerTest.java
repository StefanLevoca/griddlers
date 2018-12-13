package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.biznis.VytvVytvaranieManager;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;

class VytvVytvaranieManagerTest {

	private VytvVytvaranieManager manager;
	private Krizovka krizovka;

	public VytvVytvaranieManagerTest() {
		krizovka = new Krizovka();
		List<Policko> riesenie = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				riesenie.add(new Policko(true, i, 0));
				riesenie.add(new Policko(true, i, 1));
				riesenie.add(new Policko(true, i, 2));
			} else {
				riesenie.add(new Policko(false, i, 0));
				riesenie.add(new Policko(false, i, 1));
				riesenie.add(new Policko(true, i, 2));
			}
		}
		krizovka.setRiesenie(riesenie);
		krizovka.setVyska(3);
		krizovka.setSirka(10);
		manager = new VytvVytvaranieManager(krizovka);
	}

	@Test
	void testMozeUlozit1() {

		boolean vypocitanaHodnota = manager.mozeUlozit();
		boolean ocakavanaHodnota = true;
		assertEquals(ocakavanaHodnota, vypocitanaHodnota);
	}

	@Test
	void testMozeUlozit2() {
		List<Policko> riesenie = krizovka.getRiesenie();
		riesenie.get(riesenie.size() - 1).setStav(false);
		boolean vypocitanaHodnota = manager.mozeUlozit();
		boolean ocakavanaHodnota = false;
		assertEquals(ocakavanaHodnota, vypocitanaHodnota);
	}

	@Test
	void testVytvorLegenduH() {
		List<Legenda> legendaHVypocitana = manager.vytvorLegenduH();
		List<Legenda> legendaHOcakavana = new ArrayList<>();
		Legenda legenda;
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				legenda = new Legenda(true, i, 0, 3);
			} else {
				legenda = new Legenda(true, i, 0, 1);
			}
			legendaHOcakavana.add(legenda);
		}
		assertEquals(legendaHOcakavana, legendaHVypocitana);
	}

	@Test
	void testVytvorLegenduL() {
		List<Legenda> legendaLVypocitana = manager.vytvorLegenduL();
		List<Legenda> legendaLOcakavana = new ArrayList<>();
		Legenda legenda;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				legenda = new Legenda(false, i, j, 1);
				legendaLOcakavana.add(legenda);
			}
		}
		legendaLOcakavana.add(new Legenda(false, 2, 0, 10));
		assertEquals(legendaLOcakavana, legendaLVypocitana);
	}
}
