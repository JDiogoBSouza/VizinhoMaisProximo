package br.imd.modelo;

public class NearestNeighbor
{
	public final static int INF = Integer.MAX_VALUE;
	
	public void print(int[][] G)
	{
		for(int i = 0; i < G.length; i++)
		{
			for(int j = 0; j < G[0].length; j++)
			{
				System.out.print(G[i][j] + " ");
			}
			
			System.out.println();
		}
	}
	
	public void print(int[] G, int increment)
	{
		for(int i = 0; i < G.length; i++)
		{
			System.out.print(G[i] + increment + " ");
		}
	}
	
	private int getCloser(int[] line, int[] path)
	{
		int shorterDistance = INF;
		int nextVertex = -1;
		
		for(int i = 0; i < line.length; i++)
		{
			if( !onThePath( path, i ) && line[i] < shorterDistance )
			{
				shorterDistance = line[i];
				nextVertex = i;
			}
		}
		
		return nextVertex;
	}
	
	private boolean onThePath(int[] path, int vertex)
	{
		for(int i = 0; i < path.length; i++)
		{
			if( path[i] == vertex )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int calculateWeight(int[] path, int[][] G)
	{
		int start = path[0];
		int weight = 0;
		
		for(int i = 1; i < path.length; i++)
		{
			weight += G[start][path[i]];
			start = path[i];
		}
		
		weight += G[start][path[0]];
		
		//System.out.println("Weight: " + weight);
		
		return weight;
	}
	
	public int[] smallerPath(int[][] G)
	{
		int weightSmallerPath = INF;
		int[] smallerPath = new int[G[0].length];
		
		for(int i = 0; i < G[0].length; i++)
		{
			int initialVertex = i; // Set the start vertex of path.
			
			int[] path = new int[G[0].length];
			
			for(int j = 0; j < path.length; j++)
			{
				path[j] = NearestNeighbor.INF;
			}
			
			//print(G);
			
			path[0] = initialVertex;
			int verticeAtual = initialVertex;
			
			for(int j = 1; j < path.length; j++)
			{
				int proximoVertice = getCloser(G[verticeAtual], path);
				
				if( proximoVertice != -1 )
				{
					path[j] = proximoVertice;
					verticeAtual = proximoVertice;
				}
			}

			/*System.out.println("Ciclo Hamiltoniano: ");
			print(path, 1);
			System.out.println();*/
			
			int weightPath = calculateWeight(path, G);
			
			if( weightPath  < weightSmallerPath)
			{
				weightSmallerPath = weightPath;
				smallerPath = path.clone();
			}
		}
		
		return smallerPath;
	}
}
