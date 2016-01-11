package src.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wyiss on 15/12/23.
 */
@Entity
public class Record implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "to_user_id")
    private String toUser;

//    @ManyToOne
//    @JoinColumn(name = "from_user_id")
    private String fromUser;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;


    private String comment;            //评论内容

    public Record() {
    }

    private Date dateCreated = new Date();          //评论时间
    private Date lastUpdated;          //最后更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
