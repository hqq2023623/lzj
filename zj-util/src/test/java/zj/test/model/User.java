package zj.test.model;

/**
 * @author Lzj Created on 2015/12/18.
 */
public class User {

    private Long id;

    private String name;

    public void printAll() {
        System.out.println("id = " + id + "  -> name = " + name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
