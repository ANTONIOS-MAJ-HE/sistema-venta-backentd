package com.sistema.venta.servicio;

import java.awt.JobAttributes.DestinationType;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema.venta.dto.PublicacionDTO;
import com.sistema.venta.dto.PublicacionRespuesta;
import com.sistema.venta.entidades.Publicacion;
import com.sistema.venta.exepciones.ResourceNotFoundException;
import com.sistema.venta.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private ModelMapper modelMapper;

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
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		PageRequest pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDto(publicacion))
				.collect(Collectors.toList());

		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());

		return publicacionRespuesta;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		return mapearDto(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDto(publicacionActualizada);
	}

	public void eliminarPublicacion(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacionRepositorio.delete(publicacion);
	}

	// Convierte entidad a dto
	private PublicacionDTO mapearDto(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
		return publicacionDTO;
	}

	// convierte de dto a entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}

}
