import java.util.Arrays;

/**
 * TrainingData
 */
public class TrainingData {

	float[] data;
	float[] expected;

	public TrainingData(float[] d, float[] e){
		data = d;
		expected = e;
	}

	public int expectedIndex(){
		float max = Float.MIN_VALUE;
		int maxIndex = -1;
		for(int i = 0; i < expected.length; ++i){
			if(expected[i] > max){
				max = expected[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	public String toString(){
		return "{" + Arrays.toString(data)+", "+Arrays.toString(expected)+"}";
	}

	//5.1,3.5,1.4,0.2,Iris-setosa
	public static TrainingData[] createDataSetFromFile(String filename){
		String[] dataLines = Helper.fileToStringLines(filename);
		TrainingData[] dataSet = new TrainingData[dataLines.length];
		
		for(int i = 0; i < dataLines.length; ++i){
			float[] data = new float[Helper.charCount(dataLines[i], ',')];
			int n = 0;

			int prevIndex = -1;
			Boolean done = false;
			while(!done){
				int index = dataLines[i].indexOf(',', prevIndex+1);
				if(index < 0){
					done = true;
				}else{
					data[n] = Float.parseFloat(dataLines[i].substring(prevIndex+1, index));
					n++;
					prevIndex = index;
				}
			}
			dataSet[i] = new TrainingData(data, expectedOutput(dataLines[i].substring(prevIndex+1)));
		}
		return dataSet;
	}

	public static float[] expectedOutput(String name){
		if(Helper.compstr(name, SETOSA)) return new float[] {1,0,0};
		if(Helper.compstr(name, VERSICOLOR)) return new float[] {0,1,0};
		if(Helper.compstr(name, VIRGINICA)) return new float[] {0,0,1};
		return null;
	}

	public static String SETOSA = "Iris-setosa";
	public static String VERSICOLOR = "Iris-versicolor";
	public static String VIRGINICA = "Iris-virginica";
}