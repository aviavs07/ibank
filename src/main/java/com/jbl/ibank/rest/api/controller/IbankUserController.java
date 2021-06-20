package com.jbl.ibank.rest.api.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import com.jbl.ibank.rest.api.model.IbankUser;
import com.jbl.ibank.rest.api.model.OnCreate;
import com.jbl.ibank.rest.api.service.IbankUserService;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ibankusers")
public class IbankUserController {

	private static final String MSG_SUCESS_INSERT = "JwtUser inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "JwtUser successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted JwtUser successfully.";
	private static final String MSG_ERROR = "Error.";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IbankUserService ibankUserService;


	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping
	public String index(Model model) {
		List<IbankUser> ibankUsers = ibankUserService.findAll();
		
		model.addAttribute("ibankUsers", ibankUsers);
		return "ibank/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			IbankUser ibankUser = ibankUserService.findOne(id);
			
			model.addAttribute("data", ibankUser);
		}
		return "ibank/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute IbankUser entity) {
		
		model.addAttribute("ibankuser", entity);
		return "ibank/form";
	}

	@PostMapping("/save")
	public String create(Model model, @Validated(OnCreate.class) @ModelAttribute("ibankuser") IbankUser entity, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		try {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("ibankuser", entity);
				return "ibank/form";
			}
			entity.setStatus(true);
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			entity.setCreatedDate(java.time.LocalDateTime.now());
			// create Basic Token credentials
			String tokenStr = entity.getMobileNumber() + ':' + entity.getPassword() + ':' + java.time.LocalDateTime.now();
			entity.setJwtToken(Base64.getEncoder().encodeToString(tokenStr.getBytes()));

			ibankUserService.save(entity);

			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/ibankusers";
	}

	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				IbankUser entity = ibankUserService.findOne(id);
				model.addAttribute("ibankuser", entity);
			}
		} catch (Exception e) {

			throw new ServiceException(e.getMessage());
		}
		return "ibank/edit";
	}

	@PostMapping("/update")
	public String updateUser(Model model, @ModelAttribute("ibankuser") IbankUser entity, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			if (bindingResult.hasErrors()) {
				
				return "ibank/edit";
			}
			IbankUser ibankUser = ibankUserService.findOne(entity.getIbankUserId());
			ibankUser.setFullName(entity.getFullName());
			ibankUser.setIbankUserEmail(entity.getIbankUserEmail());
			ibankUser.setNid(entity.getNid());
			ibankUser.setStatus(entity.isStatus());
			
			ibankUserService.save(ibankUser);

			redirectAttributes.addFlashAttribute("update", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			return "redirect:/ibankusers";
		}

		return "redirect:/ibankusers";
	}

	@GetMapping("/{id}/password")
	public String updatePassword(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				IbankUser ibankuser = ibankUserService.findOne(id);
				model.addAttribute("ibankuser", ibankuser);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "ibank/password_form";
	}

	@PostMapping("/password")
	public String updatePassword(@Valid @ModelAttribute IbankUser entity, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasFieldErrors("password")) {
			redirectAttributes.addFlashAttribute("useraccount", entity);
			redirectAttributes.addFlashAttribute("passwordError", "Please enter a valid password!");
			return "redirect:/ibankusers/" + entity.getIbankUserId() + "/password";
		}
		IbankUser ibankUser = ibankUserService.findOne(entity.getIbankUserId());
		ibankUser.setPassword(passwordEncoder.encode(entity.getPassword()));

		// create Basic Token credentials
			String tokenStr = ibankUser.getMobileNumber() + ':' + passwordEncoder.encode(entity.getPassword()) + ':' + java.time.LocalDateTime.now();
			ibankUser.setJwtToken(Base64.getEncoder().encodeToString(tokenStr.getBytes()));

			ibankUserService.save(ibankUser);

		return "redirect:/ibankusers/" + ibankUser.getIbankUserId();
	}

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
