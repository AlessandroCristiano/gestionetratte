package it.prova.gestionetratte.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "airbus")
public class Airbus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "codice")
	private String codice;

	@Column(name = "descrizione")
	private String descrizione;
	
	@Column(name = "datainizioservizio")
	private Date dataInizioServizio;
	
	@Column(name = "numeropasseggeri")
	private Integer numeroPasseggeri;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "airbus")
	private Set<Tratta> tratte = new HashSet<Tratta>(0);

	public Airbus() {
		super();
	}

	public Airbus(Long id, String codice, String descrizione, Date dataInizioServizio, Integer numeroPasseggeri,
			Set<Tratta> tratte) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
		this.tratte = tratte;
	}

	public Airbus(Long id, String codice, String descrizione, Date dataInizioServizio, Integer numeroPasseggeri) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public Airbus(String codice, String descrizione, Date dataInizioServizio, Integer numeroPasseggeri,
			Set<Tratta> tratte) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
		this.tratte = tratte;
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

	public Date getDataInizioServizio() {
		return dataInizioServizio;
	}

	public void setDataInizioServizio(Date dataInizioServizio) {
		this.dataInizioServizio = dataInizioServizio;
	}

	public Integer getNumeroPasseggeri() {
		return numeroPasseggeri;
	}

	public void setNumeroPasseggeri(Integer numeroPasseggeri) {
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public Set<Tratta> getTratte() {
		return tratte;
	}

	public void setTratte(Set<Tratta> tratte) {
		this.tratte = tratte;
	}
}
