package io.github.isan95.accenturetest.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSesion {
	
	public static String getCurrentUsername() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
		  return username = ((UserDetails)principal).getUsername();
		} else {
		   return username = principal.toString();
		}
	}
}
