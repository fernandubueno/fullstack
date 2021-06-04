package br.com.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fullstack.dao.ArtistaDAO;
import br.com.fullstack.model.Artista;

@RestController
@CrossOrigin("*")
public class ArtistaController {

	@Autowired
	private ArtistaDAO dao;

	// Retornar todos os artistas
	@GetMapping("/todosartistas")
	public ResponseEntity<List<Artista>> getAll() {
		List<Artista> lista = (List<Artista>) dao.findAll();
		if (lista.size() == 0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
	}

	// Retornar o artista pelo ID
	@GetMapping("/artista/{codigo}")
	public ResponseEntity<Artista> getArtista(@PathVariable int codigo) {
		Artista objeto = dao.findById(codigo).orElse(null);
		if (objeto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(objeto);
	}

	// Gravar um novo artista
	@PostMapping("/novoartista")
	public ResponseEntity<Artista> add(@RequestBody Artista artista) {
		try {
			dao.save(artista);
			return ResponseEntity.ok(artista);
		} catch (Exception e) {
			// irá exibir uma exceção caso haja erro
			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}

	// Pesquisar por nacionalidade
	@PostMapping("/nacionalidade")
	public ResponseEntity<List<Artista>> getNacionalidade(@RequestBody Artista artista) {

		/*
		 * metodo de busca por nacionalidade aonde o processo no backend e muito custoso
		 * List<Artista> lista = (List<Artista>) dao.findAll(); List<Artista> resposta =
		 * new ArrayList<Artista>(); for (Artista obj : lista) { if
		 * (obj.getNacionalidade().equals(artista.getNacionalidade())) {
		 * resposta.add(obj); } }
		 */

		List<Artista> resposta = dao.findByNacionalidade(artista.getNacionalidade());
		if (resposta.size() == 0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(resposta);
	}

	// Apagar um artista
	@PostMapping("/apagarartista")
	public ResponseEntity<Boolean> deleteArtista(@RequestBody Artista artista) {
		try {
			dao.deleteById(artista.getId());
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}

}
