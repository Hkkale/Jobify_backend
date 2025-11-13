package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.LoginDto;
import com.jobify.jobportal_backend.DTOs.NotificationDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.DTOs.UserDto;
import com.jobify.jobportal_backend.Entity.OTP;
import com.jobify.jobportal_backend.Entity.User;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.NotificationRepository;
import com.jobify.jobportal_backend.Repository.OTPRepository;
import com.jobify.jobportal_backend.Repository.UserRepository;
import com.jobify.jobportal_backend.Utility.Data;
import com.jobify.jobportal_backend.Utility.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Utilities utilities;

    private PasswordEncoder passwordEncoder;

    private JavaMailSender javaMailSender;
    private OTPRepository otpRepository;

    private  ProfileService profileService;

    private NotificationService notificationService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, Utilities utilities, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, OTPRepository otpRepository, ProfileService profileService, NotificationRepository notificationRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.utilities = utilities;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.otpRepository = otpRepository;
        this.profileService = profileService;
        this.notificationService = notificationService;

    }


    @Override
    public UserDto registerUser(UserDto userDto) throws JobPortalException {


        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());

        if (optional.isPresent()) throw new JobPortalException("USER_FOUND");


        userDto.setProfileId(profileService.createProfile(userDto.getEmail(),userDto.getName(),userDto.getAccountType()));

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
    public UserDto getUserByEmail(String email) throws JobPortalException {


        return  userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND")).toDto();



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

    @Override
    public ResponseDto changePassword(LoginDto loginDto) throws JobPortalException {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        userRepository.save(user);

        NotificationDto notf =new NotificationDto();
        notf.setUserId(user.getId());
        notf.setMessage("Password Reset Successfully");
        notf.setAction("Password Reset");

        notificationService.sendNotification(notf);

        return new ResponseDto("Password changed successfully.");
    }


   @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs(){
        LocalDateTime expiry=LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOtps=otpRepository.findByCreationTimeBefore(expiry);

        if(!expiredOtps.isEmpty()){
            otpRepository.deleteAll(expiredOtps);
            System.out.println("Removed "+expiredOtps.size()+" expired otps");
        }



    }


}
