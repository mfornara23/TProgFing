package tpgr06.coronatickets.sys.valoracion;

public class ValoracionDTO {
	
	private int puntaje;
	private String espectaculo;
		
	public ValoracionDTO(int puntaje, String espectaculo) {
		this.puntaje = puntaje;
		this.espectaculo = espectaculo;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	public String getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}

}
