package com.scholar.projektseminar_programmierung;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.scholar.projektseminar_programmierung.model.Result;
import com.scholar.projektseminar_programmierung.repository.ResultRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResultRepositoryTest {
	
	@Autowired
	private ResultRepository resRep;

	static Result result;
	
	@BeforeAll
	static void createResult() {
		result = new Result("natural AND disaster", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
	}
	
	@BeforeEach
	public void deleteRepositoryContent() {
		resRep.deleteAll();
		resRep.flush();
	}
	
	@Test
	@Order(1)
	void testResultRepositoryIfEmpty() {
		assertThat(resRep.count()).isEqualTo(0);
	}
	
	@Test
	@Order(2)
	void testResultRepositoryResultIsSaved() {
		resRep.save(result);
		
		assertThat(resRep.count()).isEqualTo(1);
	}
	
	@Test
	@Order(3)
	void testResultRespositoryContentSearchTerm() {
		resRep.save(result);
		
		List<Result> repositoryResult = resRep.findAll();

		assertThat(repositoryResult.get(0).getTerm()).isEqualTo(result.getTerm());
	}
	
	@Test
	@Order(4)
	void testResultRespositoryContentYearBegin() {
		resRep.save(result);
		
		List<Result> repositoryResult = resRep.findAll();

		assertThat(repositoryResult.get(0).getYear_begin()).isEqualTo(result.getYear_begin());
	}
	
	@Test
	@Order(5)
	void testResultRespositoryContentYearEnd() {
		resRep.save(result);
		
		List<Result> repositoryResult = resRep.findAll();

		assertThat(repositoryResult.get(0).getYear_end()).isEqualTo(result.getYear_end());
	}
}
