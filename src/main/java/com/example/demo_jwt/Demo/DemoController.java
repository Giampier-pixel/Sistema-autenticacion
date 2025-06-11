package com.example.demo_jwt.Demo;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_jwt.User.User;
import com.example.demo_jwt.User.UserRepository;
import com.example.demo_jwt.User.Producto;
import com.example.demo_jwt.User.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo_jwt.User.Role;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {

    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/v1/demo")
    public String welcome(){
        return "Welcome form secure endpoint";
    }

    // ===== ENDPOINTS PARA USUARIOS =====
    @GetMapping(value = "usuarios")
    public List<User> getUsuarios(){
        return userRepository.findAll();
    }

    @PostMapping(value = "usuarios")
    public ResponseEntity<User> createUsuario(@RequestBody CreateUserRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .build();
        
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping(value = "usuarios/{id}")
    public ResponseEntity<User> updateUsuario(@PathVariable Integer id, @RequestBody UpdateUserRequest request){
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        User user = userOptional.get();
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCountry(request.getCountry());
        
        // Solo actualizar password si se proporciona
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id){
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS PARA PRODUCTOS =====
    @GetMapping(value = "productos")
    public List<Producto> getProductos(){
        return productoRepository.findAll();
    }

    @PostMapping(value = "productos")
    public ResponseEntity<Producto> createProducto(@RequestBody CreateProductRequest request){
        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .categoria(request.getCategoria())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .descripcion(request.getDescripcion())
                .build();
        
        Producto savedProducto = productoRepository.save(producto);
        return ResponseEntity.ok(savedProducto);
    }

    @PutMapping(value = "productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody UpdateProductRequest request){
        Optional<Producto> productoOptional = productoRepository.findById(id);
        
        if (productoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Producto producto = productoOptional.get();
        producto.setNombre(request.getNombre());
        producto.setCategoria(request.getCategoria());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setDescripcion(request.getDescripcion());
        
        Producto updatedProducto = productoRepository.save(producto);
        return ResponseEntity.ok(updatedProducto);
    }

    @DeleteMapping(value = "productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id){
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        productoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS ADICIONALES PARA PRODUCTOS =====
    @GetMapping(value = "productos/categoria/{categoria}")
    public List<Producto> getProductosByCategoria(@PathVariable String categoria){
        return productoRepository.findByCategoria(categoria);
    }

    @GetMapping(value = "productos/buscar/{nombre}")
    public List<Producto> buscarProductos(@PathVariable String nombre){
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @GetMapping(value = "productos/stock-bajo/{stockMinimo}")
    public List<Producto> getProductosStockBajo(@PathVariable Integer stockMinimo){
        return productoRepository.findProductosConStockBajo(stockMinimo);
    }
}