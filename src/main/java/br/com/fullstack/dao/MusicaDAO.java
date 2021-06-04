package br.com.fullstack.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.fullstack.model.Musica;

public interface MusicaDAO extends CrudRepository<Musica, Integer> {

}
