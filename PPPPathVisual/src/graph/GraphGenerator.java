package graph;

import graph.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
import java.util.*;


public class GraphGenerator{

	Map<Long,Collection<Edge>> answer_paths;  
	Map<Long,Collection<Edge>> predict_paths;  
	Map<Long,Collection<Edge>> uid2relations; 
	Map<Long, DirectedGraph<Long,Integer>> graph_cache;
	Map<Long, Map<Integer,Integer> > link2type_cache;
	Map<Long, Map<Integer,Long> > link2time_cache;
	RelationsReader r_reader ;
	
	String tmpfolder=null;
	public void setTmpfolder( String tmpfolder ){
		this.tmpfolder = tmpfolder;
	}

	public GraphGenerator( 
		Map<Long,Collection<Edge>> answer_paths, 
		Map<Long,Collection<Edge>> predict_paths, 
		RelationsReader r_reader ){
		//Map<Long,Collection<Edge>> uid2relations){
	
		this.answer_paths = answer_paths ;
		this.predict_paths = predict_paths;
		this.r_reader = r_reader ;
		//	this.uid2relations = uid2relations;
		
		graph_cache = new HashMap<Long, DirectedGraph<Long,Integer> >();
		link2type_cache = new HashMap<Long, Map<Integer,Integer> >();
		link2time_cache = new HashMap<Long, Map<Integer,Long> >();
	}  
	public RetGraphPackage generate( Long head_node){
		
		DirectedGraph<Long,Integer> g = new DirectedSparseGraph<Long,Integer>();
		Map<Integer,Integer> link2type = new HashMap<Integer,Integer>();
		Map<Integer,Long> link2time = new HashMap<Integer,Long>();
		
		DirectedGraph<Long,Integer> tmp_g = graph_cache.get(head_node );
		
		if( tmp_g != null ){
			g = graph_cache.get(head_node) ;
			System.out.println("Hit Cache.");
			System.out.println( "|G.vertex|= "+ g.getVertexCount() + "\t|G.edge|= " + g.getEdgeCount() );
			System.out.println("");
			link2type = link2type_cache.get( head_node );
			link2time = link2time_cache.get( head_node );
			return new RetGraphPackage( g , link2type, link2time ); 
		}
		//if not in cache
		EdgeFactory edgefactory = new EdgeFactory();

		addAnswer( g, link2type, link2time ,this.answer_paths.get( head_node ),  edgefactory );
		if( this.predict_paths.get( head_node ) != null){
			concatePredictPath( g, link2type, link2time ,this.predict_paths.get(head_node ) , edgefactory );
		}
		if( tmpfolder == null){
			concateRelation( g, link2type, edgefactory) ;
		}else{
			concateRelation( g , link2type, edgefactory , head_node ); 
		}
		
		if( link2time == null ){
			System.out.println( "Error: link2time is null" );
		}

		graph_cache.put( head_node , g );
		link2type_cache.put( head_node, link2type );
		link2time_cache.put( head_node, link2time );

		return new RetGraphPackage( g , link2type, link2time  );
	}
	
	// only concate onto the vertex which is outdegree > 0.
	public void concatePredictPath ( DirectedGraph<Long,Integer> g,
			Map<Integer,Integer> link2type ,
			Map<Integer,Long> link2time ,
			Collection<Edge> predict_path ,
			EdgeFactory edgefactory ){
		for( Edge link : predict_path ){
			
			Long from = link.getFirst();
			Long to = link.getSecond();
			
			if( g.containsVertex( from ) && ( g.outDegree( from ) > 0 )) {
				Integer eid = g.findEdge( from ,to );
				if( eid != null ){
					link2type.put( eid , EdgeFactory.TP );	
				}else{
					eid = edgefactory.getEdge();
					g.addEdge( eid, from , to );
					link2type.put( eid , EdgeFactory.FP );
				}
				link2time.put( eid , link.getTime() ); 
			} 
		} 

	}
	public void addAnswer( DirectedGraph<Long,Integer> g , 
			Map<Integer,Integer> link2type, 
			Map<Integer,Long> link2time,
			Collection<Edge> answer_path, 
			EdgeFactory edgefactory ){
		
		for( Edge link : answer_path){
			Long from = link.getFirst() ;
			Long to = link.getSecond();
			Integer eid = edgefactory.getEdge();
			g.addEdge( eid , from ,to );
			link2type.put( eid , EdgeFactory.FN ); 
			link2time.put( eid, link.getTime()); 
		} 
	}

	public void concateRelation( DirectedGraph<Long,Integer> g ,
			Map<Integer,Integer> link2type ,
			EdgeFactory edgefactory ,
			Long headNode ){
		Collection<Edge> tobeAdded = new HashSet<Edge>();

		Collection<Edge> relations = null;
		
		relations = r_reader.getRelations( headNode );
		
		if( relations== null ){
			relations = r_reader.getRelations( g.getVertices() ); 
		}

		for( Edge relation : relations ){
			Long from = relation.getFirst();
			Long to = relation.getSecond();
				if( g.findEdge( from , to ) == null && g.findEdge( to , from ) == null){
					tobeAdded.add( relation );
				}
		}	

		for( Edge relation : tobeAdded ){
			Long from = relation.getFirst();
			Long to = relation.getSecond();
			int eid = edgefactory.getEdge();
			g.addEdge( eid, from ,to );
			link2type.put( eid , EdgeFactory.RELATION );
		}

	} 
	public void concateRelation( DirectedGraph<Long,Integer> g ,
			Map<Integer,Integer> link2type,
			EdgeFactory edgefactory ){
		Collection<Edge> tobeAdded = new HashSet<Edge>();

		Collection<Edge> relations = r_reader.getRelations( g.getVertices() );
		for( Edge relation : relations ){
			Long from = relation.getFirst();
			Long to = relation.getSecond();
				if( g.findEdge( from , to ) == null && g.findEdge( to , from ) == null){
					tobeAdded.add( relation );
				}
	

		}	

		for( Edge relation : tobeAdded ){
			Long from = relation.getFirst();
			Long to = relation.getSecond();
			int eid = edgefactory.getEdge();
			g.addEdge( eid, from ,to );
			link2type.put( eid , EdgeFactory.RELATION );
		}
	}
}
