/**
 * 
 */
package com.TeachCode.E_commerce.Services;

/**
 * 
 */
public interface EmailSenderService {
	void sendEmail(String to, String subject, String htmlContent);
}
