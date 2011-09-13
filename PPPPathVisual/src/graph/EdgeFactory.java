package graph;
public class EdgeFactory{
	public static int TP = 0 ;
	public static int FP = 1 ;
	public static int FN = 2 ;
	public static int TN = 3 ;
	public static int RELATION = 4; 	
	
	Integer eid; 
	public EdgeFactory(){
		eid = -1;
	}  
	public Integer getEdge(){
		eid++;
		return eid;
	}
}
