package br.imd.visao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.imd.modelo.TsplibMatrix;

public class Principal
{	
	final static int INF = Integer.MAX_VALUE;
	
	public static void popular(int[][] G)
	{
		for(int i = 0; i < G.length; i++)
		{
			for(int j = 0; j < G[0].length; j++)
			{
				if( (i + j) % 2 != 0 )
					G[i][j] = 1;
				else
					G[i][j] = 0;
			}
		}
	}
	
	public static void imprimir(int[][] G)
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
	
	public static void imprimir(int[] G, int incremento)
	{
		for(int i = 0; i < G.length; i++)
		{
			System.out.print(G[i] + incremento + " ");
		}
	}
	
	public static int pegaMaisProximo(int[] linha, int[] caminho)
	{
		int menorDistancia = INF;
		int proximoVertice = -1;
		
		for(int i = 0; i < linha.length; i++)
		{
			if( !estaNoCaminho( caminho, i ) && linha[i] < menorDistancia )
			{
				menorDistancia = linha[i];
				proximoVertice = i;
			}
		}
		
		return proximoVertice;
	}
	
	private static boolean estaNoCaminho(int[] caminho, int vertice)
	{
		for(int i = 0; i < caminho.length; i++)
		{
			if( caminho[i] == vertice )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static int calculaPeso(int[] caminho, int[][] G)
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
	
	public static void main(String[] args)
	{
		int[][] G = { {INF,2,4,2,1}, {2,INF,3,1,5}, {4,3,INF,2,1}, {2,1,2,INF,7}, {1,5,1,7,INF} };// new int[5][5];
		
		//popular(G);
		
		TsplibMatrix matrix = new TsplibMatrix("C:\\Users\\Diogo\\Desktop\\emandg5.tsp");	// emandg5.tsp # brazil58.tsp
		//imprimir(matrix.getAdjacencyMatrix());
		
		int[][] H = matrix.getAdjacencyMatrix();
		
		int[] menorCiclo = null;
		int pesoMenorCiclo = INF;
		
		for(int i = 0; i < H[0].length; i++)
		{
			int verticeInicial = i; // Seta o vertice inicial do ciclo.
			
			int[] caminho = new int[H[0].length];
			int[] pesos = new int[H[0].length];
			
			for(int j = 0; j < caminho.length; j++)
			{
				caminho[j] = INF;
			}
			
			//imprimir(G);
			
			caminho[0] = verticeInicial;
			int verticeAtual = verticeInicial;
			
			for(int j = 1; j < caminho.length; j++)
			{
				int proximoVertice = pegaMaisProximo(H[verticeAtual], caminho);
				
				if( proximoVertice != -1 )
				{
					caminho[j] = proximoVertice;
					verticeAtual = proximoVertice;
				}
			}

			System.out.println("Ciclo Hamiltoniano: ");
			imprimir(caminho, 1);
			System.out.println();
			
			int pesoCiclo = calculaPeso(caminho, H);
			
			if( pesoCiclo  < pesoMenorCiclo)
			{
				pesoMenorCiclo = pesoCiclo;
				menorCiclo = caminho.clone();
			}
		}
		
		System.out.println();
		System.out.println("Menor Ciclo Hamiltoniano: ");
		imprimir(menorCiclo, 1);
		System.out.println();
		System.out.println("Peso do Caminho: " + pesoMenorCiclo);
		
		
		// Only to check UPPER-ROW, LOWER-ROW and UPPER DIAG ROW...
		
		/*int[][] Matrix = { {1,2,3}, {1,2,3}, {1,2,3} };
		
		for(int i = 0; i < Matrix.length; i++)
		{
			for(int j = 0; j < Matrix.length; j++)
			{
				if( i >= j)
				{
					System.out.print( Matrix[i][j] + " ");
				}
				else
				{
					System.out.print("- ");
				}
			}
			
			System.out.println();
		}*/
	}
}
