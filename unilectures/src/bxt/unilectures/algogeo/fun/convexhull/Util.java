package bxt.unilectures.algogeo.fun.convexhull;

import java.awt.geom.Point2D;
import java.util.Comparator;

public abstract class Util {
	
	/**
	 * Check if <tt>r</tt> is on the line segment between <tt>q</tt> and <tt>q</tt>.
	 * @param r Point to check.
	 * @param p One endpoint of the line segment.
	 * @param q Other endpoint of the line segment.
	 * @return If <tt>r</tt> is on the line segment <tt>pq</tt>.
	 */
	public static boolean onLine(Point2D r, Point2D p, Point2D q) {
		boolean onLine = (q.getX() - p.getX()) * (r.getY() - p.getY()) == (r.getX() - p.getX()) * (q.getY() - p.getY());
		return onLine && 
                Math.abs(Math.signum(p.getX() - r.getX()) + Math.signum(q.getX() - r.getX())) <= 1 &&
                Math.abs(Math.signum(p.getY() - r.getY()) + Math.signum(q.getY() - r.getY())) <= 1 ;
	}
	
	/**
	 * Check if a point is strictly right of a line.
	 * @param r Point to check.
	 * @param p A point on the line.
	 * @param q Another point on the line.
	 * @return If <tt>r</tt> is on the line <tt>pq</tt>.
	 */
	public static boolean strictlyRight(Point2D r, Point2D p, Point2D q) {
		double det = determinant3(
				r.getX(), r.getY(), 1.0,
				p.getX(), p.getY(), 1.0,
				q.getX(), q.getY(), 1.0);
		return det < 0;
	}
	
	/**
	 * Calculate the determinant of a 3x3 matrix.
	 * The matrix is given as:
	 * <pre>
	 *   | a11 a12 a13 |
	 *   | a21 a22 a23 |
	 *   | a31 a32 a33 |
	 * </pre>
	 * @param a11
	 * @param a12
	 * @param a13
	 * @param a21
	 * @param a22
	 * @param a23
	 * @param a31
	 * @param a32
	 * @param a33
	 * @return Determinant of the input 3x3 matrix.
	 */
	public static double determinant3(
			double a11, double a12, double a13,
			double a21,	double a22, double a23,
			double a31, double a32, double a33 ) {
		return a11*a22*a33 + a12*a23*a31 + a13*a21*a32
		     - a13*a22*a31 - a12*a21*a33 - a11*a23*a32;
	}
	
	/**
	 * Compares two {@link Point2D}s lexicographically.
	 */
	public static class Point2DComparator implements Comparator<Point2D> {
		@Override
		public int compare(Point2D o1, Point2D o2) {
			int xCmp = Double.compare(o1.getX(), o2.getX());
			if (xCmp == 0) return Double.compare(o1.getY(), o2.getY());
			return xCmp;
		}
	}

}
