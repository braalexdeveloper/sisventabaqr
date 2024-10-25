package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.SaleProductRequest;
import com.brayanweb.sisventa.dtos.SaleRequest;
import com.brayanweb.sisventa.dtos.SaleResponse;
import com.brayanweb.sisventa.exceptions.ResourceNotFoundException;
import com.brayanweb.sisventa.models.Client;
import com.brayanweb.sisventa.models.Product;
import com.brayanweb.sisventa.models.Sale;
import com.brayanweb.sisventa.models.SaleProduct;
import com.brayanweb.sisventa.repositories.ClientRepository;
import com.brayanweb.sisventa.repositories.ProductRepository;
import com.brayanweb.sisventa.repositories.SaleProductRepository;
import com.brayanweb.sisventa.repositories.SaleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final SaleProductRepository saleProductRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository,ProductRepository productRepository,ClientRepository clientRepository,SaleProductRepository saleProductRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository=clientRepository;
        this.saleProductRepository=saleProductRepository;
        this.productRepository=productRepository;
    }

    public List<SaleResponse> getSales() {
        List<SaleResponse> sales = saleRepository.findAll().stream()
                .map(this::convertToSaleResponse).collect(Collectors.toList());
        return sales;
    }

    public SaleResponse getSale(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Venta no encontrada"));

        return convertToSaleResponse(sale);
    }

    @Transactional
    public SaleResponse create(SaleRequest saleRequest) {
try{
        Client clientFound = clientRepository.findById(saleRequest.getClient_id())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado, id: " + saleRequest.getClient_id()));

        Sale createdSale = saleRepository.save(convertToSale(new Sale(), saleRequest, clientFound));
        
                
        for(SaleProductRequest saleProduct:saleRequest.getSale_products()){
             SaleProduct itemSaleProduct=new SaleProduct();
             itemSaleProduct.setQuantity(saleProduct.getQuantity());
             itemSaleProduct.setSubtotal(saleProduct.getSubtotal());
             
             Product product=productRepository.findById(saleProduct.getProduct_id())
                     .orElseThrow(()->new ResourceNotFoundException("Producto no encontrado"));
                     
             itemSaleProduct.setProduct(product);
             itemSaleProduct.setSale(createdSale);
             saleProductRepository.save(itemSaleProduct);
        }
       
        
        return convertToSaleResponse(createdSale);
         } catch (Exception e) {
        // Manejo de la excepción: log, rethrow, etc.
        throw new RuntimeException("Error al crear la venta: " + e.getMessage());
    }
    }

   
    @Transactional
    public String delete(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venta no encontrada");
        }
        saleRepository.deleteById(id);
        return "Venta Eliminado Correctamente";
    }

    private Sale convertToSale(Sale sale, SaleRequest saleRequest, Client client) {
        sale.setSaleDate(saleRequest.getSaleDate());
        sale.setTotal(saleRequest.getTotal());
        sale.setClient(client);
        return sale;
    }

    private SaleResponse convertToSaleResponse(Sale sale) {
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setId(sale.getId());
        saleResponse.setTotal(sale.getTotal());
        saleResponse.setSaleDate(sale.getSaleDate());
        saleResponse.setNameClient(sale.getClient().getName() + " " + sale.getClient().getLastName());

        return saleResponse;
    }
}
