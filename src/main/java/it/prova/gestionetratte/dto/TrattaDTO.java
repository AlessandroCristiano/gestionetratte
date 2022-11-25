package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrattaDTO {
	
	private Long id;
	
	@NotBlank(message = "{codice.notblank}")
	@Size(min = 4, max = 4, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String codice;
	
	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;
	
	@NotNull(message = "{data.notnull}")
	private java.time.LocalDate data;
	
	@NotNull(message = "{oraDecollo.notnull}")
	private java.time.LocalTime oraDecollo;
	
	@NotNull(message = "{oraAtterraggio.notnull}")
	private java.time.LocalTime oraAtterraggio;
	
	@NotNull(message = "{stato.notblank}")
	private Stato stato;
	
	@JsonIgnoreProperties(value = { "tratte" })
	@NotNull(message = "{airbus.notnull}")
	private AirbusDTO airbus;

	public TrattaDTO() {
		super();
	}

	public TrattaDTO(Long id,
			@NotBlank(message = "{codice.notblank}") @Size(min = 4, max = 4, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String codice,
			@NotBlank(message = "{descrizione.notblank}") String descrizione, LocalDate data, LocalTime oraDecollo,
			LocalTime oraAtterraggio, @NotNull(message = "{stato.notblank}") Stato stato, AirbusDTO airbus) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
		this.oraDecollo = oraDecollo;
		this.oraAtterraggio = oraAtterraggio;
		this.stato = stato;
		this.airbus = airbus;
	}

	public TrattaDTO(Long id,
			@NotBlank(message = "{codice.notblank}") @Size(min = 4, max = 4, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String codice,
			@NotBlank(message = "{descrizione.notblank}") String descrizione, LocalDate data, LocalTime oraDecollo,
			LocalTime oraAtterraggio, @NotNull(message = "{stato.notblank}") Stato stato) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
		this.oraDecollo = oraDecollo;
		this.oraAtterraggio = oraAtterraggio;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public java.time.LocalDate getData() {
		return data;
	}

	public void setData(java.time.LocalDate data) {
		this.data = data;
	}

	public java.time.LocalTime getOraDecollo() {
		return oraDecollo;
	}

	public void setOraDecollo(java.time.LocalTime oraDecollo) {
		this.oraDecollo = oraDecollo;
	}

	public java.time.LocalTime getOraAtterraggio() {
		return oraAtterraggio;
	}

	public void setOraAtterraggio(java.time.LocalTime oraAtterraggio) {
		this.oraAtterraggio = oraAtterraggio;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public AirbusDTO getAirbus() {
		return airbus;
	}

	public void setAirbus(AirbusDTO airbus) {
		this.airbus = airbus;
	}
	
	public Tratta buildTrattaModel() {
		Tratta result = new Tratta(this.id, this.codice, this.descrizione, this.data, this.oraDecollo,
				this.oraAtterraggio, this.stato);
		if (this.airbus != null) {
			result.setAirbus(this.airbus.buildAirbusModel());
		}
		return result;
	}
	
	public static TrattaDTO buildTrattaDTOFromModel(Tratta trattaModel, boolean includeAirbus) {
		TrattaDTO result = new TrattaDTO(trattaModel.getId(), trattaModel.getCodice(), trattaModel.getDescrizione(),
				trattaModel.getData(), trattaModel.getOraDecollo(), trattaModel.getOraAtterraggio(),
				trattaModel.getStato());

		if (includeAirbus)
			result.setAirbus(AirbusDTO.buildAirbusDTOFromModel(trattaModel.getAirbus(), false));

		return result;
	}
	
	public static List<TrattaDTO> createTrattaDTOListFromModelList(List<Tratta> modelListInput, boolean includeAirbus) {
		return modelListInput.stream().map(trattaEntity -> {
			return TrattaDTO.buildTrattaDTOFromModel(trattaEntity, includeAirbus);
		}).collect(Collectors.toList());
	}
	
	public static Set<TrattaDTO> createTrattaDTOSetFromModelSet(Set<Tratta> modelListInput, boolean includeAirbus) {
		return modelListInput.stream().map(trattaEntity -> {
			return TrattaDTO.buildTrattaDTOFromModel(trattaEntity, includeAirbus);
		}).collect(Collectors.toSet());
	}
	
}
