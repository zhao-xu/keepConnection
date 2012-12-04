package kc;

import oracle.dbtools.raptor.utils.DBObject;
import oracle.ide.Ide;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Pinger extends Thread
{
	private static HashMap<String,Pinger> pingers = new HashMap<String,Pinger>();

	public static void addPinger( String cn, Pinger p )
	{
		pingers.put( cn, p );
	}
	public static Pinger getPinger( String cn )
	{
		if( pingers.containsKey( cn ))
			return pingers.get( cn );
		else
			return null;
	}
	public static void removePinger( String cn ) 
	{
		pingers.remove( cn );
	}
	public static void removeAllPingers() 
	{
		pingers.clear();
	}
	public static Boolean isEmptyList()
	{
		return pingers.isEmpty();
	}
	public static Set<Entry<String,Pinger>> getEntrySet() {
		Set<Entry<String,Pinger>> e = pingers.entrySet();
		return e;
	}
	public static String getPingerNames() 
	{
		String names = "";
		for( Entry<String,Pinger> entry : pingers.entrySet() )
		{
			names += "\n" + entry.getKey();
		} 
		return names;
	}
	public static Boolean checkValidPingers() 
	{
		String validList = "";
		String inValidList = "";
		Pinger p;
		for( Entry<String,Pinger> entry : pingers.entrySet() )
		{
			p = entry.getValue();
			if( p.isValid() )
				validList += "\n   " + p.getConnName();
			else
				inValidList += "\n   " + p.getConnName();
		}
        StringBuilder sb = new StringBuilder(100);
        sb.append("Valid Pingers: ");
        sb.append(validList);
        sb.append("\n\n");
        sb.append("Invalid Pingers:");
        sb.append(inValidList);
//		JOptionPane.showMessageDialog( Ide.getMainWindow(), "Valid Pingers: " + validList, Configure.title, 1 );
//		if( inValidList.isEmpty() )
//			JOptionPane.showMessageDialog( Ide.getMainWindow(), "No Invalid Pingers", Configure.title, 1 );
//		else
//			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Invalid Pingers: " + inValidList, Configure.title, 1 );
		JOptionPane.showMessageDialog( Ide.getMainWindow(), sb.toString(), Configure.title, 1 );

		if( inValidList.isEmpty() )
			return true;
		else
			return false;
	}

	//
	// static stuff above, object stuff below
	//
	
	DBObject db;
	String cn;
	private Connection conn;
	int timeout;
	Boolean keepGoing = true;
	Boolean except = false;
	
	Pinger( DBObject dbi, String cni )
	{
		db = dbi;
		cn = cni;
		conn = db.getDatabase().getConnection();
	}
	public void setTimeout( String to )
	{
		int t = Integer.parseInt( to );
		timeout = t * 60 * 1000;  // given time in minutes must be converted to milliseconds
	}
	public String getTimeout()
	{
		return Integer.toString( timeout );
	}
	public String getConnName()
	{
		return cn;
	}
	public void end()
	{
		keepGoing = false;
	}
	public Boolean isValid()
	{
		if( except )
			return false;
		
		Boolean ret = false;
		try {
			Statement stmt = conn.createStatement();
			String q = "select sysdate from dual";
			stmt.executeQuery( q );
			ret = true;
		} catch( Exception e )
		{
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exception: " + e.getMessage() + "\n\nPlease re-activate pinger for connection if needed: " + cn, Configure.title, 1 );
		}
		return ret;
	}
	public void run()
	{
		try
		{
			// createStatement call must not be in the while loop, 
			// if it is then you'll eventually get a "Max Open Cursors" EXCEPTION !!!
			Statement stmt = conn.createStatement();
			String q = "select sysdate from dual";

			while( keepGoing ) 
			{
				stmt.executeQuery( q );
				Thread.sleep( timeout );
			}
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exited ping loop for: " + cn, Configure.title, 1 );
		}
		catch( InterruptedException e )
		{
			// don't put a pop-up msg box in here, it will hang the app
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog( Ide.getMainWindow(), "Exception: " + e.getMessage() + "\n\nPlease re-activate pinger for connection if needed: " + cn, Configure.title, 1 );
			except = true;
		}
	}
}