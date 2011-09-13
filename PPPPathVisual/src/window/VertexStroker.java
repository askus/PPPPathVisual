package window ;

import org.apache.commons.collections15.*;
import edu.uci.ics.jung.graph.*;
import java.util.*;
import graph.*; 
import java.awt.*;

public class VertexStroker implements Transformer<Long,Stroke> {
//	DirectedGraph<Long,Integer> g ;	
//	public VertexPainter( DirectedGraph<Long,Integer> g ){ this.g = g ;}	
	Long headNode ;
	public VertexStroker( Long headNode ) { this.headNode = headNode; }
	public Stroke transform( Long s ){
		if(  s == headNode ){
			return new BasicStroke( (float)5.0); 
		}else{
			return null;
		}
	}	
}
