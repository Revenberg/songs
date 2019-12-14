package info.revenberg.song.dao.jpa;

import info.revenberg.song.domain.Vers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VersRepository extends PagingAndSortingRepository<Vers, Long> {
    Vers findVersByName(String name);

    Page<Vers> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM vers v, song s where v.rank=:rank AND s.songid=:songid AND s.songid=v.fk_song", nativeQuery = true)
    Vers findVersInSong(@Param("rank") int rank, @Param("songid") long songid);
}
