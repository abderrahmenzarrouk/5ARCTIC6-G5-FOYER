package tn.esprit.spring.Services.Chambre;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChambreService implements IChambreService {
    ChambreRepository repo;
    BlocRepository blocRepository;

    @Override
    public Chambre addOrUpdate(Chambre c) {
        return repo.save(c);
    }

    @Override
    public List<Chambre> findAll() {
        return repo.findAll();
    }

    @Override
    public Chambre findById(long id) {

        Optional<Chambre> c =  repo.findById(id);
        if (c.isPresent()) {
            return c.get();
        } else {
            throw new NoSuchElementException("Chambre not found with id: " + id);
        }
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Chambre c) {
        repo.delete(c);
    }

    @Override
    public List<Chambre> getChambresParNomBloc(String nomBloc) {
        return repo.findByBlocNomBloc(nomBloc);
    }

    @Override
    public long nbChambreParTypeEtBloc(TypeChambre type, long idBloc) {
        return repo.countByTypeCAndBlocIdBloc(type, idBloc);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {
        // Récupérer l'année universitaire actuelle
        LocalDate[] anneeUniversitaire = getCurrentAcademicYear();

        List<Chambre> listChambreDispo = new ArrayList<>();
        for (Chambre c : repo.findAll()) {
            if (isChambreEligible(c, nomFoyer, type)) {
                int numReservation = countReservationsInCurrentYear(c, anneeUniversitaire);
                if (isChambreAvailable(c, numReservation)) {
                    listChambreDispo.add(c);
                }
            }
        }
        return listChambreDispo;
    }



    private boolean isChambreEligible(Chambre c, String nomFoyer, TypeChambre type) {
        return c.getTypeC().equals(type) && c.getBloc().getFoyer().getNomFoyer().equals(nomFoyer);
    }

    private int countReservationsInCurrentYear(Chambre c, LocalDate[] anneeUniversitaire) {
        return (int) c.getReservations().stream()
                .filter(reservation ->
                        reservation.getAnneeUniversitaire().isBefore(anneeUniversitaire[1]) &&
                                reservation.getAnneeUniversitaire().isAfter(anneeUniversitaire[0]))
                .count();
    }

    private boolean isChambreAvailable(Chambre c, int numReservation) {
        switch (c.getTypeC()) {
            case SIMPLE:
                return numReservation == 0;
            case DOUBLE:
                return numReservation < 2;
            case TRIPLE:
                return numReservation < 3;
            default:
                return false;
        }
    }

    @Override
    public void listeChambresParBloc() {
        for (Bloc b : blocRepository.findAll()) {
            log.info("Bloc => " + b.getNomBloc() + " ayant une capacité " + b.getCapaciteBloc());
            if (b.getChambres().size() != 0) {
                log.info("La liste des chambres pour ce bloc: ");
                for (Chambre c : b.getChambres()) {
                    log.info("NumChambre: " + c.getNumeroChambre() + " type: " + c.getTypeC());
                }
            } else {
                log.info("Pas de chambre disponible dans ce bloc");
            }
            log.info("********************");
        }
    }

    @Override
    public void pourcentageChambreParTypeChambre() {
        long totalChambre = repo.count();
        String s = "Le pourcentage des chambres pour le type SIMPLE est égale à";
        double pSimple = (repo.countChambreByTypeC(TypeChambre.SIMPLE) * 100) / (double) totalChambre;
        double pDouble = (repo.countChambreByTypeC(TypeChambre.DOUBLE) * 100) / (double) totalChambre;
        double pTriple = (repo.countChambreByTypeC(TypeChambre.TRIPLE) * 100) / (double) totalChambre;
        log.info("Nombre total des chambre: " + totalChambre);
        log.info(s + pSimple);
        log.info(s + pDouble);
        log.info(s + pTriple);

    }

    public void nbPlacesDisponibleParChambreAnneeEnCours() {
        LocalDate[] anneeUniversitaire = getCurrentAcademicYear();

        for (Chambre c : repo.findAll()) {
            long nbReservation = repo.countReservationsByIdChambreAndReservationsEstValideAndReservationsAnneeUniversitaireBetween(c.getIdChambre(), true, anneeUniversitaire[0], anneeUniversitaire[1]);
            logChambreAvailability(c, nbReservation);
        }
    }

    private LocalDate[] getCurrentAcademicYear() {
        int year = LocalDate.now().getYear() % 100;
        LocalDate dateDebutAU;
        LocalDate dateFinAU;

        if (LocalDate.now().getMonthValue() <= 7) {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + (year - 1)), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + year), 6, 30);
        } else {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + year), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + (year + 1)), 6, 30);
        }

        return new LocalDate[]{dateDebutAU, dateFinAU};
    }

    private void logChambreAvailability(Chambre c, long nbReservation) {
        int maxCapacity = getMaxCapacity(c.getTypeC());
        int availablePlaces = maxCapacity - (int) nbReservation;

        if (availablePlaces > 0) {
            log.info("Le nombre de place disponible pour la chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est " + availablePlaces);
        } else {
            log.info("La chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est complete");
        }
    }

    private int getMaxCapacity(TypeChambre type) {
        switch (type) {
            case SIMPLE: return 1;
            case DOUBLE: return 2;
            case TRIPLE: return 3;
            default: return 0; // Handle unknown types gracefully
        }
    }

}
