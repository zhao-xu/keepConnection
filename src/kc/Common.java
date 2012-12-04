package kc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import oracle.dbtools.raptor.dialogs.actions.AbstractMenuAction;
import oracle.ide.Ide;

public abstract class Common extends AbstractMenuAction
{
	private Properties prop;
	
	public String getConnName()
	{
		String cn = null;
		int pos = getDBObject().getConnectionName().indexOf( "%23" ) + 3;
		cn = getDBObject().getConnectionName().substring( pos );
		return cn;
	}
	public String getSavedTimeout()
	{
		prop = new Properties();
		try {
			File check = new File( Configure.getPropPath() );
			if (!check.exists())
				check.createNewFile();

			prop.load( new FileInputStream( Configure.getPropPath() ));
		} catch( IOException e ) {
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exception: " + e.getMessage(), Configure.title, 1 );
		}
		String savedTime = prop.getProperty( getConnName() );
		if (savedTime != null)
			return savedTime.trim();
		else
			return Configure.timeoutDefault;
	}
	// assumed that getSavedTimeout() is called before this call
	// so that ALL previously fetched props in file can be re-saved
	public void saveTimeout( String connName, String timeout )
	{
		try {
			prop.setProperty( connName, timeout );
			prop.store( new FileOutputStream( Configure.getPropPath()), null );
		} catch( IOException e ) {
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exception: " + e.getMessage(), Configure.title, 1 );
		}
	}
	public void newPinger( Pinger p, String cn, String timeout)
	{
		p = new Pinger( getDBObject(), cn );
		p.setTimeout( timeout );
		Pinger.addPinger( cn, p );  // add to list of Pingers
		p.start();
	}
}