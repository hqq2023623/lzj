package zj.custom.model;

/**
 * 字段
 * 
 * @author RD_haitao_ou
 *
 */
public class Field {
	// 字段名称
	private String name;
	// 字段key
	private String key;
	// 字段代码
	private String code;
	// 主键
	private boolean isPrimary;
	// 字段类型
	private int fieldType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public static Field createField(DynamicModel fieldObject) {
		Field field = new Field();
		field.key = fieldObject.getString("fkey");
		field.name = fieldObject.getString("fname");
		field.code = fieldObject.getString("fcode");
		field.isPrimary = fieldObject.getString("fisprimary").equalsIgnoreCase("1");
		field.fieldType = fieldObject.getInt("ftype");
		return field;
	}

}
