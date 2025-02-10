package demo.gestioneventos;

import demo.gestioneventos.Compras.Compra;
import demo.gestioneventos.Compras.compraRepository;
import demo.gestioneventos.Eventos.Evento;
import demo.gestioneventos.Eventos.eventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Service
public class gestionCompras {
    compraRepository compraRepository;
    eventoRepository eventoRepository;

    @Autowired
    public gestionCompras(demo.gestioneventos.Compras.compraRepository compraRepository, demo.gestioneventos.Eventos.eventoRepository eventoRepository) {
        this.compraRepository = compraRepository;
        this.eventoRepository = eventoRepository;
    }

    public gestionCompras() {
    }

    public demo.gestioneventos.Compras.compraRepository getCompraRepository() {
        return compraRepository;
    }

    public List<Compra> getCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(int id) {
        return compraRepository.findById(id).get();
    }

    public Compra comprarEntradas(Compra compra) {
        Evento e = eventoRepository.findById(compra.getIdEvento().getId()).get();
        if (e.getEntradasDisponibles() > 0) {
            if (e.getFechaEvento().isAfter(compra.getFechaCompra())) {
                e.setEntradasDisponibles(e.getEntradasDisponibles() - 1);
                eventoRepository.save(e);
                return compraRepository.save(compra);
            } else {
                throw new RuntimeException("No se puede comprar una entrada. El evento ya ha finalizado");
            }
        } else {
            throw new RuntimeException("El evento  está SOLD OUT");
        }

    }


    public Compra comprarEntradasParameters(String dni, String nombre, String apellido, LocalDate fecha_compra, String tarjeta, Evento idEvento) {
        Evento e = eventoRepository.findById(idEvento.getId()).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        if (e.getEntradasDisponibles() > 0) {
            if (e.getFechaEvento().isAfter(fecha_compra)) {
                Compra compra = new Compra();
                compra.setDni(dni);
                compra.setNombre(nombre);
                compra.setApellidos(apellido);
                compra.setFechaCompra(fecha_compra);
                compra.setNumeroTarjeta(tarjeta);
                compra.setIdEvento(e);

                e.setEntradasDisponibles(e.getEntradasDisponibles() - 1);
                eventoRepository.save(e);
                return compraRepository.save(compra);
            } else {
                throw new RuntimeException("No se puede comprar una entrada. El evento ya ha finalizado");
            }
        } else {
            throw new RuntimeException("El evento está SOLD OUT");
        }
    }


    public Compra updateCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    public void deleteCompra(int id) {
        Compra compra = compraRepository.findById(id).get();
        Evento e = eventoRepository.findById(compra.getIdEvento().getId()).get();
        if (e.getFechaEvento().isAfter(LocalDate.now())) {
            compraRepository.delete(compra);
            e.setEntradasDisponibles(e.getEntradasDisponibles() + 1);
            eventoRepository.save(e);
        } else throw new RuntimeException("Este evento ya ha finalizado, no se puede devolver la entrada");

    }
}
