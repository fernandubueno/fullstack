package br.com.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fullstack.dao.MusicaDAO;
import br.com.fullstack.model.Musica;

@RestController
@CrossOrigin("*")
public class MusicaController {

	@Autowired
	private MusicaDAO dao;

	@PostMapping("/novamusica")
	public ResponseEntity<Musica> add(@RequestBody Musica objeto) {
		try {
			dao.save(objeto);
			return ResponseEntity.ok(objeto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}

	@GetMapping("/musicas")
	public ResponseEntity<List<Musica>> getAll() {
		List<Musica> lista = (List<Musica>) dao.findAll();
		if (lista.size() == 0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
	}

}
