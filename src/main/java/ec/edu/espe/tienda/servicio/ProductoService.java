package ec.edu.espe.tienda.servicio;

import ec.edu.espe.tienda.modelo.Producto;
import ec.edu.espe.tienda.excepcion.ProductoNoEncontradoException;
import ec.edu.espe.tienda.excepcion.ProductoInvalidoException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();

    private Long siguienteId = 1L;

    public ProductoService() {

        productos.add(new Producto(siguienteId++,
                "Base motor RH Ford Fiesta", 35.00, 10));

        productos.add(new Producto(siguienteId++,
                "Filtro de aceite Chevrolet Aveo", 8.50, 25));

        productos.add(new Producto(siguienteId++,
                "Bomba de agua Chevrolet", 42.00, 5));
    }

    // Consultar todos los productos
    public List<Producto> listarProductos() {
        return productos;
    }

    // Buscar producto por ID
    public Producto buscarProducto(Long id) {

        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(
                                "No existe un producto con ID: " + id));
    }

    // Registrar producto
    public Producto registrarProducto(Producto producto) {

        if (producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException(
                    "El precio debe ser mayor que cero");
        }

        if (producto.getStock() < 0) {
            throw new ProductoInvalidoException(
                    "El stock no puede ser negativo");
        }

        producto.setId(siguienteId++);

        productos.add(producto);

        return producto;
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {

        Producto producto = buscarProducto(id);

        productos.remove(producto);
    }
}