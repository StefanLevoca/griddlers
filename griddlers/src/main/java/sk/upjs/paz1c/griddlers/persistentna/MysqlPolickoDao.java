package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.Policko;

public class MysqlPolickoDao implements PolickoDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlPolickoDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Policko> getPodlaId(Long krizovkaId) {
		String sql = "SELECT id, sur_x, sur_y, stav FROM policko WHERE krizovka_id = ?";
		Object[] parametre = new Object[] { krizovkaId };
		return jdbcTemplate.query(sql, parametre, new RowMapper<Policko>() {
			@Override
			public Policko mapRow(ResultSet rs, int line) throws SQLException {
				Long id = rs.getLong("id");
				int surX = rs.getInt("sur_x");
				int surY = rs.getInt("sur_y");
				boolean stav = rs.getBoolean("stav");

				return new Policko(id, stav, surX, surY);
			}
		});
	}

	@Override
	public void ulozit(List<Policko> policka, Long idKrizovky) {
		for (Policko pol : policka) {
			pol.setIdKrizovky(idKrizovky);
			ulozit(pol);
		}
	}

	private Policko ulozit(Policko policko) {
		if (policko == null)
			return null;
		if (policko.getId() == null) {
			// VYTVORENIE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("policko");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("sur_x", "sur_y", "stav", "krizovka_id");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("sur_x", policko.getSurX());
			hodnoty.put("sur_y", policko.getSurY());
			hodnoty.put("stav", policko.getStav());
			hodnoty.put("krizovka_id", policko.getIdKrizovky());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			policko.setId(id);
		}
		return policko;
	}

	@Override
	public void vymazat(Long krizovkaId) {
		String sql = String.format("DELETE FROM policko WHERE krizovka_id = %d", krizovkaId);
		jdbcTemplate.update(sql);
	}

}
