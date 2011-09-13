package utility;
import java.util.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.*;
import java.io.*;
import graph.*;
public class IOUtility{
	public static void writePair( String filename,Collection<Edge> pairs ) throws Exception{
		PrintStream printStream = new PrintStream( new FileOutputStream( new File( filename ) ));
		for( Edge pair : pairs ){
			Long from = pair.getFirst(); Long to = pair.getSecond();
			printStream.println( from +"\t" + to ); 
		}
		printStream.close();
	}	
	public static Collection<Long> readNodes( String filename ) throws Exception{
		BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString = null;
		Collection<Long> ret= new HashSet<Long>();
		while( (tmpString = br.readLine() ) != null ){
			Long v = Long.parseLong( tmpString );
			ret.add( v);
		}
		return ret ;
	}
	public static Long _getHead( Collection<Edge> graph ){
		Map<Long, Integer> inDegreeCal = new HashMap<Long, Integer>();
		for( Edge edge : graph ){
			Long from = edge.getFirst();
			Long to = edge.getSecond();
			if( !inDegreeCal.containsKey( from) ){
				inDegreeCal.put(from, 0 );
			}
			if( !inDegreeCal.containsKey( to ) ){
				inDegreeCal.put( to, 0 );
			}
			Integer tmpDegree = inDegreeCal.get( to  );
			tmpDegree += 1 ;
			inDegreeCal.put( to , tmpDegree );
		}
		System.out.println( "In degree : " + inDegreeCal );
		for( Long key : inDegreeCal.keySet() ){
			if( inDegreeCal.get( key ) == 0){
				return key ;
			}	
		}
		return null;
	}

	// get the predict path
	public static Map<Long,Collection<Edge>> readGraphs( String filename ) throws Exception{
		Map<Long,Collection<Edge>> ret = new HashMap<Long,Collection<Edge>>();
		BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString = null;
		Collection< Edge> oneGraphCollection = new HashSet< Edge >();
		Long headNode=null;
		while( ( tmpString = br.readLine()) != null ){
			//a blank -> new a graph
			if( tmpString.trim().length() == 0){
				if( oneGraphCollection.size() != 0 ){
					headNode = _getHead( oneGraphCollection );
					if( headNode == null ){
						System.out.println("error: not found head node");
					}	
					if( headNode > 0 ){
						ret.put( headNode , oneGraphCollection );
						headNode=null;
					}
					oneGraphCollection = new HashSet< Edge>();
				}
				continue;
			}
			// add link to oneGraph
			String[] tmp = tmpString.trim().split("\t");
			if( tmp.length <2 ) continue;
			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			Long time = null;
			if( tmp.length >= 3) time = Long.parseLong( tmp[2] ); 
			
			oneGraphCollection.add( new Edge( from , to, time ));
		}
		if( oneGraphCollection.size() != 0 ){
			headNode = _getHead( oneGraphCollection );
			ret.put( headNode , oneGraphCollection );
		}
		return ret ;
	}

	// get the links related v 
	public static Map<Long, Collection<Edge>> readRelations( String filename) throws Exception{
		Map<Long,Collection<Edge>> ret = new HashMap<Long, Collection<Edge>>();
		BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString = null;
		while( (tmpString= br.readLine() ) != null){
			String[] tmp = tmpString.split("\t") ;
			if( tmp.length < 2 ) continue;

			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			Edge pair = new Edge( from, to );

			Collection<Edge> tmpSet = ret.get( from ) ;
			if( tmpSet == null ){ tmpSet = new HashSet<Edge>(); ret.put( from , tmpSet); }
			tmpSet.add( pair);
			
			tmpSet = ret.get( to ) ;
			if( tmpSet == null ){ tmpSet = new HashSet<Edge>(); ret.put( to , tmpSet); }
			tmpSet.add( pair );
		}
		return ret;
	}
	public static Collection<Edge> readRelationPairs( String filename ) throws Exception{
		Collection<Edge> ret = new HashSet<Edge>();
		BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString = null;
		while( (tmpString= br.readLine() ) != null ){
			String[] tmp = tmpString.split("\t") ;
			if( tmp.length < 2 ) continue;

			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			Edge pair = new Edge( from, to );
			ret.add( pair ); 
		}
		return ret;
	}
	// get the links 
	public static Collection<Edge> readRelationsOneBy( String filename , Collection<Long> nodes)throws Exception{
		Collection<Edge> ret = new HashSet<Edge>();
		BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString = null;
		while( (tmpString= br.readLine() ) != null ){
			String[] tmp = tmpString.split("\t") ;
			if( tmp.length < 2 ) continue;

			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			if( nodes.contains( from) || nodes.contains(to) ){
				Edge pair = new Edge( from, to );
				ret.add( pair ); 
			}
			
		
		}
		return ret;
	
	}  
} 
