package br.com.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

}
