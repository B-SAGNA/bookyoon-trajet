package sn.sonatel.dsi.ins.imoc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sn.sonatel.dsi.ins.imoc.service.dto.NotificationDTO;

@FeignClient(name = "bookyoonnotificationservice")
public interface NotificationClient {

    @PostMapping("/api/notifications")
    void createNotification(@RequestBody NotificationDTO notificationDTO);
}

