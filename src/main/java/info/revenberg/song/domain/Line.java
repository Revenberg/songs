package info.revenberg.song.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import info.revenberg.song.domain.AuditModel;

@Entity
@Table(name = "line")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Line extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Column(nullable = false)
    private int rank;

    @Column(nullable = false)
    private String text;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_vers", referencedColumnName = "versid")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
    private Vers vers;

    public Line() {
    }

    public Line(int rank, String text, String location, Vers vers) {
        this.rank = rank;
        this.text = text;
        this.location = location;
        setVers(vers);
    }

    public long getId() {
        return this.id;
    }

    public void setVers(Vers vers) {
        this.vers = vers;
    }

    public Vers getVers() {
        return this.vers;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Line))
            return false;
        return id == ((Line) o).getId();
    }

}
