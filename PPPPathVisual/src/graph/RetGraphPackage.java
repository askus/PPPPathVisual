package graph;

import graph.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
import java.util.*;

public class RetGraphPackage{
	public DirectedGraph<Long,Integer> graph;
	public Map<Integer,Integer> link2type ;
	public Map<Integer,Long> link2time ;
	public RetGraphPackage( DirectedGraph<Long,Integer> graph , Map<Integer,Integer> link2type , Map<Integer,Long> link2time ){
		this.graph = graph ;
		this.link2type = link2type ;
		this.link2time = link2time;
	}

}
