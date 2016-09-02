package zj.base.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Entity
@Table(name = "t_user")
public class User {

    private long id;

    private String username;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    //打印自身属性
    public void printAll() {
        System.out.println("id = " + id + "    username= " + username);
    }


}
