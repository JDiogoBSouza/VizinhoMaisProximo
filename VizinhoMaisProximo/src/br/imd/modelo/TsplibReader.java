package br.imd.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TsplibReader
{
	final static int INF = Integer.MAX_VALUE;
	
	private String name;
	private String type;
	private String comment;
	private int dimension;
	private String edge_weight_type;
	private String edge_weight_format;
	private int[][] adjacencyMatrix;
	
	public TsplibReader(String path)
	{
		convertFromFile(path);
	}
	
	public void convertFromFile(String path)
	{		
		File file = new File(path); 
		  
		BufferedReader br;
		
		try
		{
			br = new BufferedReader(new FileReader(file));

			String st; 
			
			try
			{
				int count = 0;
				
				while( count <= 5 )
				{
					st = br.readLine();
					// System.out.println("Lido = " + st);
					
					String[] parts = st.split(": ");
					
					//String part1 = parts[0]; // Label
					String part2 = parts[1]; // Value
					
					switch(count)
					{
						case 0:
							this.name = part2;
						break;

						case 1:
							this.type = part2;
						break;

						case 2:
							this.comment = part2;
						break;

						case 3:
							this.dimension = Integer.parseInt(part2);

							adjacencyMatrix = new int[dimension][dimension];
						break;

						case 4:
							this.edge_weight_type = part2;
							
							if( edge_weight_type.contains("EUC_2D") )
							{
								count = 6;
							}
							
						break;

						case 5:
							this.edge_weight_format = part2;
						break;

						default:
						break;
					}
					
					count++;
				}
				
				// System.out.println("Leu todo o cabeçalho");
				
				if( edge_weight_type.contains("EUC_2D") )
				{
					convertFromEuclid2d(br);
				}
				else if( edge_weight_type.contains("EXPLICIT") )
				{
					convertFromMatrix(br);
				}
				else
				{
					System.out.println("Tipo de Arquivo Invalido");
				}
				
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			} 
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
	}
	
	private void convertFromMatrix(BufferedReader content) throws IOException
	{
		String st;
		int count = 0;
		
		st = content.readLine(); // Clear label that indicates start of data.
		
		while ( !( st = content.readLine() ).contains("EOF"))
		{
			//System.out.println("Leu = " + st);
			
			adjacencyMatrix[count] = separeRows(st);
			
			count++;
		}

		adjacencyMatrix[count] = separeRows(" ");
		completeMatrix();
	}
	
	private void convertFromEuclid2d(BufferedReader content) throws IOException
	{
		String st;
		int count = 0;
		String[][] lines = new String[dimension][2] ;
		
		st = content.readLine(); // Clear label that indicates start of data.
		
		while ( !( st = content.readLine() ).contains("EOF"))
		{
			//System.out.println("Leu = " + st);
			
			String[] splited = st.split(" ");
			lines[count][0] = splited[1];
			lines[count][1] = splited[2];
			
			count++;
		}
		
		calculateDistances(lines);
		completeMatrix();
	}
	
	private void calculateDistances(String[][] data)
	{
		for(int i = 0; i < dimension; i++)
		{
			for(int j = i+1; j < dimension; j++)
			{
				int xi = (int) Double.parseDouble(data[i][0]);
				int yi = (int) Double.parseDouble(data[i][1]);
				int xj = (int) Double.parseDouble(data[j][0]);
				int yj = (int) Double.parseDouble(data[j][1]);
				
				int xd = xi - xj;
				int yd = yi - yj;
				
				int dij = (int) Math.round( Math.sqrt( (xd * xd) + (yd * yd) ) );
				
				adjacencyMatrix[i][j] = dij;
			}
		}
	}
	
	private int[] separeRows(String row)
	{
		String[] parts = row.split(" ");
		
		int[] rowInt = new int[dimension];
		
		int count = 0;
		
		if( edge_weight_format.contains("UPPER_ROW") )
		{
			for(int i = 0; i < dimension; i++)
			{
				if(i >= (dimension - parts.length) )
				{
					rowInt[i] = Integer.parseInt(parts[count]);
					count++;
				}
				else
				{
					rowInt[i] = INF;
				}
				
			}
		}
		else if( edge_weight_format.contains("LOWER_DIAG_ROW") )
		{
			// need implements
		}
		
		//System.out.println("Linha dividida em: " + parts.length);
		
		return rowInt;
	}
	
	private void completeMatrix()
	{
		//if( edge_weight_format.contains("UPPER_ROW") )
		//{
			for(int i = 0; i < dimension; i++)
			{
				for(int j = 0; j < dimension; j++)
				{
					if( i < j)
					{
						adjacencyMatrix[j][i] = adjacencyMatrix[i][j];
					}
				}
			}
		/*}
		else if(  edge_weight_format.contains("LOWER_DIAG_ROW") )	// not tested
		{			
			for(int i = 0; i < dimension; i++)
			{
				for(int j = 0; j < dimension; j++)
				{
					if( i >= j)
					{
						adjacencyMatrix[i][j] = adjacencyMatrix[j][i];
					}
				}
			}
		}*/
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public int getDimension()
	{
		return dimension;
	}

	public void setDimension(int dimension)
	{
		this.dimension = dimension;
	}

	public String getEdge_weight_type()
	{
		return edge_weight_type;
	}

	public void setEdge_weight_type(String edge_weight_type)
	{
		this.edge_weight_type = edge_weight_type;
	}

	public String getEdge_weight_format()
	{
		return edge_weight_format;
	}

	public void setEdge_weight_format(String edge_weight_format)
	{
		this.edge_weight_format = edge_weight_format;
	}

	public int[][] getAdjacencyMatrix()
	{
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(int[][] adjacencyMatrix)
	{
		this.adjacencyMatrix = adjacencyMatrix;
	}
}
