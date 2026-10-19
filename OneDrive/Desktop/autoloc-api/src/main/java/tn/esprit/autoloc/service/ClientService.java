package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.BusinessException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponse create(ClientRequest request) {
        assertUnique(request, null);
        Client client = new Client();
        apply(client, request);
        return ClientResponse.from(clientRepository.save(client));
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream().map(ClientResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return ClientResponse.from(getEntity(id));
    }

    public ClientResponse update(Long id, ClientRequest request) {
        Client client = getEntity(id);
        assertUnique(request, id);
        apply(client, request);
        return ClientResponse.from(clientRepository.save(client));
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client introuvable avec l'id " + id);
        }
        clientRepository.deleteById(id);
    }

    public Client getEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable avec l'id " + id));
    }

    private void assertUnique(ClientRequest request, Long currentId) {
        boolean emailExists = currentId == null
                ? clientRepository.existsByEmail(request.email())
                : clientRepository.existsByEmailAndIdClientNot(request.email(), currentId);
        if (emailExists) {
            throw new BusinessException("Un client existe déjà avec l'email " + request.email());
        }

        boolean permisExists = currentId == null
                ? clientRepository.existsByNumPermis(request.numPermis())
                : clientRepository.existsByNumPermisAndIdClientNot(request.numPermis(), currentId);
        if (permisExists) {
            throw new BusinessException("Un client existe déjà avec le permis " + request.numPermis());
        }
    }

    private void apply(Client client, ClientRequest request) {
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
        client.setDateInscription(request.dateInscription());
    }
}
