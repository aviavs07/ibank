package com.jbl.ibank.rest.api.audit;

import java.util.Optional;

import com.jbl.ibank.rest.api.model.IbankUserAccountDetails;
import com.jbl.ibank.rest.api.model.MyUserAccountDetails;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;



public class AuditRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {

		String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal).map(IbankUserAccountDetails.class::cast)
				.map(IbankUserAccountDetails::getUsername).orElse("Demo-User");

		AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
		audit.setLogUser(currentUser);

	}

	// @Override
	// public void newRevision(Object revisionEntity) {

	// 	String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
	// 			.map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
	// 			.map(Authentication::getPrincipal).map(MyUserAccountDetails.class::cast)
	// 			.map(MyUserAccountDetails::getUsername).orElse("Demo-User");

	// 	AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
	// 	audit.setLogUser(currentUser);

	// }

}
