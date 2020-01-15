package info.revenberg.song.service;

import info.revenberg.song.domain.Line;
import info.revenberg.song.dao.jpa.VersRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class LineVersService {
    @Autowired
    private LineVersService lineRepository;

    public LineVersService() {
    }

    public Line createVers(Line line) {
        return lineRepository.save(line);
    }

    public Optional<Line> getVers(long id) {
        return lineRepository.findById(id);
    }

    public void updateVers(Line line) {
        lineRepository.save(line);
    }

    public void deleteVers(Long id) {
        lineRepository.deleteById(id);
    }
    
    public Page<Line> getAllLines(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Line> pageOfVerses = lineRepository.findAll(pageable);
        return pageOfVerses;
    }

	public Line findVersInSong(int rank, long songid) {
		return lineRepository.findVersInSong(rank, songid);
	}
}
