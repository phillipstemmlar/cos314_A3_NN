import java.util.Arrays;
import java.util.Objects;

/**
 * NN
 */
public class NN {
	Layer[] layers;

	public static final int HIDDEN_LAYERS = 1; 
	public static final int INPUT_NEURONS = 4; 
	public static final int OUTPUT_NEURONS = 3; 

	private float trainAccuracy = 0;
	private float testAccuracy = 0;
	private int epochs = 0;


	public NN(float min, float max){
		Neuron.setWeightRange(min, max);
		layers = new Layer[HIDDEN_LAYERS + 2];
		layers[0] = null;
		layers[1] = Layer.createLayer(INPUT_NEURONS, 6);
		layers[2] = Layer.createLayer(6, OUTPUT_NEURONS);
	}

	public void train(String trainFile, String validFile, float accuracy_threshold, float learning_rate){
		TrainingData[] trainDataSet = TrainingData.createDataSetFromFile(trainFile);
		TrainingData[] validDataSet = TrainingData.createDataSetFromFile(validFile);

		epochs = 0;

		while(trainAccuracy < accuracy_threshold){
			epochs++;
			for(int d = 0; d < trainDataSet.length; ++d){
				layers[0] = Layer.createInputLayer(trainDataSet[d].data);

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
				backPropagation(learning_rate, trainDataSet[d]);
			}
			trainAccuracy = testDataSet(validDataSet);
			// System.out.println("Accuracy of Training set:\t" + trainAccuracy);
		}
		System.out.println("Accuracy of Training set:\t" + trainAccuracy);
		System.out.println("Epochs to converge:\t" + epochs);
	}

	public void test(String filename){
		TrainingData[] dataSet = TrainingData.createDataSetFromFile(filename);	
		testAccuracy = testDataSet(dataSet);
		System.out.println("Accuracy of Testing set:\t" + testAccuracy);
	}

	private float testDataSet(TrainingData[] dataSet){
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
		return (float)(correctCount) / (float)(dataSet.length);
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

	public String summary(){
		String sum = "";

		sum +=  WeightMatrixString()+ "\n";
		sum +=  BiasVectorString() + "\n";

		sum += "Epochs to converge:\t" + epochs +"\n";		
		sum += "Training Accuracy: \t" + trainAccuracy +"\n";		
		sum += "Testing Accuracy:  \t" + testAccuracy +"\n";		

		return sum;
	}

	private String WeightMatrixString(){
		String sum = "";
		for(int i = 1; i < layers.length; ++i){
			sum += "\n----Weight Matrix from layer " + (i-1) + " to layer " + i + "----\n";
			for(int j = 0; j < layers[i].neurons.length; ++j){
				for(int k = 0; k < layers[i].neurons[j].weights.length; ++k) sum += layers[i].neurons[j].weights[k] + "\t";
				sum += "\n";
			}
		}
		return sum;
	}

	private String BiasVectorString(){
		String sum = "";
		for(int i = 1; i < layers.length; ++i){
			sum += "\n----Bias Vector from layer " + i + "----\n";
			for(int j = 0; j < layers[i].neurons.length; ++j) sum += layers[i].neurons[j].bias + "\t";	
			sum += "\n";
		}
		return sum;
	}

	//============================================================

	public void printDataSet(TrainingData[] dataSet){
		for(int i = 0; dataSet != null && i < dataSet.length; ++i){
			System.out.println(dataSet[i].toString());
		}
	}

}