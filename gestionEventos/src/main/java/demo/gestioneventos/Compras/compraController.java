package demo.gestioneventos.Compras;

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
@RequestMapping("/compras")
public class compraController {
    compraRepository compraRepository;

    @Autowired
    public compraController(compraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    //GET --> SELECT *
    @GetMapping("/getCompras")
    public ResponseEntity<List<Compra>> getCompras() {
        List<Compra> lista = this.compraRepository.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }


    //GET COMPRA BY ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Compra> getCompraJSon(@PathVariable int id) {
        Compra c = this.compraRepository.findById(id).get();
        return ResponseEntity.ok(c);
    }

    //POST de un Objeto compra
    @PostMapping("/compra")
    public ResponseEntity<Compra> addUsuario(@Valid @RequestBody Compra compra) {
        Compra persistedCompra = this.compraRepository.save(compra);
        return ResponseEntity.ok().body(persistedCompra);
    }

    //POST con Form Normal
    @PostMapping(value = "/compraForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Compra> addCompraForm(
            @RequestParam String dni,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam LocalDate fecha_compra,
            @RequestParam String tarjeta,
            @RequestParam Evento idEvento

    ) {
        Compra compra = new Compra();
        compra.setDni(dni);
        compra.setNombre(nombre);
        compra.setApellidos(apellido);
        compra.setFechaCompra(fecha_compra);
        compra.setNumeroTarjeta(tarjeta);
        compra.setIdEvento(idEvento);
        this.compraRepository.save(compra);
        return ResponseEntity.created(null).body(compra);
    }

    //PUT --> UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Compra> updateCompra(@PathVariable int id, @RequestBody Compra compra) {
        //Verificar si el usuario existe
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //Asegurarse de que el id recibido coincide con el id del usuario
        compra.setId(id);

        //Guardar el usuario actualizado
        Compra persistedCompra = compraRepository.save(compra);
        return ResponseEntity.ok().body(persistedCompra);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompra(@PathVariable int id) {
        compraRepository.deleteById(id);
        String mensaje = "Compra con id: " + id + " eliminada";
        return ResponseEntity.ok().body(mensaje);
    }


}
