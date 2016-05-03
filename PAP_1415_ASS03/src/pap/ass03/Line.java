package pap.ass03;

public class Line implements Shape {

	private P2d pt1;
    private P2d pt2;

    public Line(P2d pt1, P2d pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;
    }
    
    public P2d getFirstPoint() {
    	return pt1;
    }
    
    public P2d getSecondPoint() {
    	return pt2;
    }
    
	@Override
	public void move(V2d v) {
		pt1 = pt1.sum(v);
		pt2 = pt2.sum(v);
	}

	@Override
	public double getPerim() {
		return P2d.distance(pt1, pt2);
	}

	@Override
	public boolean isInside(BBox bbox) {
		BBox minBBox = getBBox();
		if(minBBox.getBottomRight().getX()<=bbox.getBottomRight().getX() 
				&& minBBox.getBottomRight().getY()>=bbox.getBottomRight().getY() 
				&& minBBox.getUpperLeft().getX()>=bbox.getUpperLeft().getX()
				&& minBBox.getUpperLeft().getY()<=bbox.getUpperLeft().getY())
			return true;
		else
			return false;
	}

	@Override
	public boolean contains(P2d p0) {
		/*int xmin = Math.min(pt1.getX(), pt2.getX());
		int xmax = Math.max(pt1.getX(), pt2.getX());
		int ymin = Math.min(pt1.getY(), pt2.getY());
		int ymax = Math.max(pt1.getY(), pt2.getY());
		if( ((pt2.getX()-pt1.getX())*(p0.getY()+pt1.getY())) == ((-pt2.getY()+pt1.getY())*(p0.getX()-pt1.getX()))//punto appartiene alla retta costruita sui due punti
				&& (p0.getX()>=xmin && p0.getX()<=xmax && p0.getY()>=ymin && p0.getY()<=ymax) )//punto è interno ai due punti
			return true;
		else
			return false;*/
		double distAC = P2d.distance(p0, pt1);
		double distBC = P2d.distance(p0, pt2);
		double distAB = P2d.distance(pt2, pt1);
		if(distAC + distBC == distAB)
			return true;
		else
			return false;
		
	}
	
	public String toString() {
		return "Line: "+pt1+","+pt2;
	}
	
	@Override
	public BBox getBBox(){
		int xmin = Math.min(pt1.getX(), pt2.getX());
		int xmax = Math.max(pt1.getX(), pt2.getX());
		int ymin = Math.min(pt1.getY(), pt2.getY());
		int ymax = Math.max(pt1.getY(), pt2.getY());
		P2d upperLeft = new P2d(xmin,ymin);
		P2d bottomRight = new P2d(xmax,ymax);
		BBox minBBox = new BBox(upperLeft,bottomRight);
		return minBBox;
	}

}
