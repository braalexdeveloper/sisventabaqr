package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.ClientRequest;
import com.brayanweb.sisventa.dtos.ClientResponse;
import com.brayanweb.sisventa.exceptions.ClientNotFoundException;
import com.brayanweb.sisventa.models.Client;
import com.brayanweb.sisventa.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Page<ClientResponse> getClients(int page,int size,String sortBy) {
        
         // Define el objeto Pageable con la página, tamaño de página y ordenación
        Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sortBy));
        
        // Obtén una página de clientes desde el repositorio
        Page<Client> clientsPage=clientRepository.findAll(pageable);
        
        // Convierte la página de entidades a una página de respuestas
        return clientsPage.map(this::convertToClientResponse);
    }

    public ClientResponse getClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente no enconrado"));

        return convertToClientResponse(client);
    }
    
    public ClientResponse getClientByDniOrRuc(String value){
        Optional<Client> client=clientRepository.findByDniOrRuc(value,value);
        
        if(client.isPresent()){
            return convertToClientResponse(client.get());
        }else{
            throw new ClientNotFoundException("Client no encontrado");
        }
        
    }

    public ClientResponse create(ClientRequest clientRequest) {

        Client clientSaved = clientRepository.save(convertToClient(new Client(), clientRequest));
        return convertToClientResponse(clientSaved);
    }

    @Transactional
    public ClientResponse update(Long id, ClientRequest clientRequest) {
        Client clientUpdated = clientRepository.findById(id).map(foundClient -> {

            return clientRepository.save(convertToClient(foundClient, clientRequest));
        }).orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con id: " + id));

        return convertToClientResponse(clientUpdated);
    }

    @Transactional
    public String delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Cliente no encontrado con id: " + id);
        }
        clientRepository.deleteById(id);
        return "Cliente eliminado correctamente";
    }

    private ClientResponse convertToClientResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setLastName(client.getLastName());
        clientResponse.setDni(client.getDni());
        clientResponse.setRuc(client.getRuc());
        clientResponse.setPhone(client.getPhone());
        clientResponse.setAddress(client.getAddress());
        return clientResponse;
    }

    private Client convertToClient(Client client, ClientRequest clientRequest) {

        client.setName(clientRequest.getName());
        client.setLastName(clientRequest.getLastName());
        client.setDni(clientRequest.getDni());
        client.setRuc(clientRequest.getRuc());
        client.setPhone(clientRequest.getPhone());
        client.setAddress(clientRequest.getAddress());
        return client;
    }
}
