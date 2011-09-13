package window ;

import org.apache.commons.collections15.*;
import java.util.*;
import graph.*; 
import java.awt.*;
import java.awt.BasicStroke;

public class LinkStroker implements Transformer<Integer,Stroke> {
	
	Map<Integer,Integer> link2type ;
	public LinkStroker( Map<Integer,Integer> link2type ){
		this.link2type = link2type; 
	}
	
	public Stroke transform( Integer s ){
		Integer linktype = this.link2type.get( s ) ;
		if( linktype != EdgeFactory.RELATION ){
			return new BasicStroke( (float)5.0) ;
		}
		float dash[] = {10.0f};
		return  new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	}	
}
