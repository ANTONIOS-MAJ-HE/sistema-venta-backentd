package com.sistema.venta.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sistema.venta.repositorio.RegistroRepository;
import com.sistema.venta.dto.RegistroDTO;
import com.sistema.venta.entidades.Registro;
import com.sistema.venta.excepciones.ResourceNotFoundException;

@Service
public class RegistroServicioImpl implements RegistroServicio {

	@Autowired
	private RegistroRepository registroRepository;

	@Override
	public RegistroDTO crearRegistro(RegistroDTO registroDTO) {

		Registro registro = mapearEntidad(registroDTO);

		Registro nuevoRegistro = registroRepository.save(registro);

		RegistroDTO registroRespuesta = mapearDTO(nuevoRegistro);
		return registroRespuesta;
	}

	@Override
	public List<RegistroDTO> obtenerTodosLosRegistros() {
		List<Registro> registros = registroRepository.findAll();
		return registros.stream().map(registro -> mapearDTO(registro)).collect(Collectors.toList());
	}

	// Convierte ENTIDAD a DTO
	private RegistroDTO mapearDTO(Registro registro) {

		RegistroDTO registroDTO = new RegistroDTO();

		registroDTO.setId(registro.getId());
		registroDTO.setNombre(registro.getNombre());
		registroDTO.setApellido(registro.getApellido());
		registroDTO.setCorreo(registro.getCorreo());
		registroDTO.setPassword(registro.getPassword());
		registroDTO.setDni(registro.getDni());

		return registroDTO;

	}

	// Convierte de DTO a ENTIDAD
	private Registro mapearEntidad(RegistroDTO registroDTO) {

		Registro registro = new Registro();

		registro.setId(registroDTO.getId());
		registro.setNombre(registroDTO.getNombre());
		registro.setApellido(registroDTO.getApellido());
		registro.setCorreo(registroDTO.getCorreo());
		registro.setPassword(registroDTO.getPassword());
		registro.setDni(registroDTO.getDni());

		return registro;
	}

	@Override
	public RegistroDTO obtenerRegistroPorId(long id) {
		Registro registro = registroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro", "id", id));
		return mapearDTO(registro);
	}

	@Override
	public RegistroDTO actualizarRegistro(RegistroDTO registroDTO, long id) {
		Registro registro = registroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro", "id", id));

		registro.setCorreo(registroDTO.getCorreo());
		registro.setPassword(registroDTO.getPassword());
		
		Registro registroActualizado = registroRepository.save(registro);
		return mapearDTO(registroActualizado);
	}

	@Override
	public void eliminarRegistro(long id) {
		Registro registro = registroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro", "id", id));
		registroRepository.delete(registro);
	}
}
