package graph;

import utility.*;
import java.util.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;

public class RelationsReader {
	String filename ;
	String tmpfolder;
	public void setTmpfolder( String tmpfolder ){ this.tmpfolder = tmpfolder ;}
	public RelationsReader( String filename ){ this.filename  = filename ; }
	public Collection<Edge> getRelations( Collection< Long > nodes) {
		if( filename != null){
			try{
				return IOUtility.readRelationsOneBy( filename , nodes );		
			}catch( Exception e ){
				System.out.println( "Err: Relations can not be retrieved." );
				return null;
			}
		}else{
			return new HashSet<Edge>();
		}
	}
	public Collection<Edge> getRelations( Long headNode ){
		if( tmpfolder == null ) { System.out.println( "No tmp Folder.") ; return null ; }
		String filename = tmpfolder + "/"+ headNode ;
		try{
			return IOUtility.readRelationPairs( filename );
		}catch( Exception e ){
			System.out.println( "IO ERROR: cannot open tmp file..");
			return null;
		}
	}   
}
