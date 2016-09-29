package com.k.kpp.business.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模板引擎(Template Engine)
 * jsp freemarker velocity
 * @author IKIVIL
 */

@Controller
@RequestMapping("/tpl")
public class TplController {
	
	@RequestMapping(path="/helloJsp")
	public String helloJsp(Model model){
		model.addAttribute("title", "欢迎 jsp");
		model.addAttribute("content", "This is Jsp Content 这是 Jsp 内容");
		return "helloJsp";
	}
	
	@RequestMapping(path="/helloFtl")
	public String helloFtl(Model model){
		model.addAttribute("title", "欢迎 ftl");
		model.addAttribute("content", "This is Freemarker Content 这是 Freemarker 内容");
		return "helloFtl";
	}
	
	@RequestMapping(path="/helloVm")
	public String helloVm(Model model){
		model.addAttribute("title", "欢迎 vm");
		model.addAttribute("content", "This is Velocity Content 这是 Velocity 内容");
		return "helloVm";
	}
	

}
