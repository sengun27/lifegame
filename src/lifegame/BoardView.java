package lifegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardView extends JPanel implements MouseListener, MouseMotionListener{
	private BoardModel m;
	private int x = -1, y = -1;
	
	public BoardView(BoardModel model) {
		m = model;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);	
		int cellHeight, cellWidth;
		if(this.getHeight() / m.getRows() < this.getWidth() / m.getCols()) {
			cellHeight = this.getHeight() / m.getRows();
			cellWidth = cellHeight;
		}else {
			cellWidth = this.getWidth() / m.getCols();
			cellHeight = cellWidth;
		}
		
		int width = cellWidth * m.getCols();
		int height = cellHeight * m.getRows();
		int startWidth = (this.getWidth() - width) /2;
		int startHeight = (this.getHeight() - height)/2;
		
		for(int i = startHeight ; i < startHeight + height ; i += cellHeight) {
			for(int j= startWidth ; j < startWidth + width ; j += cellWidth) {
				g.drawLine(j, i, j, i+cellHeight);
				g.drawLine(j, i, j+cellWidth, i);
				g.drawLine(j+cellWidth, i, j+cellWidth, i+cellHeight);
				g.drawLine(j, i+cellHeight, j+cellWidth, i+cellHeight);
				if(m.isAlive((j - startWidth)/cellWidth, (i - startHeight)/cellHeight) == true) g.fillRect(j+1, i+1, cellWidth-1, cellHeight-1);		
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int cellHeight, cellWidth;
		Point point = e.getPoint();
	    if(this.getHeight() / m.getRows() < this.getWidth() / m.getCols()) {
			cellHeight = this.getHeight() / m.getRows();
			cellWidth = cellHeight;
		}else {
			cellWidth = this.getWidth() / m.getCols();
			cellHeight = cellWidth;
		}
		int width = cellWidth * m.getCols();
		int height = cellHeight * m.getRows();
		int startWidth = (this.getWidth() - width) /2;
		int startHeight = (this.getHeight() - height)/2;
			
		if(startWidth >= point.x || point.x >= (startWidth + width) || 
			startHeight >= point.y || point.y >= (startHeight + height)) {
			return;
		}
		if( x != ((point.x - startWidth) / cellWidth) || y != ((point.y - startHeight) / cellHeight)){
			x = (point.x - startWidth) / cellWidth;
			y = (point.y - startHeight) / cellHeight;
			m.changeCellState(x, y); 
			this.repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int cellHeight, cellWidth;
		Point point = e.getPoint();
	    if(this.getHeight() / m.getRows() < this.getWidth() / m.getCols()) {
			cellHeight = this.getHeight() / m.getRows();
			cellWidth = cellHeight;
		}else {
			cellWidth = this.getWidth() / m.getCols();
			cellHeight = cellWidth;
		}
		int width = cellWidth * m.getCols();
		int height = cellHeight * m.getRows();
		int startWidth = (this.getWidth() - width) /2;
		int startHeight = (this.getHeight() - height)/2;
			
		if(startWidth >= point.x || point.x >= (startWidth + width) || 
			startHeight >= point.y || point.y >= (startHeight + height)) {
			return;
		}
		x = (point.x - startWidth) / cellWidth;
		y = (point.y - startHeight) / cellHeight;
		m.changeCellState(x, y); 
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
