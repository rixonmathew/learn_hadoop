This module generates mock files to be used as input for MapReduce jobs. the input are a json file explaining the schema of the file and the data types of the fields.
Addtional parameters that can be passed from command line are
1) number of file splits to create - default 1
2) number of records per split - default 10000
3) mode of generation:(random,prototype) : default random, if prototype is used then a json file should be specified that
    provides the properties for prototypes
4) output directory -- default output_<currenttime> where the file splits are created

To run this program following steps needs to be done
1) Create a json file that specifies the schema (e.g. positions.json)
2) Create a property file that specifies all the input values for the Generator( e.g. test.properties)
3) build the jar via command 
			mvn clean compile test assembly:single
      cd target/
4) Run the application via command
     java -jar <generated jar file> --file <fully qualified name of propertiesfile>


The type of random values that can be generated
1) Random numbers of certain length
2) Random string of certain length
3) Random alphanumeric string
4) Random values from a given range e.g. a field Designation should be of value only from CEO,Manager,Developer,Tester
5) Random values for a number between two values only (100001..1000101).
   This will be useful for generating transaction data for a given id.
6)