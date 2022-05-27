package br.com.desafio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import br.com.desafio.service.EsajService;

@SpringBootApplication
public class DesafioApplication implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private EsajService esajService;

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		esajService.getContent();
	}
}
