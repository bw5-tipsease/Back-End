package com.lambdaschool.tipsease.controllers;

import com.lambdaschool.tipsease.models.User;
import com.lambdaschool.tipsease.models.UserRoles;
import com.lambdaschool.tipsease.services.RoleService;
import com.lambdaschool.tipsease.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Api(value = "Creates%20New%20Account", produces = "MediaType.APPLICATION_JSON_VALUE", tags = {"Creates New Account"})
@RestController
public class NewUserController
{
    private static final Logger logger = LoggerFactory.getLogger(NewUserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // creates a new user
    @ApiOperation(value = "This endpoint is used to create a new user.")
    @PostMapping(value = "/createnewuser", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid @RequestBody User newuser) throws URISyntaxException
    {
        logger.trace(request.getRequestURI() + " accessed");

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        newRoles.add(new UserRoles(newuser, roleService.findByName("user")));
        newuser.setUserRoles(newRoles);

        newuser = userService.save(newuser);

        // set the location header for the newly created resource - to another controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(request.getServerName() + ":" + request.getLocalPort() + "/users/user/{userId}").buildAndExpand(newuser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

}
