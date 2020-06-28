package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	private SightingsDAO dao;
	private List<String> stati;
	private Graph<String, DefaultEdge> grafo;
	
	private List<String> best;
	
	public Model() {
		this.dao = new SightingsDAO();
	}
	
	public List<AnnoAvvistamenti> getAnniAvvistamenti() {
		return this.dao.getAnni();
	}
	
	public void creaGrafo(Year anno) {
		this.stati = this.dao.getVertici(anno);
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.stati);
		
		for(String s1 : this.grafo.vertexSet()) {
			for(String s2 : this.grafo.vertexSet()) {
				if(!s1.equals(s2)) {
					if(this.dao.arco(s1, s2, anno)) {
						this.grafo.addEdge(s1, s2);
					}
				}
			}
		}
	}

	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<String> getStati() {
		return this.stati;
	}
	
	public List<String> getSuccessori(String stato) {
		return Graphs.successorListOf(this.grafo, stato);
	}
	
	public List<String> getPredecessori(String stato) {
		return Graphs.predecessorListOf(this.grafo, stato);
	}
	
	public List<String> getRaggiungibili(String stato) {
		List<String> raggiungibili = new ArrayList<>();
		
		DepthFirstIterator<String, DefaultEdge> dp = new DepthFirstIterator<>(this.grafo, stato);
		
		dp.next();
		while(dp.hasNext())
			raggiungibili.add(dp.next());
		
		return raggiungibili;
	}
	
	public List<String> getPercorso(String partenza) {
		this.best = new ArrayList<>();
		List<String> parziale = new ArrayList<>();
		
		parziale.add(partenza);
		ricorsiva(parziale);
		
		return this.best;
	}

	private void ricorsiva(List<String> parziale) {
		
		if(parziale.size() > this.best.size()) {
			this.best = new ArrayList<>(parziale);
			//return;
		}
		
		String ultimo = parziale.get(parziale.size()-1);
		
		for(String s : Graphs.successorListOf(this.grafo, ultimo)) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsiva(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}
}
