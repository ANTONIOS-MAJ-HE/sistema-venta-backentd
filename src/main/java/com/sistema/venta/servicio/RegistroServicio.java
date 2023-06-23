package com.sistema.venta.servicio;

import java.util.List;

import com.sistema.venta.dto.RegistroDTO;

public interface RegistroServicio {

	public RegistroDTO crearRegistro(RegistroDTO registroDTO);
	
	public List<RegistroDTO> obtenerTodosLosRegistros();
	
	public RegistroDTO obtenerRegistroPorId(long id);
	
	public RegistroDTO actualizarRegistro(RegistroDTO registroDTO, long id);
	
	public void eliminarRegistro(long id);
}
