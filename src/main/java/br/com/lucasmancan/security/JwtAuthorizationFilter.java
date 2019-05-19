package br.com.lucasmancan.security;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.lucasmancan.services.AppService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	
	private final AppUserDetailService userDetailService;
	

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,AppUserDetailService userDetailService) {
		super(authenticationManager);
		this.userDetailService = userDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException, java.io.IOException {
		
		var authentication = getAuthentication(request);
		var header = request.getHeader(SecurityConstants.TOKEN_HEADER);

		if (StringUtils.isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		var token = request.getHeader(SecurityConstants.TOKEN_HEADER);

		if (StringUtils.isNotEmpty(token)) {
			try {
				var signingKey = SecurityConstants.JWT_SECRET.getBytes();

				var parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));

				var user = parsedToken.getBody().getSubject();

				var authorities = ((List<?>) parsedToken.getBody().get("roles")).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());


				System.out.println(parsedToken.getBody());
//
////				/* Recuperando as informações do Token*/
//				AppService.ACCOUNT_ID = (long) parsedToken.getBody().get("accountId");
//				AppService.USER_ID = (long) parsedToken.getBody().get("appUserId");



				var jwtDetails = new HashMap<String, Object>();
				jwtDetails.put("account", parsedToken.getBody().get("accountId"));
				jwtDetails.put("user", parsedToken.getBody().get("appUserId"));


				if (StringUtils.isNotEmpty(user)) {

					var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
					usernamePasswordAuthenticationToken.setDetails(jwtDetails);

					return usernamePasswordAuthenticationToken;
				}
			} catch (ExpiredJwtException exception) {
				log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			}

		}

		return null;
	}
}
