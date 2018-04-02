package com.example.security;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class AuthenticationService {
	static final long EXPIRATIONTIME 	= 30*60*1000; // 30 minutes - Better to configure through file outside
	static final String SECRET 			= "ThisIsASecret"; // Better to configure through file outside
	
	static final String TOKEN_PREFIX 	= "Bearer";
	static final String HEADER_STRING 	= "Authorization";
	
	static final String SPACE_STRING 	= " ";
	static final String EMPTY_STRING 	= "";

	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + SPACE_STRING + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, EMPTY_STRING))
					.getBody()
					.getSubject();

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.<GrantedAuthority>emptyList()) : null;
		}
		return null;
	}
}
