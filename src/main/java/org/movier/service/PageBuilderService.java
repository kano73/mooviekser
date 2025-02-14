package org.movier.service;

import org.movier.config.security.AuthenticatedMyUserService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PageBuilderService {
    private final AuthenticatedMyUserService auth;

    public PageBuilderService(AuthenticatedMyUserService auth) {
        this.auth = auth;
    }

}
