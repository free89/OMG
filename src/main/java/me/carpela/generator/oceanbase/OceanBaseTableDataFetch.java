package me.carpela.generator.oceanbase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.oceanbase.OceanbaseDataSourceProxy;

import me.carpela.generator.config.Configuration;
import me.carpela.generator.config.Table;
import me.carpela.generator.data.TableData;
import me.carpela.generator.util.StringUtility;

/**
 * @description 获取oceanbase表信息
 * @author Hover Winter
 *
 */
public class OceanBaseTableDataFetch {

	public static List<TableData> fetch(Configuration config) {
		List<TableData> tds = new ArrayList<TableData>();

		Connection con = null;
		PreparedStatement ps = null;
		OceanbaseDataSourceProxy odsp = new OceanbaseDataSourceProxy();
		odsp.setConfigURL(config.getDb().getConfigURL());
		try {
			odsp.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con = odsp.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Table table : config.getTables()) {
			TableData td = new TableData();
			td.setDaoname(table.getObjectName()+"DAO");
			td.setMappername(table.getObjectName()+"Mapper");
			td.setObjectName(table.getObjectName());
			td.setNamespace(config.getSqlMap().getTargetPackage());
			td.setParameterType(config.getJavaModel().getTargetPackage() + "."
					+ table.getObjectName());
			td.setTablename(table.getName());

			Map<String, String[]> fields = new HashMap<String, String[]>();

			try {
				ps = con.prepareStatement("select * from " + table.getName());
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData rsm = rs.getMetaData();

				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					fields.put(StringUtility.camelName(rsm.getColumnName(i)), new String[]{ rsm.getColumnName(i),rsm.getColumnTypeName(i)});
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			td.setFields(fields);
			tds.add(td);
		}
		
		try {
			ps.close();
			con.close();
			odsp.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tds;
	}
}
