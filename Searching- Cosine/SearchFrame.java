import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
public class SearchFrame extends JFrame
{
	public String tStrQuery,tStrFile;
	public JPanel pan;
	public JTextField tSearch,tFile;
	public JLabel lFile,lSearch;
	public JButton bSearch;
	public SearchFrame(String str)
	{
		super(str);
		pan=new JPanel(new FlowLayout(FlowLayout.CENTER,30,20));
		lFile=new JLabel("File Name");
		lSearch=new JLabel("Query :");
		tSearch=new JTextField(20);
		tFile=new JTextField(10);
		bSearch=new JButton("Search");
		pan.add(bSearch);
		
		Box top=Box.createHorizontalBox();
		top.add(Box.createHorizontalStrut(20));
		top.add(lSearch);
		top.add(Box.createHorizontalStrut(20));
		top.add(tSearch);
		top.add(Box.createHorizontalStrut(20));
		
		Box down=Box.createHorizontalBox();
		down.add(Box.createHorizontalStrut(20));
		down.add(lFile);
		down.add(Box.createHorizontalStrut(40));
		down.add(tFile);
		down.add(Box.createHorizontalStrut(20));
		down.add(pan);
		down.add(Box.createHorizontalStrut(20));

		
		Box ver=Box.createVerticalBox();
		ver.add(Box.createVerticalStrut(20));
		ver.add(top);
		ver.add(Box.createVerticalStrut(20));
		ver.add(down);
		ver.add(Box.createVerticalStrut(20));
		
		Container content=getContentPane();
		content.setLayout(new BorderLayout(30,10));
		content.add(ver,BorderLayout.CENTER);
	//	content.add(verDown,BorderLayout.SOUTH);
		
		MyBtnListener listen=new MyBtnListener();
		bSearch.addActionListener(listen);
	}
	private class MyBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evn) 
		{
			tStrQuery=tSearch.getText();
			tStrFile=tFile.getText();
			try
			{
				System.out.println("Starting Search");
				MySearcher searcher=new MySearcher();
				HashMap<Integer,Double> finalMap=searcher.sortQPRP(tStrQuery,tStrFile);
				//POP Window to say program is over
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
				
		}
	}
}
