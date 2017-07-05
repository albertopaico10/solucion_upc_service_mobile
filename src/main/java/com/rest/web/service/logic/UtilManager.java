package com.rest.web.service.logic;

import org.springframework.stereotype.Service;

@Service
public interface UtilManager {
	public boolean isSendEmailFromOtherAccount(String email);
}
