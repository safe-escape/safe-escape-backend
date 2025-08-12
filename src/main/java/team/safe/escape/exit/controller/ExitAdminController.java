package team.safe.escape.exit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/exits")
public class ExitAdminController {

    @GetMapping("/{exitId}/shelter/nearby")
    public void getNearByShelter(@PathVariable Long exitId) {

    }

}
