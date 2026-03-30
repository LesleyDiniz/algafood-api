package com.diniz.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.diniz.algafood.domain.model.Cidade;
import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.model.Endereco;
import com.diniz.algafood.domain.model.Estado;
import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.repository.EstadoRepository;
import com.diniz.algafood.domain.service.CadastroCidadeService;
import com.diniz.algafood.domain.service.CadastroCozinhaService;
import com.diniz.algafood.domain.service.CadastroRestauranteService;
import com.diniz.algafood.util.DatabaseCleaner;
import com.diniz.algafood.util.ResourceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@BeforeEach
	public void setup() {
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveAtribuirId_QuandoCadastrarRestauranteComDadosCorretos() {
		
		var novoRestaurante = new Restaurante();
		novoRestaurante.setNome("Fazendinha");
		novoRestaurante.setCozinha(cadastroCozinha.buscar(1L).get());
		var endereco = new Endereco();
		endereco.setCep("12345-678");
		endereco.setLogradouro("Rua das Flores");
		endereco.setNumero("100");
		endereco.setComplemento("Apto 101");
		endereco.setBairro("Bairro Jardim");
		endereco.setCidade(cadastroCidade.buscar(1L).get());
		novoRestaurante.setEndereco(endereco);
		
		novoRestaurante = cadastroRestaurante.salvar(novoRestaurante);
		
		assertThat(novoRestaurante).isNotNull();
		assertThat(novoRestaurante.getId()).isNotNull();
	}
	
	private void prepararDados()  {
		carregaEstados();
		
		var cozinha1 = new Cozinha();
		cozinha1.setNome("Mineira");
		cadastroCozinha.salvar(cozinha1);
		
		var cidade1 = new Cidade();
		cidade1.setNome("Uberlândia");
		cidade1.setEstado(estadoRepository.findById(13L).get());
		
		cadastroCidade.salvar(cidade1);
		
	}
	
	private void carregaEstados() {
		String json = ResourceUtils.getContentFromResource("/json/estados.json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			Estado estados[] = objectMapper.readValue(json, Estado[].class);
			
			
			for(var estado : estados) 
				estadoRepository.save(estado);
			
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar estados.", e);
		}
	}

}
