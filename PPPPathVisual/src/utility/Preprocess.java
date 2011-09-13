package utility;
import java.util.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
import java.io.*;
import graph.*;

public class Preprocess{
	public static void seperateRelation( String answerfile , String predictfile , String relationfile, String tmpfolder )throws Exception{
		Map<Long,Collection<Edge>> answer_paths = IOUtility.readGraphs( answerfile );   
		Map<Long,Collection<Edge>> predict_paths = IOUtility.readGraphs( predictfile );  
		Map<Integer,Long> link2time = new HashMap( );

		RelationsReader r_reader = null;
		r_reader = new RelationsReader( relationfile );
		GraphGenerator graph_generater = new GraphGenerator( answer_paths, predict_paths , r_reader ); 
		for( Long head_node : answer_paths.keySet() ){
			DirectedGraph<Long,Integer> g =new DirectedSparseGraph<Long,Integer>();
			Map<Integer,Integer> link2type = new HashMap<Integer,Integer>();
		
			System.out.println( "head_node: " + head_node );
			//if not in cache
			EdgeFactory edgefactory = new EdgeFactory();	
			graph_generater.addAnswer( g, link2type,link2time , answer_paths.get( head_node ),  edgefactory );
			if( predict_paths.get( head_node ) != null){
				graph_generater.concatePredictPath( g, link2type, link2time, predict_paths.get(head_node ) , edgefactory );
			}
			Collection<Edge> relations = r_reader.getRelations( g.getVertices() );
			IOUtility.writePair( tmpfolder+"/"+head_node , relations );  
		} 

	} 
	public static void main(String[] argv )throws Exception{
 		String answerfile = argv[0] ; 
		String predictfile  = argv[1] ; 
		String relationfile = argv[2] ; 
		String tmpfolder = argv[3]; 
	
		seperateRelation( answerfile , predictfile , relationfile, tmpfolder );
	}
}
