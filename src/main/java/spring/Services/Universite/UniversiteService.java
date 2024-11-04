package spring.Services.Universite;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.spring.DAO.Entities.Universite;

import tn.esprit.spring.DAO.Repositories.UniversiteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UniversiteService implements IUniversiteService {
    UniversiteRepository repo;

    @Override
    public Universite addOrUpdate(Universite u) {
        return repo.save(u);
    }

    @Override
    public List<Universite> findAll() {
        return repo.findAll();
    }

    @Override
    public Universite findById(long id) {

        Optional<Universite> u =  repo.findById(id);
        if (u.isPresent()) {
            return u.get();
        } else {
            throw new NoSuchElementException("universite not found with id: " + id);
        }
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Universite u) {
        repo.delete(u);
    }
}
