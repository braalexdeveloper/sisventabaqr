package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.ProductRequest;
import com.brayanweb.sisventa.dtos.ProductResponse;
import com.brayanweb.sisventa.exceptions.ResourceNotFoundException;
import com.brayanweb.sisventa.models.Category;
import com.brayanweb.sisventa.models.Product;
import com.brayanweb.sisventa.repositories.CategoryRepository;

import com.brayanweb.sisventa.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final String UPLOAD_DIR = "uploads/clients/";

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> getProducts() {
        List<ProductResponse> products = productRepository.findAll().stream()
                .map(this::convertToProductResponse).collect(Collectors.toList());
        return products;
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado"));

        return convertToProductResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest productRequest) {
        Category categoryFound = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada, id: " + productRequest.getCategory_id()));

        Product createdProduct = productRepository.save(convertToProduct(new Product(), productRequest, categoryFound));
        return convertToProductResponse(createdProduct);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {
              
        Product product = productRepository.findById(id).map(productFound -> {
            
            Category categoryFound = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada, id: " +
                        productRequest.getCategory_id()));
            
            return productRepository.save(convertToProduct(productFound, productRequest,categoryFound));
        }).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        return convertToProductResponse(product);
    }

    @Transactional
    public String delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        productRepository.deleteById(id);
        return "Producto Eliminado Correctamente";
    }

    private Product convertToProduct(Product product, ProductRequest productRequest, Category category) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        if(productRequest.getImageFile()!=null){
            if(product.getImage()!=null){
                deleteImage(product.getImage());
            }
           product.setImage(saveImage(productRequest.getImageFile())); 
        }
        
        product.setCategory(category);
        return product;
    }

    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        productResponse.setImage(product.getImage());
        productResponse.setCategoryName(product.getCategory().getName());
        return productResponse;
    }
    
    private String saveImage(MultipartFile imageFile){
        try{
            String imageName=System.currentTimeMillis()+" "+imageFile.getOriginalFilename();
            Path imagePath=Paths.get(UPLOAD_DIR);
            
            Files.createDirectories(imagePath.getParent());
            
            Files.write(imagePath,imageFile.getBytes());
            
            return "products/"+imageName;
            
        }catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
    
    private void deleteImage(String imagePath){
          try{
            String relativePath=imagePath.startsWith("products/") ? imagePath.substring(8) : imagePath;
            Path path=Paths.get(UPLOAD_DIR+relativePath);
            
            if(Files.exists(path)){
                Files.delete(path);
                System.out.println("Imagen eliminada: " + path.toString());
            } else {
                System.out.println("La imagen no existe : " + path.toString());
            }
            
        }catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);
        }
    }
}
