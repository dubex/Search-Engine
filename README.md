Search-Engine
=============

It searches the required document according to PRP (BM25) model and rank all the document according qPRP model.
qPRP can be thought as new model for IR. Existing qPRP approach considers term present in different section of document equally. 
Our belief is that representing the document as multidimensional subspace will give better result.  

Software Required:--
•	DOM Parser (inbuilt in Java) for extracting the text from Data Set
•	Apache Lucene 2.4.0 for Indexing and some retrieval function. 
•	Apache Commons Math Library 2.2.0 for implementing the Document as a vector, cosine function, matrix, and other 
  mathematical operation.
•	And BM25 Implementation for extracting the result from the index according to BM25 model.

Project is divided into 3 Module:-
1.	Implementation of program to Index the Data Set
2.	Implementation of program to Search Using Cosine Similarity.
3.	Implementation of program to Search Using Quantum based Similarity measure. 


Indexing is started from the Main class. In Main class we need to set the path of directory, which is to be indexed. 
Main class instantiate the Indexer class and call its method rebuildIndexes(String), and passes the given directory path.
rebuildIndex(String) in turn calls recursive(File). All file contained in the directory will get indexed recursively by
this function. Each file is parsed by TryDOM class and it is passed to indexDocument(TryDOM) to get indexed.

To search the indexed document for the given query we will use MySearcher class. Program will start from Main class which
instantiate the SearchFrame in result one GUI is popped up. GUI takes 2 input, query and file name(where result is stored).
On clicking the search button, it instantiate the MySearcher and call its method sortQPRP(), which in result 
call getProbableRelvDoc() method to get the top k result using BM25 model, now sortQPRP() rearranges the result 
according to qPRP model. To get similarity between two documents sortQPRP() calls the getSimilarity() method, which in 
turn calls testSimilarityUsingCosine() for each respective field of document. testSimilarityUsingCosine() uses DocVector
class to represent the Document as vector. 
