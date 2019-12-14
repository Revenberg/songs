package info.revenberg.song.domain;

import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import info.revenberg.song.domain.AuditModel;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "song")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Song extends AuditModel {
    public final static String secretKey = "ssshhhhhhhhhhh!!!!";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Column(nullable = false)
    private long songid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String source;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_bundle", referencedColumnName = "bundleid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bundle bundle;

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Vers> verses;

    public void setVerses(Set<Vers> verses) {
        this.verses = verses;
    }

    public Set<Vers> getVerses() {
        return verses;
    }

    public Song() {
    }

    public Song(long songid, String name, String source, Bundle bundle) {
        this.songid = songid;
        this.name = name;
        this.source = source;
        setBundle(bundle);
    }

    public long getId() {
        return this.id;
    }

    public long getSongid() {
        return songid;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        this.source = source;
    }

    public void addVers(Vers vers) {
        verses.add(vers);
        vers.setSong(this);
    }

    public void removeVers(Vers vers) {
        verses.remove(vers);
        vers.setSong(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Song))
            return false;
        return id == ((Song) o).getId();
    }
}
