package kc;

import oracle.dbtools.raptor.dialogs.actions.AbstractMenuAction;
import oracle.dbtools.raptor.dialogs.actions.XMLBasedObjectAction;
import oracle.ide.Addin;
import oracle.ide.Ide;

import javax.swing.*;
import java.awt.*;

public class About extends AbstractMenuAction
implements Addin
{
	public void launch()
	{
		JTextArea txt = new JTextArea( 2, 4 );
		JOptionPane pane = new JOptionPane();
		Font font = pane.getFont();

		txt.setText(
                "Keep Connected\n" +
                " - remove annoying popups.\n" +
                "\n" +
                "Origin info -\n" +
                "For updates, please visit:\n" +
				" - sites.google.com/site/keepconnext\n" +
				"Summary:\n" +
				" - Ping with 'select sysdate from dual' every X minutes.\n" +
				" - Will not repeatedly open cursors; one cursor is reused.\n" +
				" - Defaults properly saved and retrieved.\n" +
				" - Detect invalid Pingers and notify user.\n" +
				" - Catch all exceptions and notify user.\n" + 
				"Functions:\n" +
				" - Activate and Configure timeout per connection.\n" +
				" - List valid & invalid pingers.\n" +
				" - Remove Pingers (current or ALL).\n" +
				" - Dynamically change ping interval, no need to restart Pinger.\n" );
		txt.setEnabled( true );
		txt.setBackground( pane.getBackground() );
		txt.setFont( font );
		txt.setBorder( null );
		JOptionPane.showMessageDialog( Ide.getMainWindow(), txt, Configure.title, 1 );
	}
	public void setArgs( String args )
	{
	}
	public void initialize()
	{
		XMLBasedObjectAction.registerContextMenus( getClass().getResource( "menu.xml" ));
	}
}