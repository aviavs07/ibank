package com.jbl.ibank.rest.api.audit;


import java.util.Optional;

import com.jbl.ibank.rest.api.model.MyUserAccountDetails;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {

	@Override
   public Optional <Integer> getCurrentAuditor() {
		MyUserAccountDetails principal =(MyUserAccountDetails) SecurityContextHolder.getContext()
                                            .getAuthentication().getPrincipal();
        assert principal != null;
		return Optional.of(principal.getUserId());
        
   }

}
