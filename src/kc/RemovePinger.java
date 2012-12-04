package kc;

import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;
import oracle.ide.Ide;

import javax.swing.*;

public class RemovePinger extends Common
implements Addin
{
	public void launch()
	{
		String connName = getConnName();
		JFrame mainWin = Ide.getMainWindow();
		try {
			Pinger p = Pinger.getPinger( connName );
			if( p == null ) {
//				JOptionPane.showMessageDialog( mainWin, "No pinger to remove on this connection:\n" + connName, Configure.title, 1 );
            } else {
				p.interrupt();
				p.join();
				Pinger.removePinger( connName );
//				JOptionPane.showMessageDialog( mainWin, "Pinger on  " + connName + "  ended.", Configure.title, 1 );
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog( mainWin, "Exception: " + e.getMessage(), Configure.title, 1 );
		}
	}
	public void setArgs( String args )
	{
	}
	public void initialize()
	{
		XMLBasedObjectAction.registerContextMenus( getClass().getResource( "menu.xml" ));
	}
}