import org.apache.commons.math.linear.OpenMapRealMatrix;
import org.apache.commons.math.linear.RealVectorFormat;
import org.apache.commons.math.linear.SparseRealMatrix;
import java.util.Map;
public class DocMatrix
{
    public SparseRealMatrix matrix;
	public SparseRealMatrix transposeMatrix;
	public Map<String,Integer> terms;
	public DocMatrix(Map<String,Integer> terms)
	{
		this.terms = terms;
        this.matrix = new OpenMapRealMatrix(terms.size(),1);
	//	this.transposeMatrix=new OpenMapRealMatrix(1,terms.size());
    }
      
    public void setEntry(String term, int freq)
	{
        if (terms.containsKey(term)) 
		{
          int pos = terms.get(term);
		//  System.out.println(freq+" "+pos);
		  matrix.setEntry(pos,0, (double)freq);
		}
    }
    public void doTranspose()
	{
		transposeMatrix=(SparseRealMatrix)matrix.transpose();
	}
    public void normalize()
	{
		double sum = matrix.getNorm();
        matrix = (SparseRealMatrix) matrix.scalarMultiply(2/sum);
	}
  /*    
    public String toString() 
	{
        RealVectorFormat formatter = new RealVectorFormat();
        return formatter.format(vector);
    }*/
 
}