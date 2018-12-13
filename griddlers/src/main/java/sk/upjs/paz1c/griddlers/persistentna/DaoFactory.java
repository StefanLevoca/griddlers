package sk.upjs.paz1c.griddlers.persistentna;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;

	private JdbcTemplate jdbcTemplate;
	private KrizovkaDao krizovkaDao;
	private HraDao hraDao;
	private PolickoDao polickoDao;
	private PolickoHryDao polickoHryDao;
	private LegendaDao legendaDao;

	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("griddlers");
			dataSource.setPassword("Griddlers");
			dataSource.setDatabaseName("griddlers_db");
			dataSource.setUrl("jdbc:mysql://localhost/griddlers_db?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	public KrizovkaDao getKrizovkaDao() {
		if (krizovkaDao == null) {
			krizovkaDao = new MysqlKrizovkaDao(getJdbcTemplate());
		}
		return krizovkaDao;
	}

	public HraDao getHraDao() {
		if (hraDao == null) {
			hraDao = new MysqlHraDao(getJdbcTemplate());
		}
		return hraDao;
	}

	public PolickoDao getPolickoDao() {
		if (polickoDao == null) {
			polickoDao = new MysqlPolickoDao(getJdbcTemplate());
		}
		return polickoDao;
	}

	public PolickoHryDao getPolickoHryDao() {
		if (polickoHryDao == null) {
			polickoHryDao = new MysqlPolickoHryDao(getJdbcTemplate());
		}
		return polickoHryDao;
	}

	public LegendaDao getLegendaDao() {
		if (legendaDao == null) {
			legendaDao = new MysqlLegendaDao(getJdbcTemplate());
		}
		return legendaDao;
	}

}
