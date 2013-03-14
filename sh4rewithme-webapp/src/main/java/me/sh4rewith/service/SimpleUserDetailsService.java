package me.sh4rewith.service;


import java.util.Collection;

import me.sh4rewith.domain.RegistrationStatus;
import me.sh4rewith.domain.UserInfo;
import me.sh4rewith.persistence.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.collect.Sets;

public class SimpleUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfo info = usersRepository.getUserInfoById(username);
		if (info == null) {
			return null;
		}

		// FIXME Add support for stored authorities
		Collection<SimpleGrantedAuthority> authorities =
				Sets.newHashSet(new SimpleGrantedAuthority("ROLE_USER"));
		return new User(
				info.getId(),
				info.getCredentials(),
				info.getRegistrationStatus() == RegistrationStatus.REGISTERED,
				true,
				true,
				true,
				authorities);
	}

}