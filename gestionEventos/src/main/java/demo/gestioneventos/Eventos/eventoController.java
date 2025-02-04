package demo.gestioneventos.Eventos;

import demo.gestioneventos.Eventos.Evento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/eventos")
public class eventoController {

    eventoRepository eventoRepository;

    @Autowired
    public eventoController(eventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    //GET --> SELECT *
    @GetMapping("/getEventos")
    public ResponseEntity<List<Evento>> getEventos() {
        List<Evento> lista = this.eventoRepository.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }


    //GET EVENTO BY ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Evento> getEventoJSON(@PathVariable int id) {
        Evento e = this.eventoRepository.findById(id).get();
        return ResponseEntity.ok(e);
    }

    //POST de un Objeto evento
    @PostMapping("/evento")
    public ResponseEntity<Evento> addEvento(@Valid @RequestBody Evento evento) {
        Evento persistedEvento = this.eventoRepository.save(evento);
        return ResponseEntity.ok().body(persistedEvento);
    }

    //POST con Form Normal
    @PostMapping(value = "/eventoForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Evento> addEventoForm(

            @RequestParam String nombre,
            @RequestParam LocalDate fechaEvento,
            @RequestParam int entradasDisponibles,
            @RequestParam double precio

    ) {
        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setFechaEvento(fechaEvento);
        evento.setEntradasDisponibles(entradasDisponibles);
        evento.setPrecio(precio);

        this.eventoRepository.save(evento);
        return ResponseEntity.created(null).body(evento);
    }

    //PUT --> UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable int id, @RequestBody Evento evento) {
        //Verificar si el usuario existe
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //Asegurarse de que el id recibido coincide con el id del usuario
        evento.setId(id);

        //Guardar el usuario actualizado
        Evento persistedEvento = eventoRepository.save(evento);
        return ResponseEntity.ok().body(persistedEvento);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvento(@PathVariable int id) {
        eventoRepository.deleteById(id);
        String mensaje = "Evento con id: " + id + " eliminado";
        return ResponseEntity.ok().body(mensaje);
    }
}
