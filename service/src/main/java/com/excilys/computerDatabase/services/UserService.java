package com.excilys.computerDatabase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.excilys.computerDatabase.daos.UserDao;
import com.excilys.computerDatabase.models.User;

@Service
public class UserService extends ServiceDao<User> implements UserDetailsService{
	private UserDao userDao;
	@Autowired
    public UserService(UserDao userDao) {
		super(userDao);
        this.userDao = userDao;
    }
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUserbyUserName(username);
       if(user ==null) {
    	   throw new UsernameNotFoundException("username not found");
       }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleName())
                .build();
	}
	public User createUser(User user) throws UnexpectedRollbackException{
		return userDao.create(user);
	}
	
	public boolean updatePassword(String username,String newPass) {
		return userDao.updatePassword(username, newPass);
	}
	
}
