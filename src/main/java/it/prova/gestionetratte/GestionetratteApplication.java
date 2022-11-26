package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner {
	
//	AIRBUS -> REGISTA
//	TRATTA -> FILM
	
	@Autowired
	private AirbusService airbusService;

	@Autowired
	private TrattaService trattaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Airbus airbus= airbusService.findByCodiceAndDescrizione("GFTDV", "Roma - Istanbul");
		
		if(airbus==null) {
			airbus= new Airbus("GFTDV","Roma - Istanbul",
					LocalDate.parse("20-05-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 50);
			airbusService.inserisciNuovo(airbus);
		}
		
		Tratta tratta = new Tratta("12857","Roma - Istanbul", 
				LocalDate.parse("20-05-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 
				LocalTime.parse("09.00", DateTimeFormatter.ofPattern("HH.mm")),
				LocalTime.parse("14.00", DateTimeFormatter.ofPattern("HH.mm")),Stato.ATTIVA,airbus);
		
		if (trattaService.findByCodiceAndDescrizione(tratta.getCodice(), tratta.getDescrizione()).isEmpty())
			trattaService.inserisciNuovo(tratta);
	}

}
