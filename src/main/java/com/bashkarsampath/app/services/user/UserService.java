package com.bashkarsampath.app.services.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bashkarsampath.app.entities.Roles;
import com.bashkarsampath.app.entities.User;
import com.bashkarsampath.app.enums.Provider;
import com.bashkarsampath.app.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository repo;

	@Transactional
	public User getDbUserOauth2PostLogin(CustomOAuth2User customOAuth2User, boolean saveAsNewUser, String userRole) {
		Optional<User> optionalUser = repo.getUserByUsername(customOAuth2User.getEmail());
		if (optionalUser.isPresent())
			return optionalUser.get();
		else {
			if (!saveAsNewUser) {
				String accessDeniedLog = "Username: " + customOAuth2User + " not found. Access Denied";
				log.info(accessDeniedLog);
				throw new UsernameNotFoundException(accessDeniedLog);
			} else {
				User newUser = new User();
				newUser.setUsername(customOAuth2User.getEmail());
				newUser.setProvider(Provider.GOOGLE);
				newUser.setEnabled(true);
				newUser.setFullname(customOAuth2User.getName());
				Set<Roles> roleSet = new HashSet<>();
				Roles roles = new Roles();
				roles.setName(userRole);
				roleSet.add(roles);
				newUser.setRoles(roleSet);
				newUser = repo.save(newUser);
				log.info("Created new user: " + customOAuth2User);
				return newUser;
			}
		}
	}
}