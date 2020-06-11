public class Main {
	public static void main(String[] args) {
		
		if(args.length < 3){
			Helper.error("Missing runtime arguments!");
			Helper.error("args: <params file> <training data file> <test data file>");
			return;
		}

		String params = Helper.filetoString(args[0]);

		//parms: minWeight, maxWeight, learning_rate, training_cycles
		float minWeight = -1;
		float maxWeight = 1;
		float learning_rate = 0.05f;
		int training_cycles= 1000;

		NN net = new NN(minWeight,maxWeight);
		net.train(args[1],training_cycles,learning_rate);
		net.test(args[2]);

	}
}