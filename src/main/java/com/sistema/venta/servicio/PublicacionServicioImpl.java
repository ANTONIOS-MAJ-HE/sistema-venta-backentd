package com.sistema.venta.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.venta.dto.PublicacionDTO;
import com.sistema.venta.entidades.Publicacion;
import com.sistema.venta.exepciones.ResourceNotFoundException;
import com.sistema.venta.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = mapearEntidad(publicacionDTO);

		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

		PublicacionDTO publicacionRespuesta = mapearDto(nuevaPublicacion);
		return publicacionRespuesta;
	}

	@Override
	public List<PublicacionDTO> obtenerTodasLasPublicaciones(){
		List<Publicacion> publicaciones = publicacionRepositorio.findAll();
		return publicaciones.stream().map(publicacion -> mapearDto(publicacion)).collect(Collectors.toList());
		
	}
	//Convierte entidad a dto
	private PublicacionDTO mapearDto(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = new PublicacionDTO();
		publicacionDTO.setId(publicacion.getId());
		publicacionDTO.setTitulo(publicacion.getTitulo());
		publicacionDTO.setDescripcion(publicacion.getDescripcion());
		publicacionDTO.setContenido(publicacion.getContenido());

		return publicacionDTO;
	}

	// convierte de dto a entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = new Publicacion();

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());

		return publicacion;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id){
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id" , id));
		return mapearDto(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id){
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id" , id));

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDto(publicacionActualizada);
	}

	public void eliminarPublicacion(long id){
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id" , id));

		publicacionRepositorio.delete(publicacion);
	}

}
