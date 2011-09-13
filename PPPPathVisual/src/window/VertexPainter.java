package window ;

import org.apache.commons.collections15.*;
import edu.uci.ics.jung.graph.*;
import java.util.*;
import graph.*; 
import java.awt.*;

public class VertexPainter implements Transformer<Long,Paint> {
//	DirectedGraph<Long,Integer> g ;	
//	public VertexPainter( DirectedGraph<Long,Integer> g ){ this.g = g ;}	
	Long headNode ;
	public VertexPainter( Long headNode ) { this.headNode = headNode; }
	public Paint transform( Long s ){
		if(  s == headNode ){
			return Color.RED;
		}else{
			return Color.WHITE;
		}
	}	
}
