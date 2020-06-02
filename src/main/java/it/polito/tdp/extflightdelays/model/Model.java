package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	Graph<Airport, DefaultWeightedEdge> grafo;
	ExtFlightDelaysDAO dao;
	List<Airport> aerei;
	Map<Integer, Airport> idMapAerei;
	List<Rotta> rotte;
	Map<Airport,Airport> visita;
	int min;
	
	public Model() {
		dao=new ExtFlightDelaysDAO();
		idMapAerei=new TreeMap<>();
	}
	
	public List<Airport> trovaPercorso(Airport a1, Airport a2){
		BreadthFirstIterator<Airport, DefaultWeightedEdge> it=new BreadthFirstIterator(this.grafo,a1);
		visita=new HashMap();
		List<Airport> percorso=new ArrayList();
		visita.put(a1, null);
		it.addTraversalListener(new TraversalListener<Airport,DefaultWeightedEdge>(){
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
				Airport sorgente=grafo.getEdgeSource(e.getEdge());
				Airport destinazione=grafo.getEdgeTarget(e.getEdge());
				if(!visita.containsKey(destinazione)&&visita.containsKey(sorgente)) {
					visita.put(destinazione, sorgente);
				}
				if(!visita.containsKey(sorgente)&&visita.containsKey(destinazione)){
					visita.put(sorgente, destinazione);
				}

			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub

			}
		});
		
		while(it.hasNext()) {
			it.next();
		}
		
		if(!visita.containsKey(a1)||!visita.containsKey(a2)) {
			return null;
		}
		
		Airport step=a2;
		while(!step.equals(a1)) {
			percorso.add(step);
			step=visita.get(step);
		}
		
		percorso.add(a1);
		return percorso;
	}
	
	public void ottieniAereoporti(int min){
		this.min=min;
	}
	
	public Collection<Airport> getAereoporti(){
		return this.grafo.vertexSet();
	}
	
	
	public void creaGrafo() {
		grafo=new SimpleWeightedGraph(DefaultWeightedEdge.class);
		List<Airport> aereiTemp=dao.loadAllAirports();
		aerei=new ArrayList();
		for(Airport a: aereiTemp) {
			if(dao.getAereoportiDesiderati(a.getId(), this.min))
				aerei.add(a);
		}
		for(Airport a:aerei) {
			grafo.addVertex(a);
			idMapAerei.put(a.getId(),a);
		}
		 rotte=dao.getRotte(idMapAerei);
		 for(Rotta r:rotte) {
				if(grafo.getEdge(r.a1, r.a2)==null) {
				grafo.addEdge(r.a1, r.a2);
				grafo.setEdgeWeight(r.a1, r.a2, r.distanza);
				}
			}
		 
		
		
		
	}
}
