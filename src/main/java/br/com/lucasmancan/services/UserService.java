package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.*;
import br.com.lucasmancan.exceptions.AppException;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.*;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.TokenRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;import java.util.*;


@Service
public class UserService extends AbstractService<AppUser> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;


    public AppUser convert(AppUserDTO dto) {
        return this.convert(dto, null);
    }

    public AppUser convert(AppUserDTO dto, AppUser user) {

        if (user == null) {
            user = new AppUser();
        }

        user.setActive(dto.getActive());
        user.setDocument(dto.getDocument());
        user.setName(dto.getName());
        user.setType(dto.getType());

        for (AddressDTO addressDTO : dto.getAddresses()) {

            final var userAddress = new UserAddress();
            userAddress.setCity(addressDTO.getCity());
            userAddress.setCountry(addressDTO.getCountry());
            userAddress.setState(addressDTO.getState());
            userAddress.setStreet(addressDTO.getStreet());
            userAddress.setNumber(addressDTO.getNumber());
            userAddress.setZipCode(addressDTO.getZipCode());
            userAddress.setAppUser(user);

            if (user.getAddresses() == null) {
                user.setAddresses(new HashSet<>());
            }

            user.getAddresses().add(userAddress);
        }

        for (PhoneDTO phoneDTO : dto.getPhones()) {
            final var userPhone = new UserPhone();

            userPhone.setAreaCode(phoneDTO.getAreaCode());
            userPhone.setCountryCode(phoneDTO.getCountryCode());
            userPhone.setPhoneNumber(phoneDTO.getPhoneNumber());
            userPhone.setType(phoneDTO.getContactType());
            userPhone.setAppUser(user);

            if (user.getPhones() == null) {
                user.setPhones(new HashSet<>());
            }

            user.getPhones().add(userPhone);
        }


        for (EmailDTO emailDTO : dto.getEmails()) {
            final var userEmail = new UserEmail();

            userEmail.setAppUser(user);
            userEmail.setEmail(emailDTO.getEmail());
            userEmail.setType(emailDTO.getContactType());

            if (user.getEmails() == null) {
                user.setEmails(new HashSet<>());
            }

            user.getEmails().add(userEmail);
        }


        return user;
    }

    public AppUserDTO convert(AppUser user) {

        var dto = new AppUserDTO();

        dto.setActive(user.getActive());
        dto.setDocument(user.getDocument());
        dto.setName(user.getName());
        dto.setType(user.getType());

        for (UserAddress address : user.getAddresses()) {
            final var userAddress = new AddressDTO();

            userAddress.setCity(address.getCity());
            userAddress.setCountry(address.getCountry());
            userAddress.setState(address.getState());
            userAddress.setStreet(address.getStreet());
            userAddress.setNumber(address.getNumber());
            userAddress.setZipCode(address.getZipCode());

            if (user.getAddresses() == null) {
                dto.setAddresses(new ArrayList<AddressDTO>());
            }

            dto.getAddresses().add(userAddress);
        }

        for (UserPhone userPhone : user.getPhones()) {
            final var phoneDTO = new PhoneDTO();

            phoneDTO.setAreaCode(userPhone.getAreaCode());
            phoneDTO.setCountryCode(userPhone.getCountryCode());
            phoneDTO.setPhoneNumber(userPhone.getPhoneNumber());
            phoneDTO.setContactType(userPhone.getType());

            if (dto.getPhones() == null) {
                dto.setPhones(new ArrayList<>());
            }

            dto.getPhones().add(phoneDTO);
        }


        for (UserEmail userEmail : user.getEmails()) {
            final var emailDTO = new EmailDTO();


            emailDTO.setEmail(userEmail.getEmail());
            emailDTO.setContactType(userEmail.getType());

            if (dto.getEmails() == null) {
                dto.setEmails(new ArrayList<>());
            }

            dto.getEmails().add(emailDTO);
        }

        return dto;
    }


    public AppUserDTO save(AppUserDTO dto) throws AppNotFoundException {

        var user = convert(dto);

        if(user.getStatus() == null){
            user.setStatus(Status.active);
        }

        user.setAccount(getLoggedAccount());
        user.setUpdatedAt(new Date());
        user = repository.save(user);

        return convert(user);
    }

    public void remove(Long code) throws AppNotFoundException {
        var user = find(code);


            user.setStatus(Status.inactive);

        repository.save(user);
    }


    public AppUser find(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
    }

    public AppUserDTO findByCode(Long code) throws AppNotFoundException {
        var user = find(code);

        return convert(user);
    }

    public List<AppUser> findAll() {
        return repository.findAll();
    }

    public Optional<AppUser> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<AppUser> findByName(String name) {
        return repository.findByName(name);
    }



    public AppUserDTO update(Long code, AppUserDTO userDTO) throws AppNotFoundException {

        var user = find(userDTO.getCode());

        user = convert(userDTO, user);

        return convert(user);
    }

    public Optional<AppUser> findByAccountIdAndUserEmail(Long id, String email) {
        return repository.findByAccountIdAndUserEmail(id,email);
    }

    public void verifyEmail(UserAccountInformation userAccountInformation) throws AppException {

        var user = repository.findByEmail(userAccountInformation.getEmail());

        if(user.isPresent()){

            var token = tokenService.generateToken(user.get(), 1000 * 60 * 60 * 30);

            emailService.send("customer-verify-email", userAccountInformation.getEmail(), token);
        }
    }


    public boolean verifyToken(String token) throws AppException {

        Token foundToken = tokenRepository.findByToken(token).orElse(null);


        if(foundToken == null){
            return false;
        }

        if(foundToken.getExpiresAt() != null && foundToken.getExpiresAt().before(new Date())){
            return false;
        }

        return true;
    }

    public void signUp(UserAccountInformation userAccountInformation) throws AppException {

        if(!userAccountInformation.matchPassword()){
            throw new AppException("Senhas não coincidem.");
        }

        if( repository.findByEmail(userAccountInformation.getEmail()).isPresent()){
            throw new AppException("Usuário já existe.");
        }

        AppUser newUser = new AppUser();

        newUser.setEmail(userAccountInformation.getEmail());
        newUser.setUsername(userAccountInformation.getUsername());
        newUser.setPassword(passwordEncoder.encode(userAccountInformation.getPassword()));
        newUser.setStatus(Status.pending);
        newUser.setName(userAccountInformation.getUsername());


        newUser = repository.save(newUser);

        Account account  = new Account();
        account.setActive(true);
        account.setCreatedAt(new Date());
        account.setName(userAccountInformation.getUsername());
        account.setUpdatedAt(new Date());
        account.setAdmin(newUser);

        Account account1 = accountRepository.save(account);


        var token = tokenService.generateToken(newUser, null);

        emailService.send("customer-verify-signup", newUser.getEmail(), token);

    }

    public void activateUser(String token) throws AppException {

        var foundToken = tokenService.getToken(token);

        foundToken.getUser().setStatus(Status.active);

        repository.save(foundToken.getUser());

        tokenService.invalidateToken(foundToken, true);
    }

    public void changePassword(PasswordConfirmation passwordConfirmation) throws AppException {

        Token foundToken = tokenService.getToken(passwordConfirmation.getToken());

        AppUser user = findById(foundToken.getUser().getId()).orElseThrow(() -> new AppException("Usuário não encontrado."));

        if(!passwordConfirmation.matchPassword()){
            throw new AppException("Senhas não coincidem.");
        }

        if( passwordEncoder.matches(user.getPassword(), passwordConfirmation.getConfirmation())){
            throw new AppException("A nova senha não pode ser igual a anterior.");

        }
        user.setPassword(passwordEncoder.encode(passwordConfirmation.getPassword()));

        tokenService.invalidateToken(foundToken, true);


        getEntityManager().merge(user);
    }

}


