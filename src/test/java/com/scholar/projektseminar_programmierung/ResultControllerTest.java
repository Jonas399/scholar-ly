package com.scholar.projektseminar_programmierung;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import com.scholar.projektseminar_programmierung.controller.ResultController;
import com.scholar.projektseminar_programmierung.model.Result;
import com.scholar.projektseminar_programmierung.repository.ResultRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResultControllerTest {
	
	@Autowired
	ResultRepository repository;
	
	@Autowired 
	ResultController controller;
	
	static Result result;
	
	@BeforeAll
	static void createResult() {
		result = new Result("natural AND disaster", 2015, 2015, "69de02736440f3f2252629653b808be1");
	}
	
	@BeforeEach
	public void deleteRepositoryContent() {
		repository.deleteAll();
		repository.flush();
	}
	
	@Test
	@Order(1)
	void testRestControllerIsNotNull() {
		assertThat(controller).isNotNull();
	}
	
	@Test 
	@Order(2)
	void testResultsOfEmptyController(){
		assertThat(controller.getAllResult()).contains();
	}
	
	@Test
	@Order(3)
	void testSavingResults() {
		controller.createResult(result);
		
		List<Result> repositoryResult = repository.findAll();
		
		assertThat(repositoryResult.get(0).getTerm()).isEqualTo(result.getTerm());
	}
}
