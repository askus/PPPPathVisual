package window;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*; 
import java.util.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.visualization.decorators.*;
import edu.uci.ics.jung.graph.*;
import graph.*;
import window.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;;


public class TreeWindow extends JFrame {
	public static int HEGIHT=720;
	Map<Long,Collection<Edge>> answer_paths;  
	Map<Long,Collection<Edge>> predict_paths;  
//	Map<Long,Collection<Edge>> uid2relations;  
	GraphGenerator graphgenerator;
	RelationsReader r_reader;

	

	String tmpfolder =null;

	final JFrame self_delegate;
	final JList list;
	
	BasicVisualizationServer<Long,Integer> vv;
	
	JScrollBar scroll_bar; 
	Long startTime;
	Long endTime ;


	private static Long getTime( int ratio ,Long startTime , Long endTime ){
		Long ret = startTime;
		ret += (long)ratio  * ( endTime - startTime ) / (long )100;
		return ret;
	} 

	public TreeWindow(Map<Long,Collection<Edge>> answer_paths, 
		Map<Long,Collection<Edge>> predict_paths , 
		RelationsReader r_reader , String tmpfolder 
			){
		super("Predict Paths" );
		setLayout( new BorderLayout() );

		this.answer_paths = answer_paths;
		this.predict_paths = predict_paths;
		this.r_reader = r_reader;
		this.graphgenerator = new GraphGenerator( answer_paths, predict_paths, this.r_reader );

		if( tmpfolder != null ){
			this.graphgenerator.setTmpfolder( tmpfolder) ; 
		}
		self_delegate = this;

		// set left panel: graph
		DirectedGraph<Long,Integer> tmp_graph = new DirectedSparseGraph<Long,Integer>();
		tmp_graph.addVertex( (long) 1 );
		Layout<Long,Integer> layout = new SpringLayout2( tmp_graph );
		vv = new BasicVisualizationServer<Long,Integer>(layout);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.setPreferredSize(new Dimension(1100,TreeWindow.HEGIHT )); //Sets the viewing area size

		// set right panel: list
		DefaultListModel model = new DefaultListModel();
		for( Long head_node : answer_paths.keySet() ){
			model.addElement( head_node );
		}
		list = new JList( model );
		list.addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ){
				Long head_node = (Long) list.getSelectedValue() ;		
				initScrollBar( head_node);
				Long filtTime =(long) TreeWindow.getTime( scroll_bar.getValue() , startTime, endTime );
				drawGraph( head_node , filtTime );
			}
		});

		JScrollPane right_pane = new JScrollPane( list ); 
		right_pane.setPreferredSize( new Dimension( 100, TreeWindow.HEGIHT ) );	
		//add into display layout
		add( right_pane , BorderLayout.EAST);

		scroll_bar = new JScrollBar( Scrollbar.HORIZONTAL , 0 , 1 , 0 , 110 );
		scroll_bar.addAdjustmentListener( new AdjustmentListener(){
			public void adjustmentValueChanged( AdjustmentEvent e ){
				Long filtTime = TreeWindow.getTime( e.getValue(), startTime, endTime ) ;
				Long head_node = (Long) list.getSelectedValue();
				drawGraph( head_node , filtTime ); 
			//	System.out.println( (double) e.getValue() / (double)scroll_bar.getMaximum()  );	
			}
		}); 
		
		JPanel left_panel = new JPanel(new BorderLayout());
		left_panel.add(  vv, BorderLayout.NORTH );
		left_panel.add(  scroll_bar, BorderLayout.SOUTH );
		
		add( left_panel , BorderLayout.WEST );
	}

	public void initScrollBar( Long head_node ){
		RetGraphPackage ret = this.graphgenerator.generate(  head_node  );
		DirectedGraph<Long, Integer> g = ret.graph ;
		Map<Integer,Long> link2time = ret.link2time ;

		startTime = Collections.min( link2time.values() );
		endTime = Collections.max( link2time.values() );
			
		scroll_bar.setValues( 50, 1 , 0, 100 );

	}

	public void drawGraph( Long head_node, Long filtTime  ){
		RetGraphPackage ret = this.graphgenerator.generate(  head_node  );
		DirectedGraph<Long, Integer> g = ret.graph ;
		Map<Integer,Integer> link2type = ret.link2type ;

		DirectedGraph<Long,Integer> showG  = TimeFilter.getGraph( g , ret.link2time, ret.link2type, filtTime );	

		vv.getRenderContext().setEdgeFillPaintTransformer( new LinkPainter( link2type) );
		vv.getRenderContext().setEdgeStrokeTransformer( new LinkStroker( link2type ));
		vv.getRenderContext().setVertexFillPaintTransformer( new VertexPainter( head_node )); 
		vv.getRenderContext().setVertexStrokeTransformer( new VertexStroker( head_node ));
		vv.setGraphLayout( new SpringLayout2( showG ) );
	}
	

} 
