import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.HashMap;
public class Main
{
	public static void main(String args[])
	{
		try 
		{
			SearchFrame frm=new SearchFrame("Search Using New Sim");
			Toolkit theKit=frm.getToolkit();
			Dimension wndSize=theKit.getScreenSize();
			frm.setBounds(wndSize.width/3,wndSize.height/5,wndSize.width/3,wndSize.height/4);
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
			// build a lucene index
		/*	String query="Harass slur on Citu leader Security recce at Tagore varsity";
			System.out.println("Starting Search");
			MySearcher searcher=new MySearcher();
			HashMap<Integer,Double> finalMap=searcher.sortQPRP(query);*/
		} 
		catch (Exception e)
		{
			System.out.println("Exception caught: ");
			e.printStackTrace();
		}
	}
}