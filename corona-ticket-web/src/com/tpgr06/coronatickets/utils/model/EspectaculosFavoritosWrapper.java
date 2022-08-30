package com.tpgr06.coronatickets.utils.model;

import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;

public class EspectaculosFavoritosWrapper {

	private EspectaculoDTO espectaculo;
	private boolean esFavorito;

	public EspectaculosFavoritosWrapper(EspectaculoDTO e, boolean esFavorito) {
		super();
		this.espectaculo = e;
		this.esFavorito = esFavorito;
	}
	
	public EspectaculoDTO getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(EspectaculoDTO espectaculo) {
		this.espectaculo = espectaculo;
	}
	public boolean isEsFavorito() {
		return esFavorito;
	}
	public void setEsFavorito(boolean esFavorito) {
		this.esFavorito = esFavorito;
	}
	
	
}
