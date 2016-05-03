package pap.ass03;

/**
 * Interfaccia che rappresenta una figura
 * in una viewport grafica (0,0)-(w,h)
 * 
 *
 */
public interface Shape {
	void move(V2d v);	
	double getPerim();
	boolean isInside(BBox bbox);
	boolean contains(P2d p0);
	BBox getBBox();
}
