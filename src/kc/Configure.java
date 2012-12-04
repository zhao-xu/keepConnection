package kc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;
import oracle.ide.Ide;

public class Configure extends Common
implements Addin
{
	static String title = "Keep Connected - v2.1";
	static String timeoutDefault = "4";

	public static String getPropPath()
	{
		// you'll find this file in the folder ..\sqldeveloper x.x.x.x\sqldeveloper\bin
		return System.getProperty( "user.dir" ) + System.getProperty( "file.separator" ) + "keepconn.properties";
	}
	public void launch()
	{
		JFrame mainWin = Ide.getMainWindow();
		try {
			String tmout = null;
			String cn = getConnName();

			tmout = getSavedTimeout();

			tmout = JOptionPane.showInputDialog( mainWin, "Configure ping interval (in minutes) for:\n" + cn, tmout );

			if( tmout != null ) {
				if( Integer.parseInt(tmout) < 1 ) {
					tmout = timeoutDefault;
				}
				saveTimeout( cn, tmout );
				
				Pinger p = Pinger.getPinger( cn );
				if( p == null )
					JOptionPane.showMessageDialog( mainWin, "Saved interval for connection:  " + cn + "  ---  " + tmout + " minutes.\nPinger not active for this connection.\n", title, 1 );
				else {
					if( p.isValid() ) {
						p.setTimeout( tmout );
						JOptionPane.showMessageDialog( mainWin, "Dynamically changed interval on existing active pinger on connection:\n" + cn + "  to " + tmout + " minutes.\n", title, 1 );
					} else {
						Pinger.removePinger( cn );
						newPinger( p, cn, tmout );
						JOptionPane.showMessageDialog( mainWin, "Re-Activated new pinger on:\n" + cn + "\nPing every " + tmout + " minute(s).", Configure.title, 1 );
					}
				}
			} else {
				JOptionPane.showMessageDialog( mainWin, "No change.", title, 1 );
			}
		} catch( NumberFormatException e ) {
			JOptionPane.showMessageDialog( mainWin, "Invalid value entered. Please try again.", title, 1 );
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