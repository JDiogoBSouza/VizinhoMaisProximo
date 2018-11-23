package br.imd.visao;

import br.imd.modelo.NearestNeighbor;
import br.imd.modelo.TsplibReader;
import br.imd.modelo.TwoOpt;

public class Principal
{		
	public static void main(String[] args)
	{		
		TsplibReader matrix = new TsplibReader("C:\\Users\\Diogo\\Desktop\\TspTestes\\pr439.tsp");	// emandg5.tsp # brazil58.tsp # eil51.tsp
		//imprimir(matrix.getAdjacencyMatrix());
		
		int[][] H = matrix.getAdjacencyMatrix();
		
		NearestNeighbor vizinhoMaisProximo = new NearestNeighbor();
		
		int peso = NearestNeighbor.INF;
		int[] twoOptBest = null;
		
		for(int i = 0; i < H[0].length; i++)
		{
			long tempoInicio = System.currentTimeMillis();
			
			int[] menorCiclo = vizinhoMaisProximo.onePath(H, i);
			
			System.out.println("Tempo Total Vizinho Mais Proximo: "+ (System.currentTimeMillis() - tempoInicio) + "ms");
		
			//vizinhoMaisProximo.print(menorCiclo, 1);
			
			TwoOpt twoOpt = new TwoOpt();

			tempoInicio = System.currentTimeMillis();
			int[] twoOptApplied = twoOpt.calculeBestWay(H, menorCiclo);
			System.out.println("Tempo Total 2-OPT: "+ (System.currentTimeMillis() - tempoInicio) + "ms");
	
			
			int weight = twoOpt.calculeSize(H, twoOptApplied);
			
			if(weight < peso)
			{
				peso = weight;
				twoOptBest = twoOptApplied.clone();
			}
			
		}

		System.out.println("Menor Ciclo Hamiltoniano PÓS-2OPT: ");
		vizinhoMaisProximo.print(twoOptBest, 1);
		System.out.println();
		System.out.println("Peso do Caminho: " + peso);
		
	}
}
