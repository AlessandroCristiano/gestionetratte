package it.prova.gestionetratte.service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.web.api.exception.AirbusContainTratteException;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;

@Service
public class AirbusServiceImpl implements AirbusService{
	
	@Autowired
	private AirbusRepository repository;

	@Override
	public List<Airbus> listAllElements() {
		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	@Override
	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Airbus caricaSingoloElementoConTratte(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		Airbus airbus = repository.findByIdEager(idToRemove);
		
		if(airbus==null) {
			throw new AirbusNotFoundException("Airbus not found con id: " + idToRemove);
		}
		if(airbus.getTratte().size() > 0) {
			throw new AirbusContainTratteException("Airbus non si puo eliminare perche ha delle tratte");
		}
		repository.deleteById(idToRemove);	
	}

	@Override
	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	public List<AirbusDTO> listAirbusConSovrapposizioni() {

		List<AirbusDTO> listaAirbus = AirbusDTO.createAirbusDTOListFromModelList(repository.findAllEager(), false);

		for (AirbusDTO elementoAirbus : listaAirbus) {
			for (TrattaDTO elementoTratta : elementoAirbus.getTratte()) {
				for (TrattaDTO singolaTratta : elementoAirbus.getTratte()) {
					if ((singolaTratta.getData().isEqual(elementoTratta.getData())
							&& singolaTratta.getOraDecollo().isAfter(elementoTratta.getOraDecollo())
							&& singolaTratta.getOraDecollo().isBefore(elementoTratta.getOraAtterraggio()))
							|| (singolaTratta.getData().isEqual(elementoTratta.getData())
									&& singolaTratta.getOraAtterraggio().isAfter(elementoTratta.getOraDecollo())
									&& singolaTratta.getOraAtterraggio()
											.isBefore(elementoTratta.getOraAtterraggio()))) {
						elementoAirbus.setConSovrapposizioni(true);
					}
				}
			}
		}
		return listaAirbus;
	}

}
