package graph;

import graph.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
import java.util.*;


public class TimeEdgeFilter{
	private Long startTime , endTime; 
	public void setTime( Long startTime , Long endTime ){
		this.startTime = startTime ; this.endTime = endTime ;
	}
	public Collection<Edge> setEdges( Collection<Edge> edges ){
		// set condition
		Collection<Edge> ret = new HashSet<Edge>();
		for( Edge e : edges ){
			Long time = e.getTime();
			if( startTime <= time && time < endTime ){
				ret.add( e );
			}
		}
		return ret ;
	}
}
