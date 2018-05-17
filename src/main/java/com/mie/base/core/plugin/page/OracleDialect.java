package com.mie.base.core.plugin.page;

public class OracleDialect implements Dialect {

	@Override
	public boolean supportPage() {
		return true;
	}

	@Override
	public boolean supportPlaceholderPage() {
		return true;
	}

	@Override
	public String getPageSqlString(String sql, int offset, int limit) {
		// TODO Auto-generated method stub
		return this.getPageSqlPlaceholderString(sql, offset, null, limit, null);
	}

	@Override
	public String getPageSqlPlaceholderString(String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		
		int end = offset  + limit;
		
		/*String pageSql = " SELECT * " +
                         " FROM (" + 
				             " SELECT table_all.*, ROWNUM AS rowno " + 
                             " FROM ( " + sql +
                             " ) table_all" +
                             " WHERE ROWNUM <= " + end +
                         " ) page_alias " +
                         " WHERE page_alias.rowno > " + offset;*/
		
		
		String pageSql = " SELECT * " +
				" FROM (" + 
				" SELECT TABLE_ALL.*, ROWNUM AS rowno " + 
				" FROM ( " + sql +
				" ) TABLE_ALL" +
				" ) PAGE_ALIAS " +
				" WHERE PAGE_ALIAS.rowno > " + offset +
				" AND PAGE_ALIAS.rowno <=" + end;
		return pageSql;
	}

}
