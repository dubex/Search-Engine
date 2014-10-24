public class Main
{

    
    public static void main(String args[])
    {
        try
        {
            System.out.println("rebuildIndexes");
            Indexer indexer = new Indexer();
            indexer.rebuildIndexes("C:\\Users\\Prakash\\Downloads\\data\\over");
            System.out.println("rebuildIndexes done");
        }
        catch(Exception exception)
        {
            System.out.println("Exception caught: ");
            exception.printStackTrace();
        }
    }
}
