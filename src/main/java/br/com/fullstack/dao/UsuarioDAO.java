package br.com.fullstack.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fullstack.model.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Integer> {

	// DAO (é um PATTERN - CRUD em uma classe)
	// Gravar - Consultar - Excluir - ALterar

	// Não é necessário usar o "public"
	public List<Usuario> findByNomeLike(String nome);

	Usuario findByEmailAndSenha(String email, String senha);

}
