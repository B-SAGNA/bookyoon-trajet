package sn.sonatel.dsi.ins.imoc.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import sn.sonatel.dsi.ins.imoc.service.dto.ReservationDTO;

import java.util.List;

@FeignClient(name = "bookyoonreservationservice")
public interface ReservationClient {

    @GetMapping("/api/reservations/{trajetId}/users")
    List<String> getUserLoginsForTrajet(@PathVariable("trajetId") Long trajetId);

    @GetMapping("/api/reservations/{trajetId}/count-annulations")
    Long countDeleteReservations(@PathVariable("trajetId") Long trajetId);

    @GetMapping("/api/reservations/trajets/{trajetId}")
    List<ReservationDTO> getReservationsByTrajetId(@PathVariable Long trajetId);

    @PutMapping("/api/reservations/trajets/annuler-par-trajet/{trajetId}")
    void annulerReservations(@PathVariable("trajetId") Long trajetId);

    @GetMapping("/api/reservations/{trajetId}/count-active")
    Long countActiveReservations(@PathVariable("trajetId") Long trajetId);
}
