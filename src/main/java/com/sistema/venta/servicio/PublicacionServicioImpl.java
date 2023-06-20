package com.sistema.venta.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.venta.dto.PublicacionDTO;
import com.sistema.venta.entidades.Publicacion;
import com.sistema.venta.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		// convertimos de DTO a entidad
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		
		//Convertimos de entidad a DTO
		
		PublicacionDTO publicacionRespuesta = new PublicacionDTO();
		publicacionRespuesta.setId(nuevaPublicacion.getId());
		publicacionRespuesta.setTitulo(nuevaPublicacion.getTitulo());
		publicacionRespuesta.setDescripcion(nuevaPublicacion.getDescripcion());
		publicacionRespuesta.setContenido(nuevaPublicacion.getContenido());
		
		return publicacionRespuesta;
	}

}
