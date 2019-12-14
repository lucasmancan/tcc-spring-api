package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.*;
import br.com.lucasmancan.exceptions.AppException;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.*;
import br.com.lucasmancan.repositories.TokenRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class TokenService extends AbstractService<AppUser> {

@Autowired
    private TokenRepository tokenRepository;

   public Token generateToken(AppUser user, Integer milliseconds){


       var token = new Token();

       UUID uuid = UUID.randomUUID();

       token.setActive(true);
       token.setCreatedAt(LocalDateTime.now());
       token.setToken(uuid.toString().substring(0, 8));
       token.setUser(user);

       if(milliseconds != null){
           token.setExpiresAt(LocalDateTime.now().plusSeconds(milliseconds / 1000));
       }

       return tokenRepository.save(token);
   }

    public Token invalidateToken(Token token, boolean idUsed){

        token.setActive(false);

        if(idUsed){
            token.setUsedAt(LocalDateTime.now());
        }

        return tokenRepository.save(token);
    }


    public Token getToken(String token) throws AppException {
        Token foundToken = tokenRepository.findByToken(token).orElse(null);


        if(foundToken == null){
            throw new AppException("Authenticação não encontrada.");
        }
        return foundToken;
    }
}


