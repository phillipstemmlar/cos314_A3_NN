/**
 * Neuron
 */
public class Neuron {

	static float minWeight;
	static float maxWeight;

	float[] weights;
	float[] cache_weights;
	float gradient;
	float bias;
	float value;
	
	private Neuron(float[] w, float b, float val, float grad){
		bias = b;
		value = val;
		gradient = grad;
		weights = w;
		cache_weights = weights;
	}

	public static Neuron createNeuron(float[] weights, float bias){
		return new Neuron(weights, bias, 0, 0);
	}

	public static Neuron createInputNeuron(float value){
		return new Neuron(null, -1, value, -1);
	}

	public static void setWeightRange(float min, float max){
		maxWeight = max;
		minWeight = min;
	}

	public void updateWeights(){
		weights = cache_weights;
	}

}