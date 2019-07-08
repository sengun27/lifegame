package lifegame;

import java.util.TimerTask;

public class AutoAnimation extends TimerTask implements Runnable{
	private BoardModel m;
	private BoardView v;
	public AutoAnimation(BoardModel model, BoardView view) {
		m = model;
		v = view;
	}
	@Override
	public void run() {
		m.next();
		v.repaint();
	}
}