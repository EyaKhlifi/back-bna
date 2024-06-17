package com.example.ebank.service;

import com.example.ebank.entity.Client;
import com.example.ebank.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // other methods
}
