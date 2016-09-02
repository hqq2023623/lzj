package zj.base.common;

/**
 * 用来传递列表对象的ThreadLocal数据 .
 *
 * @author lzj
 */
public class SystemContext {

    /**
     * 分页大小 .
     */
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
    /**
     * 分页的起始页 .
     */
    private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();
    /**
     * 列表的排序字段 .
     */
    private static ThreadLocal<String> sort = new ThreadLocal<String>();
    /**
     * 列表的排序方式: 升序、降序 .
     */
    private static ThreadLocal<String> order = new ThreadLocal<String>();

    /**
     * 系统的真实路径 .
     */
    private static ThreadLocal<String> realPath = new ThreadLocal<String>();

    public static String getRealPath() {
        return realPath.get();
    }

    public static void setRealPath(String readPath) {
        SystemContext.realPath.set(readPath);
    }

    public static Integer getPageOffset() {
        return pageOffset.get();
    }

    public static void setPageOffset(Integer pageOffset) {
        SystemContext.pageOffset.set(pageOffset);
    }

    public static String getSort() {
        return sort.get();
    }

    public static void setSort(String sort) {
        SystemContext.sort.set(sort);
    }

    public static String getOrder() {
        return order.get();
    }

    public static void setOrder(String order) {
        SystemContext.order.set(order);
    }

    public static void removePageOffset() {
        pageOffset.remove();
    }

    public static void removeSort() {
        sort.remove();
    }

    public static void removeOrder() {
        order.remove();
    }

    public static void removeRealPath() {
        realPath.remove();
    }

    public static Integer getPageSize() {
        return pageSize.get();
    }

    public static void setPageSize(Integer pageSize) {
        SystemContext.pageSize.set(pageSize);
    }

    public static void removePageSize() {
        pageSize.remove();
    }

}
