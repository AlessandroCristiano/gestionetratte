package it.prova.gestionetratte.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;
import it.prova.gestionetratte.web.api.exception.TrattaNonAnnullataException;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;
import it.prova.gestionetratte.web.api.exception.TratteAttiveNotFound;
@Service
public class TrattaServiceImpl implements TrattaService{
	
	@Autowired
	private TrattaRepository repository;

	@Override
	public List<Tratta> listAllElements(boolean eager) {
		if (eager)
			return (List<Tratta>) repository.findAllTrattaEager();

		return (List<Tratta>) repository.findAll();
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		return repository.findSingleTrattaEager(id);
	}

	@Override
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}
	
	@Override
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		Tratta tratta = repository.findById(idToRemove)
		.orElseThrow(() -> new TrattaNotFoundException("Tratta not found con id: " + idToRemove));
		
		if(tratta.getStato()== Stato.ATTIVA || tratta.getStato()== Stato.CONCLUSA)
			throw new TrattaNonAnnullataException("Se la tratta non è annullata non si puo eliminare");
			
		repository.deleteById(idToRemove);
	}
	
	@Override
	public List<Tratta> findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	public void concludiTratte() {
		List<Tratta> tratte = repository.findAllByStato(Stato.ATTIVA);

		if (tratte.size() < 1) {
			throw new TratteAttiveNotFound("Non ci sono tratte attive"); 
		}
		tratte.stream().filter(t->LocalTime.now().isAfter(t.getOraAtterraggio())).forEach(t->t.setStato(Stato.CONCLUSA));
		tratte.forEach(t->repository.save(t));
	}

}
