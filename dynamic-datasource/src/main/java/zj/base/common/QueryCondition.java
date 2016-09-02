package zj.base.common;

/**
 * 查询条件
 *
 * @author lzj
 * @date 2015年7月23日
 */

public class QueryCondition {

    public static final String GT = ">";
    public static final String LT = "<";
    public static final String EQ = "=";
    public static final String NE = "!=";
    public static final String GTE = ">=";
    public static final String LTE = "<=";

    public static final String AND = "and";
    public static final String OR = "or";
    public static final String BETWEEN = "between";
    public static final String NULL = "is null";
    public static final String NOT_NULL = "is not null";

    // 字段名
    private String key;
    // 值
    private String value;
    // 操作符
    private String operator = EQ;
    // 逻辑操作符 and or
    private String logicOperator = AND;

    public QueryCondition() {
        super();
    }

    public QueryCondition(String key, String value, String operator, String logicOperator) {
        super();
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.logicOperator = logicOperator;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(String logicOperator) {
        this.logicOperator = logicOperator;
    }

}
