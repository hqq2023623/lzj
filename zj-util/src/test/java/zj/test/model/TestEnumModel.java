package zj.test.model;

/**
 * 测试枚举模型 .
 *
 * @author lzj 2015年1月16日
 */
public enum TestEnumModel {

    // 括号内为Enum的property
    RED("红色"), GREEN("绿色");

    private String color;

    private TestEnumModel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
