package com.wayne.service;

import com.wayne.model.BbsUser;

import java.util.List;






public interface  BbsUserService {


	/**
	 * Gets the user by name.
	 *
	 * @param username the user name
	 * @return the user by name
	 */
	BbsUser getUserByName(String username);

	

}
