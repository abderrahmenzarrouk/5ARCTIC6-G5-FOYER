package tn.esprit.spring.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.DAO.Entities.Etudiant;

import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Etudiant.IEtudiantService;

import java.util.List;

@RestController
@RequestMapping("etudiant")
@AllArgsConstructor
public class EtudiantRestController {
    IEtudiantService service;
    EtudiantRepository erp;

    @GetMapping("health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Spring Boot container is working!");
    }
    @PostMapping("addOrUpdate")
    public ResponseEntity<Etudiant> addOrUpdate(@RequestBody Etudiant e) {
        Etudiant savedEtudiant = erp.save(e);
        return ResponseEntity.ok(savedEtudiant);
    }

    @GetMapping("findAll")
    List<Etudiant> findAll() {
        return erp.findAll();
    }

    @GetMapping("findById")
    Etudiant findById(@RequestParam long id) {
        return service.findById(id);
    }

    @DeleteMapping("delete")
    void delete(@RequestBody Etudiant e) {
        service.delete(e);
    }

    @DeleteMapping("deleteByIdd")
    void deleteById(@RequestParam long id) {
        service.deleteById(id);
    }
}
