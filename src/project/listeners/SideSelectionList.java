package project.listeners;

import java.util.Arrays;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SideSelectionList extends JList<String> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	public SideSelectionList ( String[] args )
	{
		super ( args );
		addListSelectionListener ( this );
	}
	
	@Override
	public void valueChanged ( ListSelectionEvent e )
	{
		int[] selectedInidices = getSelectedIndices ( );
		
		if ( selectedInidices.length > 1 )
		{
			selectedInidices = Arrays.copyOf ( selectedInidices, 1 );
			setSelectedIndices ( selectedInidices );
		}
	}
	
}
