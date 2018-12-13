package sk.upjs.paz1c.griddlers.persistentna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.paz1c.griddlers.entity.Legenda;

public class MysqlLegendaDao implements LegendaDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlLegendaDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Legenda> getHornaPodlaId(Long id) {
		return getLegendaLHPodlaId(id, true);
	}

	@Override
	public List<Legenda> getLavaPodlaId(Long id) {
		return getLegendaLHPodlaId(id, false);
	}

	private List<Legenda> getLegendaLHPodlaId(Long id, boolean horna) {
		String sql = "SELECT krizovka_id, horna, riadok_stlpec, poradie, "
				+ "hodnota FROM legenda WHERE krizovka_id = ? AND horna = ?";
		return jdbcTemplate.query(sql, new Object[] { id, horna }, new RowMapper<Legenda>() {

			@Override
			public Legenda mapRow(ResultSet rs, int rowNum) throws SQLException {
				Legenda legenda = new Legenda(rs.getLong("krizovka_id"), rs.getBoolean("horna"),
						rs.getInt("riadok_stlpec"), rs.getInt("poradie"), rs.getInt("hodnota"));
				return legenda;
			}
		});
	}

	@Override
	public void ulozit(List<Legenda> legenda, Long idKrizovky) {
		for (Legenda leg : legenda) {
			leg.setKrizovkaId(idKrizovky);
			ulozit(leg);
		}
	}

	private Legenda ulozit(Legenda legenda) {
		if (legenda == null)
			return null;
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("legenda");
		simpleJdbcInsert.usingColumns("krizovka_id", "horna", "riadok_stlpec", "poradie", "hodnota");
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("krizovka_id", legenda.getKrizovkaId());
		hodnoty.put("horna", legenda.isHorna());
		hodnoty.put("riadok_stlpec", legenda.getRiadokStlpec());
		hodnoty.put("poradie", legenda.getPoradie());
		hodnoty.put("hodnota", legenda.getHodnota());
		simpleJdbcInsert.execute(hodnoty);
		return legenda;
	}

	@Override
	public void vymazat(long krizovkaId) {
		String sql = "DELETE FROM legenda WHERE krizovka_id = " + krizovkaId;
		jdbcTemplate.update(sql);
	}
}
