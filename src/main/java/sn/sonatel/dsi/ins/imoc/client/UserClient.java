package sn.sonatel.dsi.ins.imoc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import sn.sonatel.dsi.ins.imoc.service.dto.UserDTO;

@FeignClient(name = "bookyoonuserservice")
public interface UserClient {
    @GetMapping("/api/account")
    ResponseEntity<UserDTO> getAccount();
}
