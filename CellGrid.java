import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Contains the logic and visual representation of the cell grid.
 * A user can click on the Cells to spawn/kill them to set the initial
 *   configuration of the board.
 * 
 * @author Alex Pieczynski
 */
public class CellGrid extends JPanel
{
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 30;
	
	private Cell[][] _cells = new Cell[DEFAULT_HEIGHT][DEFAULT_WIDTH];
	
	public CellGrid()
	{
		super(new GridLayout(DEFAULT_HEIGHT, DEFAULT_WIDTH));
		
		for (int i=0; i < DEFAULT_HEIGHT; i++)
		{
			for (int j=0; j < DEFAULT_WIDTH; j++)
			{
				Cell c = new Cell();
				_cells[i][j] = c; 
				this.add(c);
			}
		}
	}
	
	/**
	 * disables the grid from being clicked after the simulation starts
	 */
	public void onGameStart()
	{
		for (Cell[] row : _cells)
			for (Cell cell : row)
				cell.onGameStart();
	}
	
	/**
	 * makes the grid clickable again 
	 */
	public void reset()
	{
		for (Cell[] row : _cells)
			for (Cell cell : row)
				cell.reset();
	}
	
	/**
	 * Called every time a day passes in the simulation.
	 * Spawns and kills cells according to the standard rules of Conway's Game of Life.
	 */
	public void dayPassed()
	{
		// can't change state until the fate of all cells is decided
		ArrayList<Cell> toBeChanged = new ArrayList<>();
		
		for (int i=0; i < DEFAULT_HEIGHT; i++)
		{
			for (int j=0; j < DEFAULT_WIDTH; j++)
			{
				Cell currentCell = _cells[i][j];
				int liveNeighbors = 0;
				boolean canCheckEast = j+1 < DEFAULT_WIDTH;
				boolean canCheckWest = j-1 >= 0;
				
				// check Northern neighbors
				if (i-1 >= 0)
				{
					if (_cells[i-1][j].isAlive())
						liveNeighbors++;
					if (canCheckEast && _cells[i-1][j+1].isAlive())
						liveNeighbors++;
					if (canCheckWest && _cells[i-1][j-1].isAlive())
						liveNeighbors++;
				}
				
				// check southern neighbors
				if (i+1 < DEFAULT_HEIGHT)
				{
					if (_cells[i+1][j].isAlive())
						liveNeighbors++;
					if (canCheckEast && _cells[i+1][j+1].isAlive())
						liveNeighbors++;
					if (canCheckWest && _cells[i+1][j-1].isAlive())
						liveNeighbors++;
				}
				
				// check eastern and western neighbors
				if (canCheckEast && _cells[i][j+1].isAlive())
					liveNeighbors++;
				if (canCheckWest && _cells[i][j-1].isAlive())
					liveNeighbors++;
					
				// decide fate of current cell
				if (liveNeighbors < 2)
				{
					if (currentCell.isAlive())
						toBeChanged.add(currentCell);
				}
				else if (liveNeighbors < 4)
				{
					if (!currentCell.isAlive() && liveNeighbors == 3)
						toBeChanged.add(currentCell);
				}
				else
				{
					if (currentCell.isAlive())
						toBeChanged.add(currentCell);
				}
			}
		}
		
		for (Cell c : toBeChanged)
		{
			if (c.isAlive())
				c.kill();
			else
				c.spawn();
		}
	}
	
}
