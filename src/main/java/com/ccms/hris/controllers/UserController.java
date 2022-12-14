package com.ccms.hris.controllers;

import com.ccms.hris.enums.UserStatus;
import com.ccms.hris.models.ResponseWrapper;
import com.ccms.hris.models.dto.UserCreationDto;
import com.ccms.hris.models.dto.UserDto;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.services.UserService;
import com.ccms.hris.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping("/hello")
    public String helloTest (){
        return "Hello World";
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(user, "success", "fetched"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper(null, "not found", "No user found"));
        }
    }

    @GetMapping("/")
    public ResponseEntity getAllUsers(
            @RequestParam(name = "pageNo", defaultValue = "1", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int offset
    ) {
        try {
            Page<UserDto> users = userService.getAllUsers(page, offset);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(users, "success", "fetched"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper(null, "not found", "No user found"));
        }
    }

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserCreationDto user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(user, "success", "created"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper(user, "failed", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserCreationDto user, @PathVariable Long id) {
        try {

            userService.updateUser(user, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(user, "success", "updated"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("failed", "failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper("failed", "failed", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("", "success", "deleted"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper("", "failed", e.getMessage()));
        }
    }


    @GetMapping("/filter")
    public ResponseWrapper getByStatusAndId(
            @RequestParam(name = "q", defaultValue = "", required = false) String q,
            @RequestParam(name = "userStatus",  required = false) UserStatus userStatus
    ) {

        try{
            List<User> users = userService.filterUsers(q, userStatus);
            return new ResponseWrapper<>(users, "success", "fetched");
        }catch (Exception e){
            return new ResponseWrapper<>(null, "failed", e.getMessage());
        }
    }


    @PostMapping("/{id}/status")
    public ResponseEntity updateUserStatus(@PathVariable long id,
                                           @RequestParam(name = "userStatus",  required = true) UserStatus userStatus

    ) {
        try {
            userService.updateUserStatus(id, userStatus);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("success", "success", "updated"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper("failed", "failed", e.getMessage()));
        }
    }


}
