package br.com.lucasmancan.services;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.lucasmancan.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	static void addAuthentication(HttpServletResponse response, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
				.compact();
		
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + JWT);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
		
		if (StringUtils.isNotEmpty(token)) {
			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.JWT_SECRET)
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (StringUtils.isNotEmpty(user)) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
	
}