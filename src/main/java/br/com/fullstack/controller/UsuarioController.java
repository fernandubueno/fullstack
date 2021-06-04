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

import br.com.fullstack.dao.UsuarioDAO;
import br.com.fullstack.model.Usuario;

@RestController // para que a classe responda as requisições HTTP
@CrossOrigin("*") // permite que a controller receba requisições externas
public class UsuarioController {

	@Autowired // transfere para o SpringBoot a responsabilidade sobre este objeto
	private UsuarioDAO dao;

	// Realizando a pesquisa por parte do nome
	@PostMapping("/pesquisanome")
	public ResponseEntity<List<Usuario>> getUserName(@RequestBody Usuario usuario) {
		List<Usuario> lista = dao.findByNomeLike("%" + usuario.getNome() + "%");
		if (lista.size() == 0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
	}

	/*
	 * Get -> método para ações simples, retorno para grande conjunto de dados. Get
	 * SEMPRE os dados são passados pela "rota", por meio de
	 * variáveis @PathVariable.
	 */
	@GetMapping("/loginget/{e}/{s}")
	public ResponseEntity<Usuario> logar(@PathVariable String e, @PathVariable String s) {
		Usuario resposta = dao.findByEmailAndSenha(e, s);
		if (resposta == null) {
			return ResponseEntity.status(404).build();
		}
		resposta.setSenha("");
		return ResponseEntity.ok(resposta);
	}

	/*
	 * Post -> métodos para ações onde os dados são enviados dentro do pacote
	 * (envelope) Post SEMPRE terá no body o conjunto de dados (JSON)
	 * 
	 * @RequestBody -> que recupera o objeto.
	 */
	@PostMapping("/login")
	public ResponseEntity<Usuario> logar(@RequestBody Usuario usuario) {
		Usuario resposta = dao.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if (resposta == null) {
			return ResponseEntity.status(404).build();
		}
		resposta.setSenha("");
		return ResponseEntity.ok(resposta);
	}

	@PostMapping("/novousuario")
	public ResponseEntity<Usuario> add(@RequestBody Usuario usuario) {
		try {
			dao.save(usuario);
			return ResponseEntity.ok(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}

	@GetMapping("/usuario/{var}")
	public ResponseEntity<Usuario> getUser(@PathVariable int var) {
		Usuario resposta = dao.findById(var).orElse(null);
		if (resposta == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(resposta);
	}

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> lista = (List<Usuario>) dao.findAll();
		if (lista.size() == 0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
	}

}
