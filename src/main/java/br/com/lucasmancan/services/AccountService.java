package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.AccountDTO;
import br.com.lucasmancan.dtos.RegisterForm;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.PasswordDoenstMatchException;
import br.com.lucasmancan.exceptions.RegisterAlreadyExistsException;
import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService extends AbstractService<Account> {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account entity) {
        return repository.save(entity);
    }

    public void remove(Account entity) {
        repository.delete(entity);
    }

    public Account findByCode(Long code) throws AppNotFoundException {
        return repository.findById(code).orElseThrow(AppNotFoundException::new);
    }

    public Page<Account> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List findAll() {
        return repository.findAll().stream().map(account -> {
            var dto = new AccountDTO();
            dto.setId(account.getId());
            dto.setName(account.getName());

            return dto;
        }).collect(Collectors.toList());
    }

    public Account findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    public void register(RegisterForm form) throws AppNotFoundException, RegisterAlreadyExistsException, PasswordDoenstMatchException {

        Account account = new Account();
        AppUser user = new AppUser();


        if(form.getAccountId() != null){
            account = repository.findById(form.getAccountId()).orElseThrow(() -> new AppNotFoundException("Account not found"));
        }else{

            var accountVerification = repository.findByName(form.getAccountName()).orElse(null);

            if(accountVerification != null){
                throw new RegisterAlreadyExistsException("Account already exists.", form.getAccountName());
            }

            account.setActive(true);
            account.setCreatedAt(new Date());
            account.setName(account.getName());
            account.setUpdatedAt(new Date());
        }


            verifyUserAccount(account, form.getEmail());

            user.setAccount(account);
            user.setActive(true);
            user.setCreatedAt(new Date());
            user.setName(form.getName());
            user.setDocument(form.getDocument());
            user.setPassword(this.updatePassword(form.getPassword(), form.getPasswordConfirmation()));


           user = userRepository.save(user);

           if(form.getAccountId() == null){
               account.setAdmin(user);
               repository.save(account);
           }
    }

    private void verifyUserAccount(Account account, String email) throws RegisterAlreadyExistsException {
        if(account.getId() != null){
          return;
        }

       var user =  userService.findByAccountIdAndUserEmail(account.getId(), email).orElse(null);

        if(user != null) throw  new RegisterAlreadyExistsException("User already exists!", email);


    }

    private String updatePassword(String password, String passwordConfirmation) throws PasswordDoenstMatchException {
        if(!password.equals(passwordConfirmation)){
            throw new PasswordDoenstMatchException();
        }

        return passwordEncoder.encode(password);
    }
}
