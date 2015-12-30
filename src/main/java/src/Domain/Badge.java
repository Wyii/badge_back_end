package src.Domain;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wyiss on 15/12/23.
 */
@Entity
public class Badge implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;                //当前徽章名字
    private String description;         //当前徽章描述
    private int score;                  //当前徽章权重
    private String url;                //当前徽章对应图片地址

    @OneToMany(mappedBy = "badge",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Record> records = new HashSet<Record>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
