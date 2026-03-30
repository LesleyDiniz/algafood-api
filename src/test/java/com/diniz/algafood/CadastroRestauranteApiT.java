package com.diniz.algafood;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.repository.RestauranteRepository;
import com.diniz.algafood.util.DatabaseCleaner;
import com.diniz.algafood.util.ResourceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteApiT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	Restaurante[] restaurante = {};
	Restaurante restauranteTeste;
	
	@BeforeEach
	public void setup() {
		RestAssured.basePath = "/restaurantes";
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornar200_QuandoConsultarRestaurantes() {
		given().accept(ContentType.JSON)
			.when().get()
			.then().statusCode(HttpStatus.OK.value());
	}
	
	
	private void prepararDados()  {
		String json = ResourceUtils.getContentFromResource("/json/restaurantes.json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			this.restaurante = objectMapper.readValue(json, Restaurante[].class);
			
			
			for(var cozinha : restaurante) 
				restauranteRepository.save(cozinha);
			
			restauranteTeste = restaurante[3];
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar restaurantes.", e);
		}
		
	}

}
