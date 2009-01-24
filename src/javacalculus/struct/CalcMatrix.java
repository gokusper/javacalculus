/**
 * 
 */
package javacalculus.struct;

import javacalculus.core.CALC;
import javacalculus.exception.CalcDimensionException;

/**
 * @author Duyun Chen <A HREF="mailto:duchen@seas.upenn.edu">[duchen@seas.upenn.edu]</A>,
 * Seth Shannin <A HREF="mailto:sshannin@seas.upenn.edu">[sshannin@seas.upenn.edu]</A>
 *  
 *
 */
public class CalcMatrix implements CalcObject {
	
	private CalcVector[] elements;
	private int width = 0, height = 0;
	
	public static final char matrixOpen = '[';
	public static final char matrixClose = ']';
	public static final char matrixDelim = ',';
	
	/**
	 * Default constructor
	 */
	public CalcMatrix() {}
	
	/**
	 * Constructor for a zero matrix with given width and height
	 * @param width
	 * @param height
	 */
	public CalcMatrix(int width, int height) {
		elements = new CalcVector[height];
		
		for (int ii = 0; ii < elements.length; ii++) {
			elements[ii] = new CalcVector(width);
		}
		
		this.width = width;
		this.height = height;
	}
	
	public CalcMatrix(CalcVector[] elements) {
		this.elements = elements;
		height = elements.length;
		width = elements[0].size();
	}
	
	/**
	 * 
	 * @param row
	 * @return the vector at row
	 */
	public CalcVector get(int row) {
		return elements[row];
	}
	
	/**
	 * Returns the element matrix at i = <b>row</b> and j = <b>col</b> 
	 * @param row
	 * @param col
	 * @return
	 */
	public CalcObject get(int row, int col) {
		return elements[row].get(col);
	}
	
	/**
	 * Set obj into (row, col) in this matrix
	 * @param row
	 * @param col
	 * @param obj
	 */
	public void set(int row, int col, CalcObject obj) {
		elements[row].set(col, obj);
	}
	
	/**
	 * 
	 * @return every element of this matrix in a 1D array
	 */
	public CalcVector[] getAll() {
		return elements;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public CalcObject add(CalcMatrix input) {
		if (input.getWidth() != width || input.getHeight() != height) {
			throw new CalcDimensionException("Adding matrices or vectors of different dimensions");
		}
		
		CalcVector[] inputElements = input.getAll();
		
		for (int ii = 0; ii < elements.length; ii++) {
			elements[ii] = elements[ii].add(inputElements[ii]);
		}
		
		return this;
	}
	
	public CalcObject multiply(CalcObject input) {
		if (input.isNumber()) { //multiply matrix by a scalar
			for (int ii = 0; ii < elements.length; ii++) {
				elements[ii] = elements[ii].multiply(input);
			}
			return this;
		}
		if (input instanceof CalcMatrix) { //multiply matrix by a matrix
			CalcMatrix matrix = (CalcMatrix) input;
			if (matrix.getHeight() != width) {
				throw new CalcDimensionException("Incompatible dimensions in matrix multiplication");
			}
			
			CalcVector[] inputElements = matrix.getAll();
			
			
		}
		
		return this;
	}
	
	public Object clone() {
		return new CalcMatrix(elements);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		
		buffer.append(matrixOpen);
		
		for (int ii = 0; ii < height; ii++) {
			buffer.append(matrixOpen);
			for (int jj = 0; jj < width; jj++) {
				if (jj > 0) buffer.append(matrixDelim);
				buffer.append(get(ii, jj).toString());
				index++;
			}
			buffer.append(matrixClose);
			if (ii < height - 1) buffer.append(matrixDelim);
		}
		
		buffer.append(matrixClose);
		
		return buffer.toString();
	}

	@Override
	public int compareTo(CalcObject obj) {
		//TODO figure out how to determine if one matrix is greater than/less than than another
		if (getHierarchy() > obj.getHierarchy()) {
			return 1;
		}
		else if (getHierarchy() < obj.getHierarchy()) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public CalcObject evaluate() throws Exception {
		//evaluate every element first
		for (int ii = 0; ii < elements.length; ii++) {
			elements[ii].evaluate();
		}
		return this;
	}
	
	@Override
	public CalcSymbol getHeader() {
		return CALC.MATRIX;
	}
	
	@Override
	public int getHierarchy() {
		return CalcObject.MATRIX;
	}
	
	@Override
	public int getPrecedence() {
		return 0;
	}
	
	@Override
	public boolean isNumber() {
		return false;
	}
	
	@Override
	public StringBuffer toStringBuffer() {
		return null;
	}
}