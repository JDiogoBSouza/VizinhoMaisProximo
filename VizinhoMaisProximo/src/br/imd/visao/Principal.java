package br.imd.visao;

import br.imd.modelo.NearestNeighbor;
import br.imd.modelo.TsplibReader;
import br.imd.modelo.TwoOpt;

public class Principal
{		
	public static void main(String[] args)
	{		
		TsplibReader matrix = new TsplibReader("C:\\Users\\Diogo\\Desktop\\ch130.tsp");	// emandg5.tsp # brazil58.tsp # eil51.tsp
		//imprimir(matrix.getAdjacencyMatrix());
		
		int[][] H = matrix.getAdjacencyMatrix();
		
		NearestNeighbor vizinhoMaisProximo = new NearestNeighbor();
		
		int[] menorCiclo = vizinhoMaisProximo.smallerPath(H);
		
		vizinhoMaisProximo.print(menorCiclo, 1);
		
		TwoOpt twoOpt = new TwoOpt();
		
		int[] twoOptApplied = twoOpt.calculeBestWay(H, menorCiclo);

		System.out.println();
		System.out.println("Menor Ciclo Hamiltoniano PÓS-2OPT: ");
		vizinhoMaisProximo.print(twoOptApplied, 1);
		
		int peso = twoOpt.calculeSize(H, twoOptApplied);
				
		System.out.println();
		System.out.println("Peso do Caminho: " + peso);
	}
}
