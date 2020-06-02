package it.polito.tdp.extflightdelays.model;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo();
		List<Airport> aerei2=model.aerei;
		System.out.println(model.grafo.vertexSet().size());
		System.out.println(model.rotte.size());
		System.out.println(model.grafo.edgeSet().size());
		Map<Integer,Airport> aerei=model.idMapAerei;
		DefaultWeightedEdge s=model.grafo.getEdge(aerei.get(157), aerei.get(51));
		System.out.println(s);
		System.out.println(model.grafo.getEdgeWeight(s));
		//Set<DefaultWeightedEdge> s=model.grafo.edgeSet();
		//for(DefaultWeightedEdge e: s)
			//System.out.println(e);
		List<Airport> percorso=model.trovaPercorso(aerei2.get(0), aerei2.get(1));
		System.out.println(percorso);

	}

}
