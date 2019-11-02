package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.dtos.PasswordConfirmation;
import br.com.lucasmancan.dtos.UserAccountInformation;
import br.com.lucasmancan.exceptions.AppException;
import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import br.com.lucasmancan.services.AccountService;
import br.com.lucasmancan.services.EmailService;
import br.com.lucasmancan.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;import java.util.Date;

@RestController
@RequestMapping("/api/public-auth")
@Log4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getMessage() {
        return "API is running...";
    }

    @PostMapping("verify-email")
    @ResponseBody
    public ResponseEntity verifyEmail(@RequestBody UserAccountInformation userAccountInformation) {
        try{
            userService.verifyEmail(userAccountInformation);
            return ResponseEntity.ok(new AppResponse(false, "", null));
        }catch (AppException e){

            return ResponseEntity.ok(new AppResponse(false, e.getMessage(), null));
        } catch (Exception e){
            log.warn(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AppResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("verify-token")
    @ResponseBody
    public ResponseEntity verifyToken(@RequestBody String token) {
        try{

            userService.verifyToken(token);
            return ResponseEntity.ok(new AppResponse(false, "", null));
        }catch (AppException e){

            return ResponseEntity.ok(new AppResponse(false, e.getMessage(), null));
        } catch (Exception e){
            log.warn(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AppResponse(false, e.getMessage(), null));
        }

    }

    @PostMapping("sign-up")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody UserAccountInformation user) {
        try{

            userService.signUp(user);
            return ResponseEntity.ok(new AppResponse(false, "", null));
        }catch (AppException e){

            return ResponseEntity.ok(new AppResponse(false, e.getMessage(), null));
        } catch (Exception e){
            log.warn(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AppResponse(false, e.getMessage(), null));
        }


    }

    @PostMapping("change-password")
    @ResponseBody
    public ResponseEntity changePassword(@RequestBody PasswordConfirmation passwordConfirmation) {
        try{

            userService.changePassword(passwordConfirmation);
            return ResponseEntity.ok(new AppResponse(false, "", null));
        }catch (AppException e){

            return ResponseEntity.ok(new AppResponse(false, e.getMessage(), null));
        } catch (Exception e){
            log.warn(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AppResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/save")
    @ResponseBody
    public ResponseEntity saveUser() {


        var account = new Account();
        account.setActive(true);
        account.setCreatedAt(new Date());

        account = accountRepository.save(account);

        var user = new AppUser("lucas",
                new BCryptPasswordEncoder().encode("root"));

        user.setAccount(account);
        user.setActive(true);
        user.setExpired(false);
        user.setAdmin(true);

        return new ResponseEntity(repository.save(user), HttpStatus.CREATED);
    }


}