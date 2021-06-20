package com.jbl.ibank.rest.api.controller;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.model.OnCreate;
import com.jbl.ibank.rest.api.service.JwtUserService;
import com.jbl.ibank.rest.api.service.UserAccountService;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jwtusers")
public class JwtUserController {

	private static final String MSG_SUCESS_INSERT = "JwtUser inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "JwtUser successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted JwtUser successfully.";
	private static final String MSG_ERROR = "Error.";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JwtUserService jwtuserService;

	// @Autowired
	// private CompanyService companyService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserAccountService userAccountService;

	// @Autowired
	// private ResourceService resourceService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping
	public String index(Model model) {
		List<JwtUser> jwtUsers = jwtuserService.findAll();
		
		model.addAttribute("jwtUsers", jwtUsers);
		return "jwtuser/index";
	}


	// @GetMapping("/{id}")
	// public String show(Model model, @PathVariable("id") Integer id) {
	// 	if (id != null) {
	// 		JwtUser jwtuser = jwtuserService.findOne(id).get();
	// 		UserAccount createdUser = userAccountService.findOne(jwtuser.getCreatedBy()).get();
	// 		UserAccount updatedUser;
	// 		if (jwtuser.getUpdatedBy() == 0) {
	// 			updatedUser = new UserAccount();
	// 			updatedUser.setFirstName("---");
	// 			updatedUser.setLastName("");
	// 		} else {
	// 			updatedUser = userAccountService.findOne(jwtuser.getUpdatedBy()).get();
	// 		}
	// 		model.addAttribute("data", new UniversalWraper<>(jwtuser, createdUser, updatedUser));
	// 	}
	// 	return "jwtuser/show";
	// }

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute JwtUser entity) {
		// get company data from company table
		// List<Company> comList = companyService.findBycompanyIdGreaterThan(1);
		// List<Resource> resList = resourceService.findAll();
		// model.addAttribute("companies", comList);
		// model.addAttribute("resources", resList);
		model.addAttribute("jwtuser", entity);
		return "jwtuser/form";
	}

	@PostMapping("/save")
	public String create(Model model, 
			@Validated(OnCreate.class) @ModelAttribute("jwtuser") JwtUser entity, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			if (bindingResult.hasErrors()) {
				// List<Company> comList = companyService.findAll();
				// List<Resource> resList = resourceService.findAll();
				// model.addAttribute("companies", comList);
				// model.addAttribute("resources", resList);
				// model.addAttribute("current_companyId", companyId);
				model.addAttribute("jwtuser", entity);
				return "jwtuser/form";
			}
			entity.setStatus(true);
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			// create Basic Token credentials
			String tokenStr = entity.getUsername() + ':' + entity.getPassword() + ':' + java.time.LocalDateTime.now();
			entity.setBasicToken(Base64.getEncoder().encodeToString(tokenStr.getBytes()));
			entity.setCreatedDate(LocalDateTime.now());

			jwtuserService.save(entity);

			// // create an object for user resource
			// for (int resId : resourceId) {
			// 	Resource resource = resourceService.findOne(resId).get();
			// 	// add user to this resource
			// 	resource.addJwtUser(entity);
			// 	resourceService.save(resource);
			// }
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/jwtusers";
	}

	// @GetMapping("/{id}/edit")
	// public String update(Model model, @PathVariable("id") Integer id) {
	// 	try {
	// 		if (id != null) {
	// 			JwtUser entity = jwtuserService.findOne(id).get();
	// 			model.addAttribute("jwtuser", entity);

	// 			Company company = companyService.findOne(entity.getCompany().getCompanyId()).get();
	// 			int companyId = company.getCompanyId();
	// 			model.addAttribute("current_companyId", companyId);
	// 			List<Company> comList = companyService.findBycompanyIdGreaterThan(1);
	// 			model.addAttribute("companies", comList);

	// 			List<Resource> resList = resourceService.findAll();
	// 			model.addAttribute("resources", resList);
		
	// 		}
	// 	} catch (Exception e) {

	// 		throw new ServiceException(e.getMessage());
	// 	}
	// 	return "jwtuser/edit";
	// }

	// @PostMapping("/edit")
	// public String updateUser(Model model, @ModelAttribute("jwtuser") JwtUser entity,
	// 		@RequestParam(value = "resources", required = false) int[] resourceId, BindingResult bindingResult,
	// 		RedirectAttributes redirectAttributes) {

	// 	try {
	// 		if (bindingResult.hasErrors()) {
	// 			Company company = companyService.findOne(entity.getCompany().getCompanyId()).get();
	// 			int companyId = company.getCompanyId();
	// 			model.addAttribute("current_companyId", companyId);

	// 			List<Company> comList = companyService.findAll();
	// 			model.addAttribute("companies", comList);

	// 			List<Resource> resList = resourceService.findAll();
	// 			model.addAttribute("resources", resList);

	// 			return "jwtuser/edit";
	// 		}

	// 		JwtUser jwtUser = jwtuserService.findOne(entity.getJwtUserId()).get();
	// 		jwtUser.removeAllResource(resourceService.findAll());
	// 		jwtUser.setCompany(entity.getCompany());
	// 		jwtUser.setSessionTime(entity.getSessionTime());
	// 		jwtUser.setStatus(entity.isStatus());
	// 		jwtUser.setWhitelistIp(entity.getWhitelistIp());
			

	// 		jwtuserService.save(jwtUser);

	// 		// create an object for user resource
	// 		for (int resId : resourceId) {

	// 			Resource resource = resourceService.findOne(resId).get();
	// 			// add user to this resource
	// 			resource.addJwtUser(jwtUser);
	// 			resourceService.save(resource);
	// 		}
	// 		redirectAttributes.addFlashAttribute("update", MSG_SUCESS_UPDATE);
	// 	} catch (Exception e) {
	// 		redirectAttributes.addFlashAttribute("error", MSG_ERROR);
	// 		return "redirect:/jwtusers";
	// 	}

	// 	return "redirect:/jwtusers";
	// }

	// @GetMapping("/{id}/password")
	// public String updatePassword(Model model, @PathVariable("id") Integer id) {
	// 	try {
	// 		if (id != null) {
	// 			JwtUser jwtUser = jwtuserService.findOne(id).get();
	// 			model.addAttribute("jwtuser", jwtUser);
	// 		}
	// 	} catch (Exception e) {
	// 		throw new ServiceException(e.getMessage());
	// 	}
	// 	return "jwtuser/password_form";
	// }

	// @PostMapping("/password")
	// public String updatePassword(@Valid @ModelAttribute JwtUser entity, BindingResult bindingResult,
	// 		RedirectAttributes redirectAttributes) {

	// 	if (bindingResult.hasFieldErrors("password")) {
	// 		redirectAttributes.addFlashAttribute("useraccount", entity);
	// 		redirectAttributes.addFlashAttribute("passwordError", "Please enter a valid password!");
	// 		return "redirect:/jwtusers/" + entity.getJwtUserId() + "/password";
	// 	}
	// 	JwtUser jwtUser = jwtuserService.findOne(entity.getJwtUserId()).get();
	// 	jwtUser.setPassword(passwordEncoder.encode(entity.getPassword()));

	// 	// create Basic Token credentials
	// 		String tokenStr = jwtUser.getUsername() + ':' + passwordEncoder.encode(entity.getPassword()) + ':' + java.time.LocalDateTime.now();
	// 		jwtUser.setBasicToken(Base64.getEncoder().encodeToString(tokenStr.getBytes()));

	// 	jwtuserService.save(jwtUser);

	// 	return "redirect:/jwtusers/" + jwtUser.getJwtUserId();
	// }

	// @DeleteMapping("/{id}")
	// public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	// 	try {
	// 		if (id != null) {
	// 			JwtUser entity = jwtuserService.findOne(id).get();
	// 			jwtuserService.delete(entity);
	// 			redirectAttributes.addFlashAttribute("delete", MSG_SUCESS_DELETE);
	// 		}
	// 	} catch (Exception e) {
	// 		redirectAttributes.addFlashAttribute("error", MSG_ERROR);
	// 		throw new ServiceException(e.getMessage());
	// 	}
	// 	return "redirect:/jwtusers/index";
	// }

	// @GetMapping("/history/{id}")
	// public String getInfo(@PathVariable("id") Integer id, Model model) {
	// 	if (id != null) {
	// 		JwtUser jwtUser = jwtuserService.findOne(id).get();
	// 		AuditQuery query = AuditReaderFactory.get(entityManager).createQuery()
	// 				.forRevisionsOfEntity(JwtUser.class, false, true).add(AuditEntity.id().eq(id));

	// 		List<EntityWithRevision<JwtUser>> entities = ((List<Object[]>) query.getResultList()).stream()
	// 				.map(obj -> new EntityWithRevision<>((AuditRevisionEntity) obj[1], (JwtUser) obj[0],
	// 						((RevisionType) obj[2]).name()))
	// 				.collect(Collectors.toList());

	// 		model.addAttribute("entities", entities);
	// 		model.addAttribute("jwtUser", jwtUser);
	// 	}
	// 	return "jwtuser/history";
	// }

}
