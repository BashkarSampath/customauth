package com.bashkarsampath.app.services.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bashkarsampath.app.entities.Roles;
import com.bashkarsampath.app.entities.User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;
	private final boolean registerAnonymousUsersToAsNewUsersOnDb;
	private final String userRole;

	public CustomOAuth2UserService(UserService userService, boolean registerAnonymousUsersToAsNewUsersOnDb,
			String userRole) {
		this.userService = userService;
		this.registerAnonymousUsersToAsNewUsersOnDb = registerAnonymousUsersToAsNewUsersOnDb;
		this.userRole = userRole;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);
		User dbUser = userService
				.getDbUserOauth2PostLogin(customOAuth2User, registerAnonymousUsersToAsNewUsersOnDb,
				userRole);
		Set<GrantedAuthority> authorities = new HashSet<>(dbUser.getRoles().size());
		for (Roles role : dbUser.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("AUTHORITY_" + role.getName()));
		}
		return new CustomOauth2UserCustomRole(user, userRequest.getAccessToken(), authorities);
	}
}