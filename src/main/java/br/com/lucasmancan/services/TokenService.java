package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.*;
import br.com.lucasmancan.exceptions.AppException;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.*;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TokenService extends AbstractService<AppUser> {

   public Token generateToken(AppUser user, Integer milliseconds){


       var token = new Token();

       token.setActive(true);
       token.setCreatedAt(new Date());

       if(milliseconds != null){
           token.setExpiresAt(new Date(new Date().getTime() + milliseconds));
       }



   }
}


