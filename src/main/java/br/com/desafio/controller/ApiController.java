package br.com.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.desafio.model.Processo;
import br.com.desafio.repository.ProcessoRepository;

@RestController
@RequestMapping("/api/")
public class ApiController {

	@Autowired
	private ProcessoRepository processoRepository;

	@GetMapping
	public ResponseEntity<List<Processo>> getProcessos() {
		List<Processo> processos = processoRepository.findAll();
		if (processos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(processos);
	}
}
