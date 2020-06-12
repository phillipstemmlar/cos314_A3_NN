import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		
		if(args.length < 4){
			Helper.error("Missing runtime arguments!");
			Helper.error("args: <params file> <training data file> <validation data file> <test data file>");
			return;
		}

		float[] params = parseParamFile(args[0]); 

		//parms: minWeight, maxWeight, learning_rate, training_cycles
		float minWeight = params[0];
		float maxWeight = params[1];
		float learning_rate = params[2];
		float accuracy_threshold = params[3];

		NN net = new NN(minWeight,maxWeight);
		net.train(args[1],args[2],accuracy_threshold,learning_rate);
		net.test(args[3]);

		Helper.writeToFile("output.txt", net.summary());

	}

	public static float[] parseParamFile(String paramsInputFile){
		String paramsFile = Helper.filetoString(paramsInputFile);
		// System.out.println(paramsFile);
		
		float[] result = new float[4];
		try{
			int prev = 0;
			int pos = 0;
			for(int i = 0; i < 4; ++i){
				pos = paramsFile.indexOf("\n",prev);
				if(pos < 0) pos = paramsFile.length();
				result[i] = Float.parseFloat(paramsFile.substring(prev, pos));
				prev = pos+1;
			}

		}catch(StringIndexOutOfBoundsException e){
			System.out.println("Error in parameter file: " + paramsInputFile);
		}

		System.out.println(Arrays.toString(result));

		return result;
	}
}