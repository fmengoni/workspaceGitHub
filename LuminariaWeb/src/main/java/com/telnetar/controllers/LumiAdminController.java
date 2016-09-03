package com.telnetar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telnetar.services.LuminariaService;

@Controller
@RequestMapping(value="/lumiAdmin")
public class LumiAdminController {
	@Autowired
	LuminariaService luminariaService;
	
	@RequestMapping(value="/posicionarLuminarias")
	public String posicionarLuminarias(Model model){
		return "lumiAdmin/posicionarLuminarias";
	}
}