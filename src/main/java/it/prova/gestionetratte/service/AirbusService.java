package it.prova.gestionetratte.service;

import java.util.List;
import java.util.Map;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.model.Airbus;

public interface AirbusService {

	List<Airbus> listAllElements();
	
	List<Airbus> listAllElementsEager();

	Airbus caricaSingoloElemento(Long id);
	
	Airbus caricaSingoloElementoConTratte(Long id);

	Airbus aggiorna(Airbus airbusInstance);

	Airbus inserisciNuovo(Airbus airbusInstance);

	void rimuovi(Long idToRemove);
	
	Airbus findByCodiceAndDescrizione(String codice, String descrizione);
	
	List<AirbusDTO> listAirbusConSovrapposizioni();
		
}
