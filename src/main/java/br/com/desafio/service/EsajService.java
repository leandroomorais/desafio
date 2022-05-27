package br.com.desafio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import br.com.desafio.model.Movimentacao;
import br.com.desafio.model.Parte;
import br.com.desafio.model.Processo;
import br.com.desafio.repository.ProcessoRepository;

@Service
public class EsajService {

	private static final String URL_BASE = "http://esaj.tjrn.jus.br/cpo/pg/open.do";
	private static final String CODIGO_PROCESSO = "0033765492008";
	private static final String FORO_NUMERO_UNIFICADO = "0001";
	private static final String INPUT_ANO_UNIFICADO = "numeroDigitoAnoUnificado";
	private static final String INPUT_FORO_UNIFICADO = "foroNumeroUnificado";

	private static final String PATERN_PROCESSO = "[0-9]{7}-[0-9]{2}.[0-9]{4}.[8].[0-9]{2}.[0-9]{4}";

	@Autowired
	private ProcessoRepository processoRepository;

	public void getContent() {
		try (WebClient webClient = new WebClient()) {
			try {
				HtmlPage htmlPage = webClient.getPage(URL_BASE);
				HtmlInput anoUnificado = htmlPage.getElementByName(INPUT_ANO_UNIFICADO);
				HtmlInput foroUnificadoo = htmlPage.getElementByName(INPUT_FORO_UNIFICADO);
				htmlPage = anoUnificado.click();
				anoUnificado.setValueAttribute(CODIGO_PROCESSO);
				foroUnificadoo.setValueAttribute(FORO_NUMERO_UNIFICADO);
				HtmlSubmitInput htmlSubmitInput = htmlPage.getElementByName("pbEnviar");
				htmlPage = htmlSubmitInput.click();
				Document document = Jsoup.parse(htmlPage.asXml());
				saveObject(document);
			} catch (FailingHttpStatusCodeException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveObject(Document document) {
		processoRepository.save(getDataProcess(document));
	}

	private String findByRegex(String patternString, String text) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "Sem informação";
	}

	private Processo getDataProcess(Document document) {
		Processo processo = new Processo();
		Element tableDadosProcesso = document.getElementsByClass("secaoFormBody").get(1);
		Element tbody = tableDadosProcesso.getElementsByTag("tbody").first();
		List<Element> atributos = tbody.getElementsByTag("td");
		processo.setCodigoProcesso(findByRegex(PATERN_PROCESSO, atributos.toString()));
		processo.setClasse(atributos.get(4).text());
		processo.setAssunto(atributos.get(10).text());
		processo.setLocalFisico(atributos.get(12).text());
		processo.setDistribuicao(atributos.get(14).text().concat(atributos.get(16).text()));
		processo.setValorAcao(atributos.get(18).text());
		processo.setPartes(getPartsProcess(document));
		processo.setMovimentacoes(getMovimentacoes(document));
		return processo;
	}

	private List<Parte> getPartsProcess(Document document) {
		Element tableParts = document.getElementsByAttributeValue("id", "tableTodasPartes").first();
		Element tbody = tableParts.getElementsByTag("tbody").first();
		List<Element> atributos = tbody.getElementsByTag("td");
		List<Parte> partes = new ArrayList<>();
		int index = 0;
		for (Element element : atributos) {
			if (index % 2 != 0) {
				Parte parte = new Parte();
				parte.setText(element.text());
				partes.add(parte);
			}
			index++;
		}
		return partes;
	}

	private List<Movimentacao> getMovimentacoes(Document document) {
		Element tableParts = document.getElementsByAttributeValue("id", "tabelaTodasMovimentacoes").first();
		Element tbody = tableParts.getElementsByTag("tbody").first();
		List<Element> atributos = tbody.getElementsByTag("td");
		List<Movimentacao> movimentacoes = new ArrayList<>();
		for (Element element : atributos) {
			Movimentacao movimentacao = new Movimentacao();
			movimentacao.setText(element.text());
			System.err.println(element.text());
			movimentacoes.add(movimentacao);
		}
		return movimentacoes;
	}
}
