package sk.upjs.paz1c.griddlers.persistentna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;

public class MysqlPolickoHryDao implements PolickoHryDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlPolickoHryDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PolickoHry> getVsetky() {
		// TODO
		return null;
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
			hodnoty.put("hra_id", polickoHry.getIdKrizovky());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			polickoHry.setId(id);
		} else {
			// AKTUALIZACIA
			String sql = "UPDATE policko_hry SET sur_x = ?, sur_y = ?, stav = ?, pozadovany_stav = ?, hra_id = ? WHERE id = ?";
			jdbcTemplate.update(sql, polickoHry.getSurX(), polickoHry.getSurY(), polickoHry.getStav(),
					polickoHry.getPozadovanyStav(), polickoHry.getIdKrizovky(), polickoHry.getId());
		}
		return polickoHry;
	}

	@Override
	public void vymazat(long polickoHry_id) {
		String sql = String.format("DELETE FROM policko_hry WHERE id = %d", polickoHry_id);
		jdbcTemplate.update(sql);
	}

}
