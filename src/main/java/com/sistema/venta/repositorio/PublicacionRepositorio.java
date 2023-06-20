package com.sistema.venta.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.venta.entidades.Publicacion;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long>{

}
