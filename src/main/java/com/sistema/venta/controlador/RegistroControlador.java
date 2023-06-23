package com.sistema.venta.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegistroDTO;
import com.example.demo.servicio.RegistroServicio;

@RestController
@RequestMapping("/api/usuarios")
public class RegistroControlador {

	@Autowired
	private RegistroServicio registroServicio;
	
	@PostMapping
	public ResponseEntity<RegistroDTO> guardarRegistro(@RequestBody RegistroDTO registroDTO){
		return new ResponseEntity <> (registroServicio.crearRegistro(registroDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<RegistroDTO> listarRegistros(){
		return registroServicio.obtenerTodosLosRegistros();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegistroDTO> obtenerRegistroPorId(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(registroServicio.obtenerRegistroPorId(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RegistroDTO> actualizarRegistro(@RequestBody RegistroDTO registroDTO, @PathVariable(name = "id") long id){
		RegistroDTO registroRespuesta = registroServicio.actualizarRegistro(registroDTO, id);
		return new ResponseEntity<>(registroRespuesta, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarRegistro(@PathVariable(name = "id") long id){
		registroServicio.eliminarRegistro(id);
		return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
	}
}
