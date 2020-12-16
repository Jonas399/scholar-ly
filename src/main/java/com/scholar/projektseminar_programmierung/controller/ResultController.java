package com.scholar.projektseminar_programmierung.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholar.projektseminar_programmierung.exception.ResourceNotFoundException;
import com.scholar.projektseminar_programmierung.model.Result;
import com.scholar.projektseminar_programmierung.repository.ResultRepository;



@RestController
@RequestMapping("/api/v1")
public class ResultController {
	
	@Autowired 
	public ResultRepository resultRepository;
	
	//get results
	@GetMapping("results")
	public List<Result> getAllResult() {
		return this.resultRepository.findAll(); 
	}
	
	//get result by id
	@GetMapping("results/{id}")
	public ResponseEntity<Result> getResultById(@PathVariable(value="id") Long resultId) throws ResourceNotFoundException {
		Result result = resultRepository.findById(resultId).orElseThrow(() -> new ResourceNotFoundException("Result not found for this id :: "+resultId));
		return ResponseEntity.ok().body(result);
	}
	
	//save result
	@PostMapping("results")
	public Result createResult(@RequestBody Result result) {
		System.out.println("Preparing to save result");
		return this.resultRepository.save(result);
	}
	
	
	/*
	//update result
	public ResponseEntity<Result> updateResult(@PathVariable(value="id") Long resultId, @Valid @RequestBody)
	*/
	
	//get result by id
	@DeleteMapping("results/{id}")
	public Map<String, Boolean> deleteResult(@PathVariable(value="id") Long resultId) throws ResourceNotFoundException {
		Result result = resultRepository.findById(resultId).orElseThrow(() -> new ResourceNotFoundException("Result not found for this id :: "+resultId));
		
		this.resultRepository.delete(result);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
	
}
