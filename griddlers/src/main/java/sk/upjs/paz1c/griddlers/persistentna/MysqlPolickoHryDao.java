package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;

public class MysqlPolickoHryDao implements PolickoHryDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlPolickoHryDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PolickoHry> ulozit(List<PolickoHry> polickaHry) {
		List<PolickoHry> policka = new ArrayList<>();
		for (PolickoHry polHry : polickaHry) {
			policka.add(ulozit(polHry));
		}
		return policka;
	}

	@Override
	public PolickoHry ulozit(PolickoHry polickoHry) {
		if (polickoHry == null)
			return null;
		if (polickoHry.getId() == null) {
			// VYTVORENIE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("policko_hry");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("sur_x", "sur_y", "stav", "pozadovany_stav", "hra_id");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("sur_x", polickoHry.getSurX());
			hodnoty.put("sur_y", polickoHry.getSurY());
			hodnoty.put("stav", polickoHry.getStav());
			hodnoty.put("pozadovany_stav", polickoHry.getPozadovanyStav());
			hodnoty.put("hra_id", polickoHry.getIdHry());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			polickoHry.setId(id);
		} else {
			// AKTUALIZACIA
			String sql = "UPDATE policko_hry SET stav = ? WHERE id = ?";
			jdbcTemplate.update(sql, polickoHry.getStav(), polickoHry.getId());
		}
		return polickoHry;
	}

	@Override
	public void vymazat(Long hraId) {
		String sql = "DELETE FROM policko_hry WHERE hra_id = ?";
		jdbcTemplate.update(sql, hraId);
	}

	@Override
	public List<PolickoHry> getPodlaHraId(Long hraId) {
		String sql = "SELECT id, hra_id, stav, sur_x, sur_y, pozadovany_stav FROM policko_hry WHERE hra_id = ?";
		return jdbcTemplate.query(sql, new Object[] { hraId }, new RowMapper<PolickoHry>() {

			@Override
			public PolickoHry mapRow(ResultSet rs, int row) throws SQLException {
				Long id = rs.getLong("id");
				Boolean stav = rs.getBoolean("stav");
				if(rs.wasNull()) {
					stav = null;
				}	
				int surX = rs.getInt("sur_x");
				int surY = rs.getInt("sur_y");
				boolean pozadovanyStav = rs.getBoolean("pozadovany_stav");
				PolickoHry policko = new PolickoHry(stav, surX, surY, pozadovanyStav);
				policko.setId(id);
				return policko;
			}
		});

	}

}
