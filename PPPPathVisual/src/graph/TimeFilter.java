package graph;

import graph.*;
import java.util.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
public class TimeFilter{
	public static DirectedGraph<Long,Integer> cloneGraph( DirectedGraph<Long,Integer>g ){
		DirectedGraph<Long,Integer> ret = new DirectedSparseGraph<Long,Integer>();
		for( Long v : g.getVertices() ){
			ret.addVertex( v );
		}

		for( Integer eid : g.getEdges() ){
			Long from = g.getSource( eid );
			Long to  = g.getDest( eid );
			ret.addEdge( eid , from , to );
		}
		return ret ;
	} 
	public static  DirectedGraph<Long,Integer> getGraph( DirectedGraph<Long, Integer> g , Map<Integer,Long> link2time , Map<Integer,Integer> link2type , Long filtTime ){
		Collection<Integer> EidTobeRemoved = new HashSet<Integer>();
		// clones
		DirectedGraph<Long,Integer> retG = cloneGraph( g );
		
		
		for( Integer eid : retG.getEdges() ){
			if(link2time.containsKey( eid ) &&   link2time.get( eid ) > filtTime ){
				System.out.println("time: " + link2time.get( eid ) + " filtTime: " + filtTime );
				EidTobeRemoved.add( eid );
			}
		}
		for( Integer removedEid : EidTobeRemoved ){
			retG.removeEdge( removedEid );
		}

		Collection<Long> VidToBeRemoved = new HashSet<Long>();
		//tobeRemoved.clear();
		// remove the node which is zero-degree or all is relation  
		for( Long vid : retG.getVertices() ){
			if( retG.degree( vid ) == 0 ){
				VidToBeRemoved.add( vid );
			}else{
				/*
				boolean shouldBeRemoved = true;
				for( Integer eid : retG.getInEdges( vid ) ){
					if( link2type.get( eid )  != EdgeFactory.RELATION ){
						shouldBeRemoved = false;
					}   
				}  
				for( Integer eid : retG.getOutEdges( vid ) ){
					if( link2type.get( eid )  != EdgeFactory.RELATION ){
						shouldBeRemoved = false;
					}   
				}  
				if( shouldBeRemoved ){
					VidToBeRemoved.add( vid );
				}*/
			}
		}
		for( Long vid :VidToBeRemoved ){
			retG.removeVertex( vid ) ;
		}	
		return retG ;
	} 
	

}
