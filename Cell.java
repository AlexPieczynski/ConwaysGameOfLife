import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Class to represent a cell in Conway's Game of Life.
 * 
 * @author Alex  Pieczynski
 */
public class Cell extends JButton
{
	private static final Color ALIVE_COLOR = Color.yellow;
	private static final Color DEAD_COLOR = Color.gray;
	
	private boolean _alive;
	private ActionListener _listener;
	
	public Cell()
	{
		super();
		this.setPreferredSize(new Dimension(15,15));
		
		_listener = e ->
		{
			if (_alive)
				kill();
			else
				spawn();
		};
		
		this.reset();
	}
	
	public void kill()
	{
		_alive = false;
		setBackground(DEAD_COLOR);
	}
	
	public void spawn()
	{
		_alive = true;
		setBackground(ALIVE_COLOR);
	}
	
	public boolean isAlive()
	{
		return _alive;
	}
	
	/**
	 * Disables clicking on the grid so the user cannot kill/spawn cells
	 * once the simulation has started.
	 */
	public void onGameStart()
	{
		this.removeActionListener(_listener);
	}
	
	/**
	 * Puts the cell into its initial state, dead and waiting for clicks.
	 */
	public void reset()
	{
		this.addActionListener(_listener);
		this.kill();
	}
}