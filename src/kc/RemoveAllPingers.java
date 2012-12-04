package kc;

import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;
import oracle.ide.Ide;

import javax.swing.*;
import java.util.Map.Entry;

public class RemoveAllPingers extends Common
implements Addin
{
	public void launch()
	{
		JFrame mainWin = Ide.getMainWindow();
		try {
			if( Pinger.isEmptyList( )) {
//				JOptionPane.showMessageDialog( mainWin, "No pingers to remove.", Configure.title, 1 );
			} else {
				for( Entry<String,Pinger> entry : Pinger.getEntrySet() )
				{
					Pinger p = entry.getValue();
					String connName = p.getConnName();
					p.interrupt();
					p.join();
//					JOptionPane.showMessageDialog( mainWin, "Pinger on  " + connName + "  ended.", Configure.title, 1 );
				} 
				Pinger.removeAllPingers();
//				JOptionPane.showMessageDialog( mainWin, "All pingers ended.", Configure.title, 1 );
			}
		} catch (Exception e) {
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