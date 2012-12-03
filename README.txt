README-

Execution- This program can be executed on any machine that has java installed with the following 		     command line command. “java -jar VAL.jar”

Summary- This program will keep track of your travelling from location to location. It will keep track 	of what your favourite destinations were for various different criteria such as food and culture.
	It also has the ability to look up information on landmarks that are contained within ones city. 	This is done through wikipedia.

Class Summary-
	VAL.java- The main class that runs the program. This keeps track of the current location in a 	conversation
	WordDictionary.java-	Class used for lookup on words that are synonyms of other words. Also 	contains various String manipulation methods.
	WikiUse.java- Contains the methods used to access and validate information from wikipedia.
	Wiki.java- Class containing methods used for accessing the java-wiki api.

API Summary-
	java-wiki api- Used by WikiUse.java to provide a link to the data on wikipedia.
	Mongo-2.10.0 – Used by VAL.java to store and retrieve data about conversations.

Bugs-
	If wiki article doesn't contain location field then program assumes landmark is from a different 	area.
	Results from wiki sometimes aren't correctly parsed by parsing method.
	Wiki search doesn't always produce most relevant result.
