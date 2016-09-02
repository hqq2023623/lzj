package zj.custom.model;

/**
 * 关联表
 * 
 * @author RD_haitao_ou
 *
 */
public class Entry {

	// 分录key
	private String key;
	// 分录关联的元数据
	private Metadata metaData;
	// 关系
	private int relation;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Metadata getMetaData() {
		return metaData;
	}

	public void setMetaData(Metadata metaData) {
		this.metaData = metaData;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	// 生成entry
	public static Entry createEntry(DynamicModel obj) {
		Entry entry = new Entry();
		entry.setKey(obj.getString("FKEY"));
		entry.setRelation(obj.getInt("FRELATION"));
		// Metadata metadata = Metadata.createMetaData(obj);
		entry.setMetaData((Metadata) obj.get("metaData"));
		return entry;
	}

}
