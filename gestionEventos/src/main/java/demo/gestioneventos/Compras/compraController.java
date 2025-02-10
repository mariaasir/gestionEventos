package demo.gestioneventos.Compras;

import demo.gestioneventos.Eventos.Evento;
import demo.gestioneventos.gestionCompras;
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
    gestionCompras gestionCompras = new gestionCompras();


    @Autowired
    public compraController(demo.gestioneventos.gestionCompras gestionCompras) {
        this.gestionCompras = gestionCompras;
    }

    public compraController() {
    }




    //GET --> SELECT *
    @GetMapping("/getCompras")
    public ResponseEntity<List<Compra>> getCompras() {

        return ResponseEntity.ok(gestionCompras.getCompras());
    }


    //GET COMPRA BY ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Compra> getCompraJSon(@PathVariable int id) {
        return ResponseEntity.ok(gestionCompras.getCompraById(id));
    }

    //POST de un Objeto compra
    @PostMapping("/comprar")
    public ResponseEntity<Compra> addCompra(@Valid @RequestBody Compra compra) {
        return ResponseEntity.ok().body(gestionCompras.comprarEntradas(compra));
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

        return ResponseEntity.created(null).body(gestionCompras.comprarEntradasParameters(dni,nombre,apellido,fecha_compra,tarjeta,idEvento));
    }

    //PUT --> UPDATE
    @PutMapping("/update{id}")
    public ResponseEntity<Compra> updateCompra(@PathVariable int id, @RequestBody Compra compra) {
        //Verificar si el usuario existe
        if (!gestionCompras.getCompras().contains(id)) {
            return ResponseEntity.notFound().build();
        }

        //Asegurarse de que el id recibido coincide con el id del usuario
        compra.setId(id);
        return ResponseEntity.ok().body(gestionCompras.updateCompra(compra));
    }

    //DELETE
    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> deleteCompra(@PathVariable int id) {
        gestionCompras.deleteCompra(id);
        String mensaje = "Compra con id: " + id + " eliminada";
        return ResponseEntity.ok().body(mensaje);
    }


}
