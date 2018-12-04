package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Narocnost;
import sk.upjs.paz1c.griddlers.entity.Policko;

public class MysqlKrizovkaDao implements KrizovkaDao {

	private JdbcTemplate jdbcTemplate;
	private PolickoDao polickoDao;
	private LegendaDao legendaDao;

	public MysqlKrizovkaDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.polickoDao = DaoFactory.INSTANCE.getPolickoDao();
		this.legendaDao = DaoFactory.INSTANCE.getLegendaDao();
	}

	public List<Krizovka> getVsetky() {
		String sql = "SELECT id, nazov, narocnost, sirka, vyska FROM krizovka";
		return jdbcTemplate.query(sql, new RowMapper<Krizovka>() {
			@Override
			public Krizovka mapRow(ResultSet rs, int rowNum) throws SQLException {
				Krizovka krizovka = new Krizovka();

				krizovka.setId(rs.getLong("id"));
				krizovka.setNazov(rs.getString("nazov"));
				krizovka.setNarocnost(Narocnost.valueOf(rs.getString("narocnost")));
				krizovka.setSirka(rs.getInt("sirka"));
				krizovka.setVyska(rs.getInt("vyska"));
				krizovka.setRiesenie(polickoDao.getPodlaId(krizovka.getId()));
				krizovka.setLegendaH(legendaDao.getHornaPodlaId(krizovka.getId()));
				krizovka.setLegendaL(legendaDao.getLavaPodlaId(krizovka.getId()));

				return krizovka;
			}
		});

	}

	@Override
	public Krizovka ulozit(Krizovka krizovka) {
		if (krizovka == null)
			return null;
		if (krizovka.getId() == null) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("krizovka");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("nazov", "narocnost", "sirka", "vyska");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("nazov", krizovka.getNazov());
			hodnoty.put("narocnost", krizovka.getNarocnost().name());
			hodnoty.put("sirka", krizovka.getSirka());
			hodnoty.put("vyska", krizovka.getVyska());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			krizovka.setId(id);
			List<Legenda> legendaH = krizovka.getLegendaH();
			List<Legenda> legendaL = krizovka.getLegendaL();
			List<Policko> riesenie = krizovka.getRiesenie(); 
			legendaDao.ulozit(legendaH, krizovka.getId());
			legendaDao.ulozit(legendaL, krizovka.getId());
			polickoDao.ulozit(riesenie, krizovka.getId()); 
			
		} else {
			// AKTUALIZACIA
			String sql = "UPDATE krizovka SET nazov = ?, narocnost = ?, sirka = ?, vyska = ? WHERE id = ?";
			jdbcTemplate.update(sql, krizovka.getNazov(), krizovka.getNarocnost().name(), krizovka.getSirka(),
					krizovka.getVyska(), krizovka.getId());
		}
		return krizovka;
	}

	@Override
	public void vymazat(long krizovka_id) {
		String sql = String.format("DELETE FROM krizovka WHERE id = %d", krizovka_id);
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Krizovka> getPodlaNarocnosti(Narocnost narocnost) {
		String sql = "SELECT id, narocnost, sirka, vyska, nazov FROM krizovka WHERE narocnost = ?";
		Object[] parametre = new Object[] {narocnost.name()};
		return jdbcTemplate.query(sql, parametre, new RowMapper<Krizovka>() {

			@Override
			public Krizovka mapRow(ResultSet rs, int row) throws SQLException {
				Krizovka krizovka = new Krizovka();
				krizovka.setId(rs.getLong("id"));
				krizovka.setNarocnost(Narocnost.valueOf(rs.getString("narocnost")));
				krizovka.setSirka(rs.getInt("sirka"));
				krizovka.setVyska(rs.getInt("vyska"));
				krizovka.setNazov(rs.getString("nazov"));
				
				return krizovka;
			}
		});
		
	}

}
