package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.LoginDto;
import com.jobify.jobportal_backend.DTOs.UserDto;
import com.jobify.jobportal_backend.Entity.OTP;
import com.jobify.jobportal_backend.Entity.User;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.OTPRepository;
import com.jobify.jobportal_backend.Repository.UserRepository;
import com.jobify.jobportal_backend.Utility.Data;
import com.jobify.jobportal_backend.Utility.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Utilities utilities;

    private PasswordEncoder passwordEncoder;

    private JavaMailSender javaMailSender;
    private OTPRepository otpRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, Utilities utilities, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, OTPRepository otpRepository) {
        this.userRepository = userRepository;
        this.utilities = utilities;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.otpRepository = otpRepository;
    }


    @Override
    public UserDto registerUser(UserDto userDto) throws JobPortalException {


        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());

        if (optional.isPresent()) throw new JobPortalException("USER_FOUND");


        userDto.setId(utilities.getNextSequence("users"));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userDto.toEntity();
        user = userRepository.save(user);
        return user.toDto();

    }

    @Override
    public UserDto loginUser(LoginDto loginDto) throws JobPortalException {

        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new JobPortalException("INVALID_CREDENTIALS");
        return user.toDto();
    }

    @Override
    public Boolean sendOtp(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true);
        message.setTo(email);
        message.setSubject("Your OTP Code");
        String genOTp = Utilities.generateOTP();
        OTP otp = new OTP(email, genOTp, LocalDateTime.now());
        otpRepository.save(otp);
        message.setText(Data.getMessageBody(genOTp,user.getName()) ,true);
        javaMailSender.send(mm);




        return true;

    }

    @Override
    public Boolean verifyOtp(String email, String otp) throws JobPortalException {

        OTP otpEntity= otpRepository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));

        if(!otpEntity.getOtpCode().equals(otp)){
            throw new JobPortalException("OTP_INCORRECT");
        }



        return true;
    }


}
