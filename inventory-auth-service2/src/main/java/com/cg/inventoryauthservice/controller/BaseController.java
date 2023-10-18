package com.cg.inventoryauthservice.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cg.inventoryauthservice.dto.request.LoginRequest;
import com.cg.inventoryauthservice.dto.request.RegisterRequest;
import com.cg.inventoryauthservice.exception.ResponseObject;
import com.cg.inventoryauthservice.service.UserService;

import io.swagger.annotations.ApiOperation;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@RestController
@RequestMapping({"/users"})
@AllArgsConstructor
public class BaseController {

    private final UserService userService;
    
    @CrossOrigin
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertPatient(@RequestPart("patient") @Valid RegisterRequest registerRequest) {
        return ResponseObject.createSuccess(userService.signup(registerRequest));
    }
    
    @CrossOrigin
    @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> authenticateUser(@RequestPart("patient") @Valid LoginRequest loginRequest) {

    	return ResponseObject.createSuccess(userService.signin(loginRequest.getUsername(),loginRequest.getPassword()));
    }
    
    @CrossOrigin
    @GetMapping({"/myUser"})
    @ResponseBody
    ResponseEntity<?> getUserByPricipal(Principal principal) {
        return ResponseObject.success(userService.getPrinciple(principal));
    }



}
