package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Obdobie;

public class MysqlHraDao implements HraDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlHraDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Hra> getVsetky() {
		String sql = "SELECT id, pocet_tahov, cas_riesenia, ukoncena, zaciatok, posledny_medzicas, koniec, krizovka_id"

				+ " FROM hra";
		return jdbcTemplate.query(sql, new RowMapper<Hra>() {

			@Override
			public Hra mapRow(ResultSet rs, int rowNum) throws SQLException {
				Hra hra = new Hra();
				hra.setId(rs.getLong("id"));
				hra.setPocetTahov(rs.getInt("pocet_tahov"));
				hra.setCasRiesenia(rs.getInt("cas_riesenia"));

				Timestamp zaciatok = rs.getTimestamp("zaciatok");
				hra.setZaciatok(zaciatok.toLocalDateTime());
				Timestamp poslednyMedzicas = rs.getTimestamp("posledny_medzicas");
				hra.setPoslednyMedzicas(poslednyMedzicas.toLocalDateTime());
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

		// VYTVORENIE
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("hra");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("pocet_tahov", "cas_riesenia", "ukoncena", "zaciatok", "posledny_medzicas",
				"koniec", "krizovka_id");
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("pocet_tahov", hra.getPocetTahov());
		hodnoty.put("cas_riesenia", hra.getCasRiesenia());
		hodnoty.put("ukoncena", hra.isUkoncena());
		hodnoty.put("zaciatok", hra.getZaciatok());
		hodnoty.put("posledny_medzicas", hra.getPoslednyMedzicas());
		hodnoty.put("koniec", hra.getKoniec());
		hodnoty.put("krizovka_id", hra.getKrizovkaId());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		hra.setId(id);

		return hra;
	}

	@Override
	public void vymazat(long hra_id) {
		String sql = String.format("DELETE FROM hra WHERE id = %d", hra_id);
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Hra> getPodlaObdobia(Obdobie obdobie) {
		// TODO Unit test
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
		
		StringBuilder sql = new StringBuilder("SELECT h.cas_riesenia, k.nazov, h.pocet_tahov FROM Hra as h JOIN krizovka as k ON h.krizovka_id = k.id WHERE h.ukoncena = 1");
		if(!obdobie.equals(Obdobie.VSETKY)) {
			sql.append(String.format(" AND "+ "(SELECT TIMESTAMPDIFF(%s, h.koniec, now()) < 1)", vybraneObdobie));
		}
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<Hra>() {

			@Override
			public Hra mapRow(ResultSet rs, int rowNum) throws SQLException {
				Hra hra = new Hra();
				hra.setNazovKrizovky(rs.getString("k.nazov"));
				hra.setCasRiesenia(rs.getInt("h.cas_riesenia"));
				hra.setPocetTahov(rs.getInt("h.pocet_tahov"));
				return hra;
			}
		});
	}
}