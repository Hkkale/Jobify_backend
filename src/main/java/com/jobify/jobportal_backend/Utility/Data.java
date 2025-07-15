package com.jobify.jobportal_backend.Utility;

public class Data {

    public  static String getMessageBody(String otp, String name){; // dynamically generated OTP

        String htmlTemplate = """
        <div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9;'>
            <div style='max-width: 500px; margin: auto; background: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>
                <h2 style='color: #333333;'>Your OTP Code</h2>
                <p style='font-size: 16px; color: #555555;'>
                    Hello <strong>%s</strong>,<br><br>
                    Your One-Time Password (OTP) for verification is:
                </p>
                <div style='font-size: 24px; font-weight: bold; color: #007bff; margin: 20px 0;'>%s</div>
                <p style='font-size: 14px; color: #999999;'>
                    This OTP is valid for 5 minutes. Please do not share it with anyone.
                </p>
                <p style='font-size: 14px; color: #999999;'>Thanks,<br>Jobify</p>
            </div>
        </div>
        """;
        return String.format(htmlTemplate,name, otp);

    }
}
