package com.example.admin;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.admin.company.Company;
import com.example.admin.company.CompanyRepository;

@RunWith(SpringRunner.class)
//@WebMvcTest(CompanyController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

	@Autowired
	private MockMvc mvc;
	//param
	private Company company;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Before
	public void setUp() throws Exception{
		createCompany();
	}
	private void createCompany(){
		company = random(Company.class);
		company.setSeq(null);
	}
	
	@Test
	public void testCreateForm() throws Exception{
		mvc.perform(get("/companys/register")
				.contentType(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("company/register"))
				.andExpect(content().string(containsString("가맹점 등록")));
	}
	
	@Test
	public void testCreate() throws Exception{
		MultiValueMap<String, String> params = toMultiValueMap(company);
		mvc.perform(post("/companys").contentType(MediaType.APPLICATION_FORM_URLENCODED).params(params))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/companys"));
	}
	private MultiValueMap<String, String> toMultiValueMap(Object obj) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> objMap = BeanUtils.describe(obj);
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.setAll(objMap);
		return params;
	}
	
	@Test
	public void testList() throws Exception{
		final int count = 3;
		final List<Company> companys = randomListOf(count, Company.class, "seq");
		Iterable<Company> saveds = companyRepository.save(companys);
		
		mvc.perform(get("/companys")
				.contentType(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("company/list"))
				.andExpect(model().attribute("companys", saveds))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	public void testView() throws Exception{
		final Company saved = companyRepository.save(company);
		
		mvc.perform(get("/companys/{seq}", saved.getSeq())
				.contentType(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("company/view"))
				.andExpect(model().attribute("company", is(saved)));
	}
	
	
}
