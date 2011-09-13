
package main;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.*;
import org.apache.commons.collections15.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;


import window.*;
import graph.*;

import utility.*;
public class RunMain{
	public static void main( String[] argv) throws Exception{
		String answerFileName  = argv[0];
		String predictFileName  = argv[1];
		String relationName = argv[2];
		Integer isRelation = Integer.parseInt( argv[3] );
		String tmpfolder =null;
		if( argv.length > 4 ){
			tmpfolder = argv[4] ;
		}

		Map<Long,Collection<Edge>> answer_paths = IOUtility.readGraphs( answerFileName );   
		Map<Long,Collection<Edge>> predict_paths = IOUtility.readGraphs( predictFileName);  
//		Map<Long,Collection<Edge>> uid2relations = IOUtility.readRelations( relationName ); 
	
		RelationsReader r_reader = null;
		if( isRelation == 0)
			r_reader = new RelationsReader( null );
		else 
			r_reader = new RelationsReader( relationName );
	
		if( tmpfolder!= null ){
			r_reader.setTmpfolder( tmpfolder );
		}

		TreeWindow window = new TreeWindow( answer_paths, predict_paths, r_reader, tmpfolder  );
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
}
