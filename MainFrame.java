import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * The main screen of the program.
 * 
 * @author Alex Pieczynski
 */
public class MainFrame extends JFrame
{
	private boolean _gameStarted = false;
	private CellGrid _cellGrid;
	private Timer _timer;
	private int _daysElapsed;
	
	public static enum Speed
	{
		Slow(1000), Medium(450), Fast(150);
		
		private int delay;
		private Speed(int delay)
		{
			this.delay = delay;
		}
	}
	
	/**
	 * Assembles all necessary parts of the user interface
	 */
	public MainFrame()
	{
		super("Conway's Game of Life");
		
		EmptyBorder padding = new EmptyBorder(5,5,5,5);
		_cellGrid = new CellGrid();
		_cellGrid.setBorder(padding);
		this.add(_cellGrid, BorderLayout.CENTER);
		
		
		GridLayout layout = new GridLayout();
		layout.setHgap(15);
		JPanel settingsPanel = new JPanel(layout);
		settingsPanel.setBorder(padding);
		
		_daysElapsed = 0;
		JLabel daysLabel = new JLabel("Days elapsed: " + _daysElapsed + "   ");
		settingsPanel.add(daysLabel, BorderLayout.PAGE_START);
		_timer = new Timer(0, e ->
		{
			_cellGrid.dayPassed();
			_daysElapsed++;
			daysLabel.setText("Days elapsed: " + _daysElapsed);
		});
		
		JComboBox<Speed> dropdown = new JComboBox<>(Speed.values());
		dropdown.setSelectedIndex(1);
		settingsPanel.add(dropdown);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener( e ->
		{
			if (!_gameStarted)
			{
				_gameStarted = true;
				_cellGrid.onGameStart();
				Speed speed = (Speed) dropdown.getSelectedItem();
				_timer.setDelay(speed.delay);
				_timer.start();
			}
			else // game is paused
				_timer.start();
			
			startButton.setEnabled(false);
		});
		settingsPanel.add(startButton);
		
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener( e ->
		{
			_timer.stop();
			startButton.setEnabled(true);
		});
		settingsPanel.add(pauseButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener( e ->
		{
			_timer.stop();
			_daysElapsed = 0;
			daysLabel.setText("Days Elapsed: "+_daysElapsed+"   ");
			_cellGrid.reset();
			_gameStarted = false;
			startButton.setEnabled(true);
		});
		settingsPanel.add(resetButton);
		
		this.add(settingsPanel, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	public static void main(String[] args)
	{
		new MainFrame().setVisible(true);
	}
}
