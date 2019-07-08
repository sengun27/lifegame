package lifegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

class NextButton implements ActionListener{
	private BoardModel m;
	private BoardView v;
	public NextButton(BoardModel model,BoardView view) {
		m = model;
		v = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		m.next();
		v.repaint();
	}
}

class UndoButton implements ActionListener{
	private BoardModel m;
	private BoardView v;
	public UndoButton(BoardModel model,BoardView view) {
		m = model;
		v = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		m.undo();
		v.repaint();
	}
}

class NewGameButton implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		Main.main(null);
	}
}

class ModelUndo implements BoardListener{
    private JButton b;
    public ModelUndo(JButton buttonUndo) {
    	b = buttonUndo;
    }
	@Override
	public void updated(BoardModel m) {
		b.setEnabled(m.isUndoable());
	}
}

class AutoButton implements ActionListener{
	private BoardModel m;
	private BoardView v;
	private JButton b;
	public AutoButton(BoardModel model, BoardView view,JButton button){
		m = model;
		v = view;
		b = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		AutoAnimation auto = new AutoAnimation(m,v);
		Timer timer = new Timer(true);
		timer.schedule(auto,0,500);
		b.setEnabled(false);
	}
}

public class Main implements Runnable {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}
	public void run() {
		BoardModel model = new BoardModel(10, 10);
		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(500, 400));
		frame.setMinimumSize(new Dimension(500, 400));
		
		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		base.add(view, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
	    
		JButton buttonNext = new JButton("next");
		ActionListener listenerNext = new NextButton(model,view);
		buttonNext.addActionListener(listenerNext);
	    buttonPanel.add(buttonNext);
	    
	    JButton buttonUndo = new JButton("undo");
		ActionListener listenerUndo = new UndoButton(model,view);
		buttonUndo.addActionListener(listenerUndo);
	    buttonPanel.add(buttonUndo);
	    
	    buttonUndo.setEnabled(false);
	    model.addListener(new ModelUndo(buttonUndo));
	    
	    JButton buttonNew = new JButton("New Game");
	    buttonNew.addActionListener(new NewGameButton());
	    buttonPanel.add(buttonNew);
	    
	    JButton buttonAuto = new JButton("Auto");
		ActionListener listenerAuto = new AutoButton(model,view,buttonAuto);
		buttonAuto.addActionListener(listenerAuto);
	    buttonPanel.add(buttonAuto);
	   
		frame.pack();
		frame.setVisible(true);		   
	}
}
