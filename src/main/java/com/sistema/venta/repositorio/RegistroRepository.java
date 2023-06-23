package com.sistema.venta.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.venta.entidades.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long>{

}
