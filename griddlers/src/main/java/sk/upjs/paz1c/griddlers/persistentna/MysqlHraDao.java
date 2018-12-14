package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.Narocnost;
import sk.upjs.paz1c.griddlers.Obdobie;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;

public class MysqlHraDao implements HraDao {

	private JdbcTemplate jdbcTemplate;
	protected PolickoDao polickoDao = DaoFactory.INSTANCE.getPolickoDao();
	protected LegendaDao legendaDao = DaoFactory.INSTANCE.getLegendaDao();

	public MysqlHraDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Hra> getVsetky() {
		String sql = "SELECT id, pocet_tahov, cas_riesenia, ukoncena, zaciatok, koniec, krizovka_id" + " FROM hra";
		return jdbcTemplate.query(sql, new RowMapper<Hra>() {

			@Override
			public Hra mapRow(ResultSet rs, int rowNum) throws SQLException {
				Hra hra = new Hra();
				hra.setId(rs.getLong("id"));
				hra.setUkoncena(rs.getBoolean("ukoncena"));
				hra.setPocetTahov(rs.getInt("pocet_tahov"));
				hra.setCasRiesenia(rs.getInt("cas_riesenia"));
				Timestamp zaciatok = rs.getTimestamp("zaciatok");
				hra.setZaciatok(zaciatok.toLocalDateTime());
				Timestamp koniec = rs.getTimestamp("koniec");
				if (koniec != null) {
					hra.setKoniec(koniec.toLocalDateTime());
				}
				hra.setKrizovkaId(rs.getLong("krizovka_id"));

				return hra;
			}
		});
	}

	@Override
	public Hra ulozit(Hra hra) {
		if (hra == null)
			return null;

		if (hra.getId() == null) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("hra");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("pocet_tahov", "cas_riesenia", "ukoncena", "zaciatok", "koniec",
					"krizovka_id");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("pocet_tahov", hra.getPocetTahov());
			hodnoty.put("cas_riesenia", hra.getCasRiesenia());
			hodnoty.put("ukoncena", hra.isUkoncena());
			hodnoty.put("zaciatok", hra.getZaciatok());
			hodnoty.put("koniec", hra.getKoniec());
			hodnoty.put("krizovka_id", hra.getKrizovkaId());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			hra.setId(id);
		} else {
			String sql = "UPDATE hra SET pocet_tahov = ?, cas_riesenia = ?, ukoncena = ?, koniec = ? WHERE id = ?";
			jdbcTemplate.update(sql, hra.getPocetTahov(), hra.getCasRiesenia(), hra.isUkoncena(), hra.getKoniec(),
					hra.getId());
		}
		return hra;
	}

	@Override
	public void vymazat(long hraId) {
		String sql = String.format("DELETE FROM hra WHERE id = %d", hraId);
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Hra> getPodlaObdobia(Obdobie obdobie) {
		String vybraneObdobie = "";
		switch (obdobie.toString()) {
		case "Deň":
			vybraneObdobie = "DAY";
			break;
		case "Týždeň":
			vybraneObdobie = "WEEK";
			break;
		case "Mesiac":
			vybraneObdobie = "MONTH";
			break;
		}

		StringBuilder sql = new StringBuilder(
				"SELECT h.cas_riesenia, k.nazov, h.pocet_tahov, h.koniec FROM Hra as h JOIN krizovka as k ON h.krizovka_id = k.id WHERE h.ukoncena = 1");
		if (!obdobie.equals(Obdobie.VSETKY)) {
			sql.append(String.format(" AND " + "(SELECT TIMESTAMPDIFF(%s, h.koniec, now()) < 1)", vybraneObdobie));
		}

		return jdbcTemplate.query(sql.toString(), new RowMapper<Hra>() {

			@Override
			public Hra mapRow(ResultSet rs, int rowNum) throws SQLException {
				Hra hra = new Hra();
				hra.setNazovKrizovky(rs.getString("k.nazov"));
				hra.setCasRiesenia(rs.getInt("h.cas_riesenia"));
				hra.setPocetTahov(rs.getInt("h.pocet_tahov"));
				hra.setKoniec(rs.getTimestamp("h.koniec").toLocalDateTime());
				return hra;
			}
		});
	}

	// metoda vracia jednu krizovku podla hraId
	@Override
	public Krizovka getKrizovkaPodlaHraId(Long id) {
		String sql = "SELECT id, nazov, narocnost, sirka, vyska FROM krizovka WHERE id = (SELECT krizovka_id FROM hra WHERE id = ?)";
		Object[] parametre = new Object[] { id };
		return jdbcTemplate.query(sql, parametre, new RowMapper<Krizovka>() {

			@Override
			public Krizovka mapRow(ResultSet rs, int row) throws SQLException {
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
		}).get(0);
	}
}