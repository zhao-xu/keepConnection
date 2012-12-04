package kc;

import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;
import oracle.ide.Ide;

import javax.swing.*;

public class Activate extends Common
implements Addin
{
	public void launch()
	{
		JFrame mainWin = Ide.getMainWindow();
		try {
			String cn = getConnName();
			
			String tmout = getSavedTimeout();

//			JOptionPane.showMessageDialog( mainWin, "If not connected to " + cn + ", a connection will be created with timeout\n" + tmout + " min.\nThis may take a moment if you are remote.", Configure.title, 1 );
			
			Pinger p = Pinger.getPinger( cn );
			if( p != null )
			{
				if( p.isValid() ) {
					p.setTimeout( tmout );
//					JOptionPane.showMessageDialog( mainWin, "Set timeout for ALREADY activated pinger on  " + cn + ".\nPing every " + tmout + " minute(s).", Configure.title, 1 );
				} else {
					Pinger.removePinger( cn );
					newPinger( p, cn, tmout );
//					JOptionPane.showMessageDialog( mainWin, "Re-Activated new pinger on:  " + cn + "\nPing every " + tmout + " minute(s).", Configure.title, 1 );
				}
			}
			else if( getDBObject().getDatabase().isConnectionAlive() )
			{
				newPinger( p, cn, tmout );
//				JOptionPane.showMessageDialog( mainWin, "Activated pinger on:  " + cn + "\nPing every " + tmout + " minute(s).", Configure.title, 1 );
			}
			else {
//				JOptionPane.showMessageDialog( mainWin, "Connection error.", Configure.title, 0 );
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