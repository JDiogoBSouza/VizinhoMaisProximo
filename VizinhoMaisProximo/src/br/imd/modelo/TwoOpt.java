package br.imd.modelo;

public class TwoOpt
{
	public int[] calculeBestWay(int[][] G, int[] caminho)
	{
		// Get tour size
	    int size = caminho.length;
	 
	    // repeat until no improvement is made 
	    int improve = 0;
	    
	    int run = 0;
	 
	    while ( improve < 20 )
	    {
	        int best_distance = calculeSize(G, caminho);
	 
	        for ( int i = 0; i < size - 1; i++ ) 
	        {
	            for ( int k = i + 1; k < size; k++) 
	            {
	            	int[] novoCaminho = twoOptSwap( caminho, i, k );
	 
	                int new_distance = calculeSize(G, novoCaminho);
	 
	                if ( new_distance < best_distance ) 
	                {
	                    // Improvement found so reset
	                    improve = 0;
	                    caminho = novoCaminho;
	                    best_distance = new_distance;
	                }
	                
	                run++;
	            }
	        }
	 
	        improve ++;
	    }
	    
	    System.out.println( run + " Execuções");
	    
	    return caminho;
	}
	
	public int[] twoOptSwap(int[] caminho, int i, int k)
	{
		int size = caminho.length;
		
		int[] novoCaminho = new int[size];
		
	    // 1. take route[0] to route[i-1] and add them in order to new_route
	    for ( int c = 0; c <= i - 1; ++c )
	    {
	    	novoCaminho[c] = caminho[c];
	    }
	     
	    // 2. take route[i] to route[k] and add them in reverse order to new_route
	    int dec = 0;
	    for ( int c = i; c <= k; ++c )
	    {
	    	novoCaminho[c] = caminho[k - dec];
	        dec++;
	    }
	 
	    // 3. take route[k+1] to end and add them in order to new_route
	    for ( int c = k + 1; c < size; ++c )
	    {
	    	novoCaminho[c] = caminho[c];
	    }
	    
	    return novoCaminho;
	}
	
	public int calculeSize(int[][] G, int[] caminho)
	{
		int inicio = caminho[0];
		int peso = 0;
		
		for(int i = 1; i < caminho.length; i++)
		{
			peso += G[inicio][caminho[i]];
			inicio = caminho[i];
		}
		
		peso += G[inicio][caminho[0]];
		
		System.out.println("Peso do Caminho: " + peso);
		
		return peso;
	}
}
