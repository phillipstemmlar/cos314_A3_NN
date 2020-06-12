COS314 Assignment 3
Phillip Schulze
u18171185

==================================JAR FILE==========================================
To compile the code into a JAR file, use

	make jar

The JAR file will be available in the bin/ directory.

To run the JAR file, called COS314_A3_u18171185.jar, use

	java -jar COS314_A3_u18171185.jar <params file> <training data file> <validation data file> <test data file>

===================================PARAMETER FILE FORMAT=============================

Given filename: params.txt
Content:

	-1.0
	1.0
	0.05
	0.96

The 1st value (-1.0)  specifies the minimum weight value for a neuron.
The 2nd value (1.0) 	specifies the maximum weight value for a neuron.
The 3rd value (0.05) 	specifies the learning rate.
The 4th value (0.96)  specifies the accuracy threshold - minimum acceptable accuracy.

*Each value has to be on a new line with no empty lines in between.

===================================DEVELOPMENT ENVIRONMENT===========================

openjdk 11.0.7 2020-04-14
OpenJDK Runtime Environment (build 11.0.7+10-post-Ubuntu-2ubuntu218.04)
OpenJDK 64-Bit Server VM (build 11.0.7+10-post-Ubuntu-2ubuntu218.04, mixed mode, sharing)