package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami ;
	private double bestMedia = 0.0;
	private Set<Esame> bestSoluzione = null;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami= dao.getTuttiEsami();
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new HashSet<>();
		
		cerca (parziale, 0, numeroCrediti);
		
		return bestSoluzione;
	}
	
	private void cerca(Set<Esame> parziale, int L, int m) {
		
		//casi terminali
		
		int crediti = sommaCrediti(parziale);
		if(crediti > m) {
			return;
		}
		
		if(crediti == m) {
			//soluzione è interessante
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestMedia=media;
				bestSoluzione = new HashSet<>(parziale);// CLONE
			}
		}
		
		// sicuramente crediti <m 
		if(L== esami.size())
			return;
		
		//generazione sottoproblemi
		//esami(L) è da aggiungere o no ? provo entrambe le cose
		
		//provo ad aggiungerlo
		parziale.add(esami.get(L));
		cerca(parziale, L+1, m);
		parziale.remove(esami.get(L)); //backtracking
		
		//provo a non aggiungerlo
		cerca(parziale, L+1, m);
		
	}  
	public double calcolaMedia(Set<Esame> parziale) {
		int crediti =0;
		int somma =0;
		
		for(Esame e : parziale) {
			crediti+=e.getCrediti();
			somma+= (e.getVoto())*(e.getCrediti());
		}
		return somma/crediti;
	}
	private int sommaCrediti(Set<Esame> parziale) {
		int somma = 0;
				for(Esame e: parziale) {
					somma+= e.getCrediti();
				}
		return somma;
	}

}
