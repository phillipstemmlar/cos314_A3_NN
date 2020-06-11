/**
 * Layer
 */
public class Layer {
	public Neuron[] neurons;

	private Layer(){}

	public static Layer createLayer(int prevNeuronCount, int neuronCount){
		Layer layer = new Layer();
		layer.neurons = new Neuron[neuronCount];
		for(int i = 0; i < neuronCount; ++i){
			float[] weights = MathUtil.RandomFloatArray(prevNeuronCount,Neuron.minWeight, Neuron.maxWeight, true);
			float bias = MathUtil.RandomSignedFloat(0, 1);
			layer.neurons[i] = Neuron.createNeuron(weights, bias);
		}
		return layer;
	}

	public static Layer createInputLayer(float[] inputs){
		Layer layer = new Layer();
		layer.neurons = new Neuron[inputs.length];
		for(int i = 0; i < inputs.length; ++i){
			layer.neurons[i] = Neuron.createInputNeuron(inputs[i]);
		}
		return layer;
	}

	public int bestNeuronIndex(){
		float max = Float.MIN_VALUE;
		int maxIndex = -1;
		for(int i = 0; i < neurons.length; ++i){
			if(neurons[i].value > max){
				max = neurons[i].value;
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	public String toString(){
		return "Layer( n:"+ neurons.length +" )";
	}

}