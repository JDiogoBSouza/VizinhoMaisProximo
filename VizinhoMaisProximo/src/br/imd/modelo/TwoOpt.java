package br.imd.modelo;



/**
 * The Class TwoOpt.
 */
public class TwoOpt
{
	
	/**
	 * This method calculates and return a smaller path in a graph by using the 2OPT strategy. 
	 * 
	 * @param graph the graph
	 * @param path the path
	 * @return the new path 
	 */
	public int[] calculeBestWay(int[][] graph, int[] path)
	{
		// Get tour size
	    int size = path.length;
	 
	    // repeat until no improvement is made 
	    int improve = 0;
	    
	    int run = 0;
	 
	    while ( improve < 20 )
	    {
	        int best_distance = calculeSize(graph, path);
	 
	        for ( int i = 0; i < size - 1; i++ ) 
	        {
	            for ( int k = i + 1; k < size; k++) 
	            {
	            	int[] newPath = twoOptSwap( path, i, k );
	 
	                int new_distance = calculeSize(graph, newPath);
	 
	                if ( new_distance < best_distance ) 
	                {
	                    // Improvement found so reset
	                    improve = 0;
	                    path = newPath;
	                    best_distance = new_distance;
	                }
	                
	                run++;
	            }
	        }
	 
	        improve ++;
	    }
	    
	    //System.out.println( run + " Execuções");
	    
	    return path;
	}
	
	/**
	 * The method execute the exchange of positions of vertices in a path.
	 *
	 * @param path the path
	 * @param i the i
	 * @param k the k
	 * @return the new Path
	 */
	public int[] twoOptSwap(int[] path, int i, int k)
	{
		int size = path.length;
		
		int[] newPath = new int[size];
		
	    // 1. take route[0] to route[i-1] and add them in order to new_route
	    for ( int c = 0; c <= i - 1; ++c )
	    {
	    	newPath[c] = path[c];
	    }
	     
	    // 2. take route[i] to route[k] and add them in reverse order to new_route
	    int dec = 0;
	    for ( int c = i; c <= k; ++c )
	    {
	    	newPath[c] = path[k - dec];
	        dec++;
	    }
	 
	    // 3. take route[k+1] to end and add them in order to new_route
	    for ( int c = k + 1; c < size; ++c )
	    {
	    	newPath[c] = path[c];
	    }
	    
	    return newPath;
	}
	
	/**
	 * This method Calcules and return the size of a path that was pass by parameter.
	 *
	 * @param graph the graph
	 * @param path the path
	 * @return the weight of graph
	 */
	public int calculeSize(int[][] graph, int[] path)
	{
		int initiation = path[0];
		int weight = 0;
		
		for(int i = 1; i < path.length; i++)
		{
			weight += graph[initiation][path[i]];
			initiation = path[i];
		}
		
		weight += graph[initiation][path[0]];
		
		//System.out.println("Peso do Caminho: " + weight);
		
		return weight;
	}
}
