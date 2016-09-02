package zj.custom.mybatis;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import zj.custom.model.DynamicModel;
import zj.custom.model.Metadata;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * 自定义rowMapper,返回的是DynamicModel
 */
public class ColumnDynamicObjectRowMapper implements RowMapper<DynamicModel> {

    private Metadata metadata;

    public ColumnDynamicObjectRowMapper() {
    }

    public ColumnDynamicObjectRowMapper(Metadata m) {
        this.metadata = m;
    }

    @Override
    public DynamicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        DynamicModel mapOfColValues = createColumnMap(columnCount);
        // metadata不为空的时候调用通用查询接口
        if (metadata != null) {
            Map<String, String> code2Key = metadata.getCode2Key();
            for (int i = 1; i <= columnCount; i++) {
                String columnCode = getColumnKey(JdbcUtils.lookupColumnName(
                        rsmd, i));
                Object obj = getColumnValue(rs, i);
                mapOfColValues.put(code2Key.get(columnCode), obj);
            }
        } else {
            for (int i = 1; i <= columnCount; i++) {
                String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
                Object obj = getColumnValue(rs, i);
                mapOfColValues.put(key, obj);
            }
        }
        return mapOfColValues;
    }

    /**
     * Create a Map instance to be used as column map.
     * <p/>
     * By default, a linked case-insensitive Map will be created.
     *
     * @param columnCount the column count, to be used as initial capacity for the Map
     * @return the new Map instance
     * @see org.springframework.util.LinkedCaseInsensitiveMap
     */
    protected DynamicModel createColumnMap(int columnCount) {
        return new DynamicModel(columnCount);
    }

    /**
     * Determine the key to use for the given column in the column Map.
     *
     * @param columnName the column name as returned by the ResultSet
     * @return the column key to use
     * @see ResultSetMetaData#getColumnName
     */
    protected String getColumnKey(String columnName) {
        return columnName;
    }

    /**
     * Retrieve a JDBC object value for the specified column.
     * <p/>
     * The default implementation uses the {@code getObject} method.
     * Additionally, this implementation includes a "hack" to get around Oracle
     * returning a non standard object for their TIMESTAMP datatype.
     *
     * @param rs    is the ResultSet holding the data
     * @param index is the column index
     * @return the Object returned
     * @see JdbcUtils#getResultSetValue
     */
    protected Object getColumnValue(ResultSet rs, int index)
            throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index);
    }

}
