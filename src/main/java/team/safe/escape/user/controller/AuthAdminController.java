package team.safe.escape.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.safe.escape.user.service.AuthService;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AuthAdminController {
    private final AuthService authService;


}
