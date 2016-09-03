package com.telnetar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.telnetar.Util;
import com.telnetar.exceptions.TelnetarException;
import com.telnetar.model.User;
import com.telnetar.services.SystemService;
import com.telnetar.services.UserService;
import com.telnetar.view.model.ViewUser;

@Controller
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	SystemService systemService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "users/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(@ModelAttribute User user, Model model) {
		// implement your own registration logic here...

		// for testing purpose:
		user.setPassword(Util.hashPassword(user.getPassword()));
		try{
			userService.register(user);
			model.addAttribute("successMessage", "El usuario fue registrado con éxito");
		}catch (DataIntegrityViolationException e) {
			model.addAttribute("errorMessage", "El usuario seleccionado ya se encuentra registrado en la base de datos");
		}
		return "users/register";
	}
	
	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	public String listUsers(Model model){
		model.addAttribute("lsUsers", userService.findAll());
		return "users/listUsers";
	}
	
	@RequestMapping(value = "/editUser/{pkUser}", method = RequestMethod.GET)
	public String editUser(@PathVariable Long pkUser, Model model){
		User user = userService.findOne(pkUser);
		model.addAttribute("viewUser", setViewUserData(user));
		return "users/editUser";
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String editUser(@ModelAttribute ViewUser viewUser, Model model){
		User user = null;
		try{
			user = userService.findOne(viewUser.getPk());
			
			if(viewUser.getClaveNueva().length() > 0 || viewUser.getRepitaClaveNueva().length() > 0){
				if(!viewUser.getClaveNueva().equals(viewUser.getRepitaClaveNueva())){
					throw new TelnetarException("Las nuevas claves no son iguales");
				}else if(viewUser.getClaveNueva().trim().length() < 8){
					throw new TelnetarException("La clave no puede tener menos de 8 caracteres");
				}
				user.setPassword(Util.hashPassword(viewUser.getClaveNueva()));
			}
			user.setEnabled(viewUser.getEnabled() ? new Integer(1) : new Integer(0));
			
			userService.update(user);
			model.addAttribute("successMessage", "Los datos se modificaron con éxito");
		}catch (TelnetarException e) {
			model.addAttribute("errorMessage", e.getMsg());
		}
		model.addAttribute("viewUser", setViewUserData(user));
		return "users/editUser";
	}
	
	private ViewUser setViewUserData(User user){
		ViewUser viewUser = new ViewUser();
		viewUser.setPk(user.getPk());
		viewUser.setUsuario(user.getUsername());
		viewUser.setClaveActual(null);
		viewUser.setClaveNueva(null);
		viewUser.setRepitaClaveNueva(null);
		viewUser.setEnabled(user.getEnabled().equals(new Integer(1)) ? Boolean.TRUE : Boolean.FALSE);
		return viewUser;
	}
}
