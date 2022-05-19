package com.pp.app.service.implementation;

import com.pp.app.io.entity.AddressEntity;
import com.pp.app.io.entity.PasswordResetTokenEntity;
import com.pp.app.io.entity.UserEntity;
import com.pp.app.io.repositories.PasswordResetTokenRepository;
import com.pp.app.io.repositories.UserRepository;
import com.pp.app.service.EmailService;
import com.pp.app.service.UserService;
import com.pp.app.ui.model.response.ErrorMessages;
import com.pp.app.ui.shared.DTO.AddressDTO;
import com.pp.app.ui.shared.DTO.UserDto;
import com.pp.app.ui.shared.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDto createUser(UserDto userDto) {

        userRepository.findByEmail(userDto.getEmail()).ifPresent( user -> {
            throw new RuntimeException("Record already exists!");
        });

        //BeanUtils.copyProperties(userDto, userEntity);
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));
        userDto.setEmailVerificationToken(userEntity.getEmailVerificationToken());

        for (int i = 0; i < userEntity.getAddresses().size(); i++) {
            AddressEntity addressEntity = modelMapper.map(userDto.getAddresses().get(i), AddressEntity.class);
            addressEntity.setUserDetails(userEntity);
            addressEntity.setAddressId(utils.generateAddressId(30));
            userEntity.getAddresses().set(i, addressEntity);
        }

        UserEntity storedUserDetails = userRepository.save(userEntity);


        //BeanUtils.copyProperties(storedUserDetails, returnValue);
        modelMapper.addMappings(new PropertyMap<AddressDTO, AddressDTO>() {
            @Override
            protected void configure() {
                skip(destination.getUserDto());
            }
        });
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
        emailService.sendEmail(userDto);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException(email);
        });

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        });
        UserDto returnValue = new ModelMapper().map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        });
        if (userDto.getFirstName() != null && userDto.getFirstName() != userEntity.getFirstName())
            userEntity.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null && userDto.getLastName() != userEntity.getLastName())
            userEntity.setLastName(userDto.getLastName());

        userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        });
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page>0) page -= 1;

        Pageable pageable = PageRequest.of(page,limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity: users){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity,userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);

        if (userEntity != null){
            boolean hasTokenExpired = Utils.hasTokenExpired(token);
            if (!hasTokenExpired){
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userEntity.setEmailVerificationToken(null);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty())
            return returnValue;

        String token = utils.generatePasswordResetToken(userEntity.get().getUserId());
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserEntity(userEntity.get());
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = emailService.sendPasswordResetEmail(userEntity.get().getFirstName(),
                userEntity.get().getEmail(),
                token);

        return returnValue;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;

        if (Utils.hasTokenExpired(token))
            return returnValue;

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token).orElseThrow( () ->{
            throw new RuntimeException("No such a token");
        });

        String enncodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity userEntity = passwordResetTokenEntity.getUserEntity();
        userEntity.setEncryptedPassword(enncodedPassword);
        UserEntity saveUserEntity = userRepository.save(userEntity);

        if (saveUserEntity != null && saveUserEntity.getEncryptedPassword().equalsIgnoreCase(enncodedPassword))
            returnValue = true;

        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->{
            throw new UsernameNotFoundException(email);
        });

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(),
                true, true, true,
                new ArrayList<>());

        //return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
