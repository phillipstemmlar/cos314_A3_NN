import java.util.Arrays;
import java.util.Objects;

/**
 * NN
 */
public class NN {
	Layer[] layers;
	TrainingData[] dataSet;

	public static final int HIDDEN_LAYERS = 1; 
	public static final int INPUT_NEURONS = 4; 
	public static final int OUTPUT_NEURONS = 3; 


	public NN(float min, float max){
		Neuron.setWeightRange(min, max);
		dataSet = null;
		layers = new Layer[HIDDEN_LAYERS + 2];
		layers[0] = null;
		layers[1] = Layer.createLayer(INPUT_NEURONS, 6);
		layers[2] = Layer.createLayer(6, OUTPUT_NEURONS);
	}

	public void train(String filename, int training_cycles, float learning_rate){
		dataSet = TrainingData.createDataSetFromFile(filename);

		for(int t = 0; t < training_cycles; ++t){
			for(int d = 0; d < dataSet.length; ++d){
				layers[0] = Layer.createInputLayer(dataSet[d].data);

				for(int i = 1; i < layers.length; ++i){
					for(int j = 0; j < layers[i].neurons.length; ++j){
						float sum = 0;
						for(int k = 0; k < layers[i-1].neurons.length; ++k){
							sum += layers[i-1].neurons[k].value * layers[i].neurons[j].weights[k];
						}
						sum += layers[i].neurons[j].bias;
						layers[i].neurons[j].value = MathUtil.Sigmoid(sum);
					}
				}
				backPropagation(learning_rate, dataSet[d]);
			}
		}
	}

	public void test(String filename){
		dataSet = TrainingData.createDataSetFromFile(filename);
		int correctCount = 0;

		for(int d = 0; d < dataSet.length; ++d){
			TrainingData dataS = dataSet[d];

			layers[0] = Layer.createInputLayer(dataS.data);
			for(int i = 1; i < layers.length; ++i){
				for(int j = 0; j < layers[i].neurons.length; ++j){
					float sum = 0;
					for(int k = 0; k < layers[i-1].neurons.length; ++k){
						sum += layers[i-1].neurons[k].value * layers[i].neurons[j].weights[k];
					}
					sum += layers[i].neurons[j].bias;
					layers[i].neurons[j].value = MathUtil.Sigmoid(sum);
				}
			}

			if(layers[layers.length -1].bestNeuronIndex() == dataS.expectedIndex()) correctCount++;
		}

		float success_rate = (float)(correctCount) / (float)(dataSet.length);

		System.out.println("Data Tested:\t" + dataSet.length);
		System.out.println("Tests Correct:\t" + correctCount);

		System.out.println("Success Rate:\t" + success_rate);

	}

	//============================================================

	public void backPropagation(float learning_rate, TrainingData data){

		//Update Output Layer
		Layer outputLayer = layers[layers.length -1];
		for(int i = 0; i < outputLayer.neurons.length; ++i){
			Neuron curNeuron = outputLayer.neurons[i];
			float derivative = curNeuron.value-data.expected[i];
			curNeuron.gradient = derivative * curNeuron.value * (1 - curNeuron.value);
			for(int j = 0; j < curNeuron.weights.length; ++j){
				float prevOutput = layers[layers.length -2].neurons[j].value;
				curNeuron.cache_weights[j] = curNeuron.weights[j] - learning_rate * curNeuron.gradient * prevOutput; 
			}
		}

		//Update Hidden Layer
		for(int i = layers.length-2; i > 0; --i){
			Layer curLayer = layers[i];
			for(int j = 0; j < curLayer.neurons.length; ++j){
				Neuron curNeuron = curLayer.neurons[j];
				float gradSum = sumGradient(j, i+1);
				curNeuron.gradient = gradSum * curNeuron.value * (1 - curNeuron.value);
				for(int k = 0; k < curNeuron.weights.length; ++k){
					float prevOutput = layers[i-1].neurons[k].value;
					curNeuron.cache_weights[k] = curNeuron.weights[k] - learning_rate * curNeuron.gradient * prevOutput; 
				}
			}
		}

		//Update Neuron weights
		for(int i = 0; i < layers.length; ++i){
			for(int j = 0; j < layers[i].neurons.length; ++j){
				layers[i].neurons[j].updateWeights();
			}
		}
			
	}

	public float sumGradient(int n_index, int l_index){
		float gradSum = 0;
		Layer curLayer = layers[l_index];
		for(int i = 0; i < curLayer.neurons.length; ++i){
			Neuron curNeuron = curLayer.neurons[i];
			gradSum += curNeuron.weights[n_index]*curNeuron.gradient;
		}
		return gradSum;
	}

	//============================================================

	public void printDataSet(){
		for(int i = 0; dataSet != null && i < dataSet.length; ++i){
			System.out.println(dataSet[i].toString());
		}
	}

}