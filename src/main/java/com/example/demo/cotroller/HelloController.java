package com.example.demo.cotroller;

import com.example.demo.model.AnswerRequest;
import com.example.demo.model.AnswerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@RestController
public class HelloController {

	@PostMapping(value = "/check")
	public AnswerResponse home(@RequestBody AnswerRequest request) {
		AnswerResponse res = new AnswerResponse(request.isRight());
		return res;
	}
}
