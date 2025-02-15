package sn.sonatel.dsi.ins.imoc.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.sonatel.dsi.ins.imoc.client.NotificationClient;
import sn.sonatel.dsi.ins.imoc.client.ReservationClient;
import sn.sonatel.dsi.ins.imoc.domain.Trajet;
import sn.sonatel.dsi.ins.imoc.repository.TrajetRepository;
import sn.sonatel.dsi.ins.imoc.security.SecurityUtils;
import sn.sonatel.dsi.ins.imoc.service.dto.NotificationDTO;
import sn.sonatel.dsi.ins.imoc.service.dto.ReservationDTO;
import sn.sonatel.dsi.ins.imoc.service.dto.TrajetDTO;
import sn.sonatel.dsi.ins.imoc.service.mapper.TrajetMapper;

/**
 * Service Implementation for managing {@link sn.sonatel.dsi.ins.imoc.domain.Trajet}.
 */
@Service
@Transactional
public class TrajetService {

    private final Logger log = LoggerFactory.getLogger(TrajetService.class);

    private final TrajetRepository trajetRepository;

    private final TrajetMapper trajetMapper;
    private final ReservationClient reservationClient;
    private final NotificationClient notificationClient;

    public TrajetService(TrajetRepository trajetRepository, TrajetMapper trajetMapper, ReservationClient reservationClient, NotificationClient notificationClient) {
        this.trajetRepository = trajetRepository;
        this.trajetMapper = trajetMapper;
        this.reservationClient = reservationClient;
        this.notificationClient = notificationClient;
    }

    /**
     * Save a trajet.
     *
     * @param trajetDTO the entity to save.
     * @return the persisted entity.
     */
    public TrajetDTO save(TrajetDTO trajetDTO) {
        log.debug("Request to save Trajet : {}", trajetDTO);
        Trajet trajet = trajetMapper.toEntity(trajetDTO);
        trajet = trajetRepository.save(trajet);

        // Créer le message de notification
        String message = String.format(
            "Vous venez de créer le trajet pour %s à %s pour le %s",
            trajet.getPointDepart(),
            trajet.getPointArrive(),
            trajet.getDateHeure().toString()
        );

        // Créer notification
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setMessage(message);
        notificationDTO.setUserLogin(trajet.getUserLogin());
        notificationDTO.setReservationId(trajet.getId());
        notificationDTO.setDeleted(false);
        notificationDTO.setRead(false);

        // Appel du microservice Notification pour créer la notification
        notificationClient.createNotification(notificationDTO);

        return trajetMapper.toDto(trajet);
    }

    /**
     * Update a trajet.
     *
     * @param trajetDTO the entity to save.
     * @return the persisted entity.
     */
    public TrajetDTO update(TrajetDTO trajetDTO) {
        log.debug("Request to update Trajet : {}", trajetDTO);

        // Récupération de l'ancien trajet pour comparer les modifications
        Trajet ancienTrajet = trajetRepository.findById(trajetDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("Trajet not found"));

        Trajet trajet = trajetMapper.toEntity(trajetDTO);
        trajet = trajetRepository.save(trajet);

        // Comparer les anciens et nouveaux détails pour construire le message
        String messageConducteur = String.format(
            "Vous avez modifié le trajet de %s à %s, prévu le %s.",
            ancienTrajet.getPointDepart(), ancienTrajet.getPointArrive(), ancienTrajet.getDateHeure()
        );

        String messagePassager = String.format(
            "Le trajet de %s à %s, prévu le %s, a été modifié. Veuillez consulter les nouveaux détails.",
            ancienTrajet.getPointDepart(), ancienTrajet.getPointArrive(), ancienTrajet.getDateHeure()
        );

        // Appel au microservice Notification
        sendNotification(trajet.getUserLogin(), messageConducteur, null);
        notifyPassengers(trajet.getId(), messagePassager);


        return trajetMapper.toDto(trajet);
    }

    private void sendNotification(String userLogin, String message, Long reservationId) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUserLogin(userLogin);
        notificationDTO.setMessage(message);
        notificationDTO.setReservationId(reservationId);
        notificationDTO.setDeleted(false);
        notificationDTO.setRead(false);

        // Appeler le client Feign pour envoyer la notification
        notificationClient.createNotification(notificationDTO);
    }

    private void notifyPassengers(Long trajetId, String message) {
        List<ReservationDTO> reservations = reservationClient.getReservationsByTrajetId(trajetId);
        reservations.forEach(reservation -> {
            sendNotification(reservation.getUserLogin(), message, reservation.getId());
        });
    }


    /**
     * Partially update a trajet.
     *
     * @param trajetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrajetDTO> partialUpdate(TrajetDTO trajetDTO) {
        log.debug("Request to partially update Trajet : {}", trajetDTO);

        return trajetRepository
            .findById(trajetDTO.getId())
            .map(existingTrajet -> {
                trajetMapper.partialUpdate(existingTrajet, trajetDTO);

                return existingTrajet;
            })
            .map(trajetRepository::save)
            .map(trajetMapper::toDto);
    }

    /**
     * Get one trajet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrajetDTO> findOne(Long id) {
        log.debug("Request to get Trajet : {}", id);
        return trajetRepository.findById(id).map(trajetMapper::toDto);
    }

    public Optional<TrajetDTO> findOnes(Long trajetId) {
        return trajetRepository.findById(trajetId)
            .map(trajetMapper::toDto); // Convert entity to DTO
    }

    /**
     * Delete the trajet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trajet : {}", id);
        trajetRepository.deleteById(id);
    }

    // RETOURNE L'ENSEMBLE DES TRAJETS DE L'UTILISATEUR CONNECTE
    public List<TrajetDTO> getTrajetsHistory() {
        return trajetMapper.toDto(trajetRepository.findAllByUserLoginIgnoreCase(SecurityUtils.getCurrentUserLogin().orElseThrow()));
    }
    // RETOURNE L'ENSEMBLE DES TRAJETS DE L'UTILISATEUR CONNECTE a venir
    public List<TrajetDTO> getTrajetsMesAvenirEtActifs() {
        ZonedDateTime currentDate = ZonedDateTime.now();
        // Récupérer l'utilisateur connecté (le login)
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElseThrow();List<Trajet> trajets = trajetRepository.findByUserLoginAndDateHeureGreaterThanEqualAndDeletedFalse(
            currentUserLogin,
            currentDate
        );

        // Mapper les trajets en DTO (en utilisant un TrajetMapper ou autre méthode de mapping)
        return trajetMapper.toDto(trajets);}
    //RETOURNE LE NOMBRE DE PLACE DISPONIBLE
    public Integer placesDisponibles(Long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
            .orElseThrow(() -> new EntityNotFoundException("Trajet not found"));
        return trajet.getPlaceDispo();
    }

    //RETOURNE L'ENSEMBLE DES TRAJET D'AUJOURDHIU ET A VENIR
    public List<Trajet> getTrajetsAvenirEtActifs() {
        ZonedDateTime currentDate = ZonedDateTime.now(); // Date et heure actuelle
        return trajetRepository.findByDateHeureGreaterThanEqualAndDeletedFalse(currentDate);
    }


    //RETOURNE LES USERLOGIN D'UN TRAJET
    public List<String> getUserLoginsForTrajet(Long trajetId) {
        return reservationClient.getUserLoginsForTrajet(trajetId);
    }

    //annulation
    public Integer getAnnulation(Long trajetId) {
        // Récupérer le trajet
        Trajet trajet = trajetRepository.findById(trajetId)
            .orElseThrow(() -> new EntityNotFoundException("Trajet non trouvé avec l'ID " + trajetId));

        // Appeler le service Réservation
        Long reservationsAnnuler = reservationClient.countDeleteReservations(trajetId);

        return reservationsAnnuler.intValue();
    }

    //RETOURNE de place restante
    public Integer getPlacesRestantes(Long trajetId) {
        // Récupérer le trajet
        Trajet trajet = trajetRepository.findById(trajetId)
            .orElseThrow(() -> new EntityNotFoundException("Trajet non trouvé avec l'ID " + trajetId));

        // Appeler le service Réservation
        Long reservationsActive = reservationClient.countActiveReservations(trajetId);

        return trajet.getPlaceDispo() - reservationsActive.intValue();
    }

    //ANNULER UN TRAJET
    public void annulerTrajet(Long trajetId) {
        // Récupérer toutes les réservations associées au trajet
        List<ReservationDTO> reservations = reservationClient.getReservationsByTrajetId(trajetId);
        // Récupérer le trajet
        Trajet trajet = trajetRepository.findById(trajetId)
            .orElseThrow(() -> new EntityNotFoundException("Trajet not found"));

        // Mettre à jour le statut deleted
        trajet.setDeleted(true);
        trajetRepository.save(trajet);

        // Annuler les réservations via Feign
        reservationClient.annulerReservations(trajetId);

        // Envoyer une notification au créateur du trajet
        NotificationDTO creatorNotification = new NotificationDTO();
        creatorNotification.setUserLogin(trajet.getUserLogin());
        creatorNotification.setMessage("Vous venez d'annuler le trajet de " +
            trajet.getPointDepart() + " à " + trajet.getPointArrive() +
            " du " + trajet.getDateHeure() + ".");
        creatorNotification.setReservationId(trajet.getId());
        creatorNotification.setRead(false);
        creatorNotification.setDeleted(false);
        notificationClient.createNotification(creatorNotification);

        // Notifier tous les utilisateurs ayant réservé
        List<String> userLogins = getUserLoginsWithReservation(trajetId);
        for (String userLogin : userLogins) {
            NotificationDTO userNotification = new NotificationDTO();
            userNotification.setUserLogin(userLogin);
            userNotification.setMessage("Le trajet de " +
                trajet.getPointDepart() + " à " + trajet.getPointArrive() +
                " du " + trajet.getDateHeure() + " vient d'être annulé.");
            userNotification.setReservationId(trajet.getId());
            userNotification.setRead(false);
            userNotification.setDeleted(false);
            notificationClient.createNotification(userNotification);
        }
    }

    // Méthode pour récupérer les utilisateurs ayant réservé
    private List<String> getUserLoginsWithReservation(Long trajetId) {
        // Appeler le microservice Réservation et récupérer les logins
        return reservationClient.getUserLoginsForTrajet(trajetId);
    }

}
