package GestioneServer;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Utente, Long> {
    }
