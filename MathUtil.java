/**
 * MathUtil
 */
public class MathUtil {
	private MathUtil(){};

	public static float RandomFloat(float min, float max){	
		return min + (float) Math.random() * (max - min);
	}

	public static float RandomSign(float x){
		float a = (float) Math.random();
		if(a < 0.5) x *= -1;
		return x;
	} 

	public static float RandomSignedFloat(float min, float max){
		return RandomSign(RandomFloat(min, max));
	}

	public static float[] RandomFloatArray(int size, float min, float max, boolean randomSign){
		float[] values = new float[size];
		for(int i = 0; i < size; ++i){
			if(randomSign) values[i] = RandomSignedFloat(min, max);
			else values[i] = RandomFloat(min, max);
		}
		return values;
	}

	public static float Sigmoid(float x){
		return (float) (1/(1+Math.pow(Math.E, -x)));
	}

	public static float Sigmoid_derivation(float x){
		return Sigmoid(x) * (1- Sigmoid(x));
	}

	public static float squaredError(float output, float target){
		return (float) (0.5* Math.pow(2, target-output));
	}

	public static float sumSquaredError(float[] outputs, float[] targets){
		float sum = 0;
		for(int i = 0; i < outputs.length; ++i) sum += squaredError(outputs[i], targets[i]); 
		return sum;
	}

}