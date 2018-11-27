package br.imd.visao;

import br.imd.modelo.NearestNeighbor;
import br.imd.modelo.TsplibReader;
import br.imd.modelo.TwoOpt;

public class Principal
{		
	public static void main(String[] args)
	{		
		TsplibReader matrix = new TsplibReader("C:\\Users\\Diogo\\Desktop\\TspTests\\novos\\pr76.tsp");	// emandg5.tsp # brazil58.tsp # eil51.tsp
		//imprimir(matrix.getAdjacencyMatrix());
		
		int[][] H = matrix.getAdjacencyMatrix();
		
		NearestNeighbor vizinhoMaisProximo = new NearestNeighbor();
		TwoOpt twoOpt = new TwoOpt();
		
		long vizinhoAcumulado = 0;
		long twoOptAcumulado = 0;
		
		int weightVizinho = 0;
		int weight2Opt = 0;
		
		for(int i = 0; i < 100; i++)
		{
			long tempoInicio = System.currentTimeMillis();

			int[] menorCiclo = vizinhoMaisProximo.smallerPath(H);
			
			long tempo = System.currentTimeMillis() - tempoInicio;
			
			//System.out.println("Tempo Total Vizinho mais Proximo: "+ tempo + "ms");
			
			vizinhoAcumulado += tempo;

			weightVizinho = twoOpt.calculeSize(H, menorCiclo);
			//vizinhoMaisProximo.print(menorCiclo, 1);
			/*System.out.println();
			System.out.println("Peso do Caminho: " + weight);
			System.out.println();
			System.out.println();*/
			
			/*tempoInicio = System.currentTimeMillis();
			int[] twoOptApplied = twoOpt.calculeBestWay(H, menorCiclo);
			System.out.println("Tempo Total 2-OPT: "+ (System.currentTimeMillis() - tempoInicio) + "ms");*/

			tempoInicio = System.currentTimeMillis();
			
			int[] twoOptApplied = twoOpt.calculeBestWay(H, menorCiclo);

			tempo = System.currentTimeMillis() - tempoInicio;
			//System.out.println("Tempo Total 2-OPT: "+ tempo + "ms");
			twoOptAcumulado += tempo;
			
			weight2Opt = twoOpt.calculeSize(H, twoOptApplied);

			//vizinhoMaisProximo.print(twoOptApplied, 1);
			//System.out.println();
			//System.out.println("Peso do Caminho: " + weight);
		}

		System.out.print("Tempo médio Vizinho mais Proximo: ");
		System.out.println(vizinhoAcumulado/100 + "ms");
		System.out.println("Peso: " + weightVizinho);
		
		System.out.print("Tempo médio 2-OPT: ");
		System.out.println(twoOptAcumulado/100 + "ms");
		System.out.println("Peso: " + weight2Opt);
	}
}
