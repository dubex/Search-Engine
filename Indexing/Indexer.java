import java.io.IOException;
//import java.io.StringReader;
import java.io.File;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;

public class Indexer
{
	int i=0,error=0;
	
	Indexer()
	{	}
	IndexWriter indexWriter = null;
	    
    public IndexWriter getIndexWriter(boolean create) throws IOException
	{
        if (indexWriter == null)
		{
			Directory dir=FSDirectory.getDirectory(new File("D:\\Study\\summer_plan\\ProjectIndex"));
			indexWriter = new IndexWriter(dir,new StandardAnalyzer(),create,MaxFieldLength.UNLIMITED);
        }
        return indexWriter;
   }    
   
   public void closeIndexWriter() throws IOException
   {
        if (indexWriter != null)
		{
			indexWriter.commit();
            indexWriter.close();
        }
   }
   
   public void indexDocument(TryDOM tryDOM) throws IOException
   {
		try
		{
			//System.out.println("Indexing hotel: " + news);
			IndexWriter writer = getIndexWriter(false);
			Document doc = new Document();
			doc.add(new Field("name", tryDOM.getName(), Field.Store.YES, Field.Index.NO));
			doc.add(new Field("docno",tryDOM.getDocNum(), Field.Store.YES, Field.Index.NO));
			doc.add(new Field("title", tryDOM.getTitle(), Field.Store.YES, Field.Index.ANALYZED,TermVector.YES));
			doc.add(new Field("fp", tryDOM.getFirstPara(), Field.Store.YES, Field.Index.ANALYZED,TermVector.YES));
			doc.add(new Field("sp", tryDOM.getSecondPara(), Field.Store.YES, Field.Index.ANALYZED,TermVector.YES));
			doc.add(new Field("all",tryDOM.getWholeDocument(),Field.Store.YES, Field.Index.ANALYZED,TermVector.YES));
			writer.addDocument(doc);
			i++;
			System.out.println(i);
		}catch(Exception e)
		{
			System.out.println(tryDOM.getName());
			error++;
			
		}
		
    }
	public void recursion(File file) throws IOException
	{
		TryDOM tryDOM;
		if(file.isFile())
		{
			try{
			System.out.println(file);
			tryDOM=new TryDOM();
			tryDOM.buildDocument(file);
			indexDocument(tryDOM); 
			}catch(Exception e)
			{
				error++;
			}
		}
		else
		{
			File[] listOfFiles = file.listFiles(); 
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				recursion(listOfFiles[i]);
			}
		}
	}
	
	public void rebuildIndexes(String path) throws IOException
	{
		//TryDOM tryDOM;
		getIndexWriter(true);
      	File folder = new File(path);
		recursion(folder);
		/*File[] listOfFiles = folder.listFiles(); 
		for (File file:listOfFiles) 
		{
			if (file.isFile()) 
			{
				System.out.println(file);
				tryDOM=new TryDOM();
				tryDOM.buildDocument(file);
				indexDocument(tryDOM); 
			}
		}*/
		System.out.println("Error==> "+error);
		closeIndexWriter();
    }  
}