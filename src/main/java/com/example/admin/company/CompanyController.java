package com.example.admin.company;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@GetMapping("/companys/register")
	public String createForm(Model model){
		model.addAttribute("company", new Company());
		return "company/register";
	}
	
	@PostMapping("/companys")
	public String create(@Valid Company company, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "company/register";
		}
		
		companyRepository.save(company);
		return "redirect:/companys";
	}
	
	@GetMapping("/companys")
	public String list(Model model){
		Iterable<Company> companys = companyRepository.findAll();
		model.addAttribute("companys", companys);
		return "company/list";
	}
	
	@GetMapping("/companys/{seq}")
	public String view(@PathVariable("seq") Long seq, Model model){
		Company company = companyRepository.findOne(seq);
		model.addAttribute("company", company);
		return "company/view";
	}

}
