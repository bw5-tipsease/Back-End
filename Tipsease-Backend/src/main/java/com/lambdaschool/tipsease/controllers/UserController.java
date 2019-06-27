package com.lambdaschool.tipsease.controllers;

import com.lambdaschool.tipsease.models.User;
import com.lambdaschool.tipsease.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "User%20Actions", produces = "MediaType.APPLICATION_JSON_VALUE", tags = {"User Actions"})
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // this is in charge of updating the current user
    @ApiOperation(value = "This endpoint is responsible for returning the currently authenticated user's information as one single object.")
    @GetMapping(value = "/user/info", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        User data = userService.findUserData(authentication.getName());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // this updates the current user
    @PutMapping(value = "/user/update/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody User updateUser, @PathVariable long id)
    {
        logger.trace(request.getRequestURI() + " accessed");

        userService.update(updateUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // johns code

    @ApiOperation(value = "this lists all of the users and their associated properties, this should be used for testing & not production.")
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request)
    {
        logger.trace(request.getRequestURI() + " accessed");

        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    // returns any user you want by their ID
    @ApiOperation(value = "This gets the user by user id and returns an object of data relevant to that user. You do NOT need to be signed into the users account you want to get data returned to")
    @GetMapping(value = "/user/{userId}", produces = {"application/json"})
    public ResponseEntity<?> getUser(HttpServletRequest request, @PathVariable Long userId)
    {
        logger.trace(request.getRequestURI() + " accessed");

        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation(value = "Gets the current username of the signed in user.")
    @GetMapping(value = "/getusername", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authentication)
    {
        logger.trace(request.getRequestURI() + " accessed");

        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation(value = "this was supposed to be used to add a new user")
    @PostMapping(value = "/user", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid @RequestBody User newuser) throws URISyntaxException
    {
        logger.trace(request.getRequestURI() + " accessed");

        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userid}").buildAndExpand(newuser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @Deprecated
    @ApiOperation(value = "This is used to delete a user by their id.")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request, @PathVariable long id)
    {
        logger.trace(request.getRequestURI() + " accessed");

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}