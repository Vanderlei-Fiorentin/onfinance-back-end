package com.onfinance.controllers;

import com.onfinance.entities.RoleEntity;
import com.onfinance.repositories.RoleRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class RoleController extends Controller<Integer, RoleEntity> {

    public RoleController() {
        super(RoleRepository.class);
    }

}
