/*Compatible With Lucne 2.4.0*/
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;
import org.ninit.models.bm25.BM25BooleanQuery;
import org.ninit.models.bm25.BM25Parameters;
import java.io.PrintWriter;

public class MySearcher
{
	HashMap<Integer,Double> map;
	HashMap<String,Integer> hashTitle,hashFirstPara,hashSecondPara;
	ArrayList<Integer> idArrList;
	ArrayList<Double> valArrList;
	IndexSearcher searcher;
	Directory dir;
	Document doc;
	IndexReader indexReader;
	double tempScore=0.0,title=0.2,fp=0.5,sp=0.3;
	int tempDoc=0,num=0;
	MySearcher()
	{
		try{
			Directory dir=FSDirectory.getDirectory(new File("D:\\Study\\summer_plan\\ProjectIndex"));
			indexReader = IndexReader.open(dir,true);
			doc=new Document();
			searcher = new IndexSearcher("D:\\Study\\summer_plan\\ProjectIndex");
			hashTitle=new HashMap<String,Integer>();
			hashFirstPara=new HashMap<String,Integer>();
			hashSecondPara=new HashMap<String,Integer>();
			TermEnum termEnum = indexReader.terms();
			int posTitle = 0;
			int posSecondPara = 0;
			int posFirstPara = 0;
			while (termEnum.next()) 
			{
				Term term = termEnum.term();
				if("title".equals(term.field()))
				{
					hashTitle.put(term.text(), posTitle++);
				}
				else if("fp".equals(term.field()))
				{
					hashFirstPara.put(term.text(), posFirstPara++);
				}
				else if("sp".equals(term.field()))
				{
					hashSecondPara.put(term.text(), posSecondPara++);
				}
				else
				{ }
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public ScoreDoc[] getProbableRelvDoc(String query,String fileName) throws Exception
	{
		//Load average length
		BM25Parameters.load("C:\\Users\\Prakash\\Desktop\\Lucene\\Program\\Searching- Cosine\\fieldValue.txt");
		BM25BooleanQuery xquery = new BM25BooleanQuery(query,"all",new StandardAnalyzer());
		TopDocs top = searcher.search(xquery, null, 150);
		ScoreDoc[] docs = top.scoreDocs;
		idArrList=new ArrayList<Integer>();
		valArrList=new ArrayList<Double>();
		PrintWriter writer = new PrintWriter("D:\\Study\\summer_plan\\prp\\"+fileName+".txt", "UTF-8");
		for (int i = 0; i<top.scoreDocs.length; i++)
		{
			System.out.println(searcher.doc(docs[i].doc).get("docno") + "----:"+docs[i].score);
	     // System.out.println(docs[i].doc + "----:"+docs[i].score);
		  writer.println(searcher.doc(docs[i].doc).get("name") + "----:"+docs[i].score);
		  idArrList.add(docs[i].doc);
		  valArrList.add((double)docs[i].score);
		}
		writer.close();
		return docs;
	}
	public HashMap<Integer,Double> sortQPRP(String query,String fileName)throws Exception
	{
		ScoreDoc[] docs=getProbableRelvDoc(query,fileName);
		PrintWriter writerAbs = new PrintWriter("D:\\Study\\summer_plan\\result_absolute\\"+fileName+".txt", "UTF-8");
		PrintWriter writerFileName = new PrintWriter("D:\\Study\\summer_plan\\result\\"+fileName+".txt", "UTF-8");
		map=new HashMap<Integer,Double>();
		map.put(docs[0].doc,(double)docs[0].score);
		doc=searcher.doc(docs[0].doc);
		System.out.println("Doc ID:->"+docs[0].doc+"  File Name:->"+doc.get("docno"));
		writerAbs.println("Doc ID:->"+docs[0].doc+"  File Name:->"+doc.get("name"));
		writerFileName.println(doc.get("docno"));
		idArrList.remove(0);
		valArrList.remove(0);
		boolean status=true;
		for(int i=1;i<docs.length;i++)
		{
			tempScore=0.0;
			tempDoc=0;
			num=0;
		//	status=true;
			for(int j=0;j<idArrList.size();j++)
			{
				double sum=valArrList.get(j);
				for(int key: map.keySet())
				{
					double xscore=map.get(key);
					sum=sum+(2*Math.sqrt(valArrList.get(j))*Math.sqrt(xscore)*getSimilarity(idArrList.get(j),key));
				}
			/*	if(status)
				{
					tempDoc=idArrList.get(j);
					num=j;
					tempScore=sum;	
					status=false;
				}*/
				if(sum>tempScore)
				{
					tempDoc=idArrList.get(j);
					num=j;
					tempScore=sum;
				}
			}
			map.put(tempDoc,tempScore);
			idArrList.remove(num);
			valArrList.remove(num);
			doc=searcher.doc(tempDoc);
			System.out.println("Doc ID:->"+tempDoc+"  File Name:->"+doc.get("docno"));
			writerAbs.println("Doc ID:->"+tempDoc+"  File Name:->"+doc.get("name"));
			writerFileName.println(doc.get("docno"));
		}
		indexReader.close();
		writerAbs.close();
		writerFileName.close();
		return map;
	}
	
		
	public double getSimilarity(int docID1,int docID2)throws Exception
	{
		double coscore=title*testSimilarityUsingCosine(docID1,docID2,"title")+fp*testSimilarityUsingCosine(docID1,docID2,"fp")+sp*testSimilarityUsingCosine(docID1,docID2,"sp");
		return coscore;
	}
	
	public double testSimilarityUsingCosine(int docID1,int docID2,String xfield) throws Exception
	{
        // first find all terms in the index
		HashMap<String,Integer> hashTerms = new HashMap<String,Integer>();
		if(xfield.equals("title"))
		hashTerms=hashTitle;
		else if(xfield.equals("fp"))
		hashTerms=hashFirstPara;
		else if(xfield.equals("sp"))
		hashTerms=hashSecondPara;
		else
		{}
	    int[] docIds = new int[] {docID1,docID2};
		DocVector[] docs = new DocVector[docIds.length];
        int i = 0;
        for (int docId : docIds)
		{
		//try{
          TermFreqVector tfv = indexReader.getTermFreqVector(docId,xfield);
          docs[i] = new DocVector(hashTerms); 
          String[] termTexts = tfv.getTerms();
          int[] termFreqs = tfv.getTermFrequencies();
          for (int j = 0; j < termTexts.length; j++)
		  {
			docs[i].setEntry(termTexts[j], termFreqs[j]);
          }
          docs[i].normalize();
		 /* }catch(Exception e)
		  {
			e.printStackTrace();
		  }*/
          i++;
        }
		 // System.out.println(i);
        // now get similarity between doc[0] and doc[1]
        double cosim = getCosineSimilarity(docs[0], docs[1]);
		return cosim;
    }
	
	double getCosineSimilarity(DocVector d1, DocVector d2)
	{
		return (d1.vector.dotProduct(d2.vector))/(d1.vector.getNorm() * d2.vector.getNorm());
    }
}