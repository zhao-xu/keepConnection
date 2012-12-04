package kc;

//import javax.swing.JOptionPane;
//import oracle.ide.Ide;

import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;

public class ListPingers extends Common
implements Addin
{
	public void launch()
	{
		Pinger.checkValidPingers();
		
		/*
		try {
			String names = Pinger.getPingerNames();
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Pingers:  " + names, Configure.title, 1 );
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exception: " + e.getMessage(), Configure.title, 1 );
		}
		*/
	}
	public void setArgs( String args )
	{
	}
	public void initialize()
	{
		XMLBasedObjectAction.registerContextMenus( getClass().getResource( "menu.xml" ));
	}
}