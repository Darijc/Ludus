package bxt.convexhull.processing;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import bxt.unilectures.algogeo.fun.convexhull.ConvexHullBuilder;
import bxt.unilectures.algogeo.fun.convexhull.FirstConvexHullBuilder;
import bxt.unilectures.algogeo.fun.convexhull.GiftWrapppingConvexHullBuilder;
import bxt.unilectures.algogeo.fun.convexhull.StableConvexHullBuilder;
import bxt.util.GeomDrawer;

public class ConvexHullSketch extends PApplet {
	
	private GeomDrawer d = new GeomDrawer(this);
	
	private List<Point2D> points = new LinkedList<Point2D>();
	
	private ConvexHullBuilder[] cbhs = new ConvexHullBuilder[]{
			new GiftWrapppingConvexHullBuilder(),
			new FirstConvexHullBuilder(),
			new StableConvexHullBuilder(),
	};
	
	/**
	 * Main-method for direct invocation, dispatches to 
	 * {@link PApplet#main(String[])}. 
	 * @param args
	 */
	public static void main(String args[]) {
		PApplet.main(new String[]{ConvexHullSketch.class.getCanonicalName()});
	}
	
	public void settings() {
	  size(700, 400);
	}
	
	public void setup() {
		randomPoints(80000);
	}
	
	public void draw() {
		background(20);
		
		stroke(color(0,0,255));
		List<Point2D> complexHull = cbhs[2].build(points);
		for (int i = 1; i < complexHull.size(); i++) {
			d.draw(complexHull.get(i-1), complexHull.get(i));
		}
		if(complexHull.size() >= 2)
			d.draw(complexHull.get(complexHull.size()-1), complexHull.get(0));
		
		stroke(255);
		for(Point2D point : points) {
			d.draw(point);
		}
		
	}
	
	public void mouseClicked() {
		if(mouseButton == LEFT) {
			points.add(new Point2D.Double(mouseX, mouseY));
		} else {
			points.clear();
		}
		//System.out.println(points);
	}
	
	private void randomPoints(int count) {
		for (int i = 0; i < count; i++) {
			points.add(new Point2D.Double(Math.random()*width, Math.random()*height));
		}
	}
}
