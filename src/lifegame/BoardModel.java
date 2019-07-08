package lifegame;
import java.util.*;

public class BoardModel {
	private int cols;
	private int rows;
	private boolean[][] cells;
	private ArrayList<BoardListener> listeners;
	Deque<Boolean[][]> deque = new ArrayDeque<>();
	private Boolean[][] wrappCells;
	
	public BoardModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows][cols];
		listeners = new ArrayList<BoardListener>();
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getRows() {
		return rows;
	}
	
	public boolean isAlive(int x, int y) {
		if(cells[y][x] == true) return true;
		else return false;
	}
	
	public void changeCellState(int x, int y) {
        pushToDeque();
		cells[y][x] = (cells[y][x] == false) ? true : false ;
	    fireUpdate();
	}
	
	private void createWrappCells() {
		wrappCells = new Boolean[rows][cols];
		for(int i = 0; i < getRows() ; i++) {
    		for(int j = 0 ; j < getCols() ; j++) {
    			wrappCells[i][j]= cells[i][j];
    		}
    	}
	}
	
	private void pushToDeque(){
		if(deque.size() >= 32) {
	    	deque.removeLast();
	    }
    	createWrappCells();
    	deque.push(wrappCells);
    }
	
	public void undo() {
		wrappCells = deque.pop();
		for(int i = 0 ; i < getRows() ; i++) {
			for(int j = 0 ; j < getCols() ; j++) {
				cells[i][j] = wrappCells[i][j];
			}
		}
		fireUpdate();
	}
	
	public boolean isUndoable() {
		if(deque.size() > 0) return true;
		else return false;
	}
   
	public void next() {
    	boolean[][] cellsStatic = new boolean[getRows()][getCols()];
        pushToDeque();
    	for(int i = 0 ; i < getRows() ; i++) {
    		cellsStatic[i] = cells[i].clone();
    	}
        for(int i = 0 ; i < getRows() ; i++) {
        	for(int j = 0 ; j < getCols() ; j++) {
        		cellsStatic[i][j] = lifechecker(i,j,cellsStatic[i][j]);
        	} 
        } 
        for(int i = 0 ; i < getRows() ; i++) {
    		cells[i] = cellsStatic[i].clone();
    	}
        fireUpdate();
    }
    
    private boolean lifechecker(int y, int x,boolean cellCheck) {
    	int counter = 0;
    	for(int i = y-1 ; i <= y+1 ; i++) {
    		for(int j = x-1 ; j <= x+1 ; j++ ) {
    			if(i < 0 || i > getRows()-1 || j < 0 || j > getCols()-1) continue;
    			if( !(i == y && j == x) && cells[i][j] == true) counter++;
    		}
    	}
    	if(cellCheck == true && (counter == 2 || counter == 3)) {
    		return true;
    	 }
    	else if(cellCheck == false && counter == 3) return true;
    	else return false; 	
    } 
	
    public void addListener(BoardListener listener) {
		listeners.add(listener);
    }
    
    private void fireUpdate() {
		for (BoardListener listener: listeners) {
			listener.updated(this);
		}
	} 
}
