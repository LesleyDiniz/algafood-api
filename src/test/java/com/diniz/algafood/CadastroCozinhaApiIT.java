package com.diniz.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.repository.CozinhaRepository;
import com.diniz.algafood.util.DatabaseCleaner;
import com.diniz.algafood.util.ResourceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaApiIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	Cozinha[] cozinhas = {};
	Cozinha cozinhaTeste;
	final int COZINHA_ID_INEXISTENTE = 404;  
		
	@BeforeEach
	public void setup() {
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornar200_QuandoConsultarCozinhas() {
		given().accept(ContentType.JSON)
			.when().get()
			.then().statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterCozinhasInseridas_QuandoConsultarCozinhas() {
		given().accept(ContentType.JSON)
			.when().get()
			.then()
				.body("", hasSize(this.cozinhas.length));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		String novaCozinha = ResourceUtils.getContentFromResource("/json/cozinhaSalvar.json");
		given()
			.body(novaCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when().post()
		.then().statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", cozinhaTeste.getId())
			.accept(ContentType.JSON)
		.when().get("/{cozinhaId}")
		.then().statusCode(HttpStatus.OK.value()).body("nome", equalTo(cozinhaTeste.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when().get("/{cozinhaId}")
		.then().statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados()  {
		String json = ResourceUtils.getContentFromResource("/json/cozinhas.json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			this.cozinhas = objectMapper.readValue(json, Cozinha[].class);
			
			
			for(var cozinha : cozinhas) 
				cozinhaRepository.save(cozinha);
			
			cozinhaTeste = cozinhas[3];
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar cozinhas.", e);
		}
		
	}
	
}
