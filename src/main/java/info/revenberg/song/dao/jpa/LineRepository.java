package info.revenberg.song.dao.jpa;

import info.revenberg.song.domain.Line;
import info.revenberg.song.domain.Vers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LineRepository extends PagingAndSortingRepository<Line, Long> {
    
    Page<Line> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM line l, vers v where l.rank=:rank AND v.versid=:versid AND s.versid=v.fk_vers", nativeQuery = true)
    Vers findLineinVers(@Param("rank") int rank, @Param("versid") long versid);

    @Query(value = "SELECT l.id, l.songid, l.rank, l.text, l.location, l.created_at, l.updated_at, l.fk_vers FROM line l, vers v where v.versid=:versid AND v.versid=l.fk_vers order by l.rank", nativeQuery = true)
    List<Vers> findAllByVersid(@Param("versid") long versid);
}
