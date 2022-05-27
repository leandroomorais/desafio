package br.com.desafio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigoProcesso;

	private String classe;

	private String assunto;

	private String localFisico;

	private String distribuicao;

	private String valorAcao;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "processo_partes", joinColumns = @JoinColumn(name = "id_processo"), inverseJoinColumns = @JoinColumn(name = "id_partes"))
	private List<Parte> partes;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "processo_movimentacoes", joinColumns = @JoinColumn(name = "id_processo"), inverseJoinColumns = @JoinColumn(name = "id_movimentacao"))
	private List<Movimentacao> movimentacoes;

	public Processo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoProcesso() {
		return codigoProcesso;
	}

	public void setCodigoProcesso(String codigoProcesso) {
		this.codigoProcesso = codigoProcesso;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getLocalFisico() {
		return localFisico;
	}

	public void setLocalFisico(String localFisico) {
		this.localFisico = localFisico;
	}

	public String getDistribuicao() {
		return distribuicao;
	}

	public void setDistribuicao(String distribuicao) {
		this.distribuicao = distribuicao;
	}

	public String getValorAcao() {
		return valorAcao;
	}

	public void setValorAcao(String valorAcao) {
		this.valorAcao = valorAcao;
	}

	public List<Parte> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

}
