package com.bashkarsampath.app.services.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOauth2UserCustomRole extends CustomOAuth2User {
	private final Collection<? extends GrantedAuthority> customAuthorities;
	private final OAuth2AccessToken oAuth2AccessToken;
	
	public CustomOauth2UserCustomRole(OAuth2User oauth2User, OAuth2AccessToken oAuth2AccessToken,
			Collection<? extends GrantedAuthority> customAuthorities) {
		super(oauth2User);
		this.oAuth2AccessToken = oAuth2AccessToken;
		this.customAuthorities = customAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return customAuthorities;
	}

	public TokenType getAccessTokenType() {
		if (oAuth2AccessToken != null)
			return oAuth2AccessToken.getTokenType();
		else
			return null;
	}

	public String getAccessTokenValue() {
		if (oAuth2AccessToken != null)
			return oAuth2AccessToken.getTokenValue();
		else
			return null;
	}
}
