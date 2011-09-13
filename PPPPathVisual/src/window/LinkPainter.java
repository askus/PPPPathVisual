package window ;

import org.apache.commons.collections15.*;
import java.util.*;
import graph.*; 
import java.awt.*;
public class LinkPainter implements Transformer<Integer,Paint> {
	
	Map<Integer,Integer> link2type ;
	public LinkPainter( Map<Integer,Integer> link2type ){
		this.link2type = link2type; 
	}
	
	public Paint transform( Integer s ){
		Integer linktype = this.link2type.get( s ) ;
		if( linktype == EdgeFactory.TP ){
			return Color.BLUE;
		}else if( linktype == EdgeFactory.FP ){
			return Color.RED;
		}else if( linktype == EdgeFactory.FN ){
			return Color.GREEN;
		}else if( linktype == EdgeFactory.RELATION ){
	//		return Color.GRAY;
			return null;
		}else{
			return Color.BLACK;
		}
	}	
}
