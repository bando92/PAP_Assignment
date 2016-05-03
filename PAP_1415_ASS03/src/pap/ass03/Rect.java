package pap.ass03;

public class Rect implements Shape {

	private P2d pt1;
    private P2d pt2;

    public Rect(P2d pt1, P2d pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;
    }
	
	@Override
	public void move(V2d v) {
		pt1 = pt1.sum(v);
		pt2 = pt2.sum(v);
	}

	@Override
	public double getPerim() {
		return 2*Math.abs(pt1.getX()-pt2.getX()) + 2*Math.abs(pt1.getY()-pt2.getY());
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
		int xmin = Math.min(pt2.getX(), pt1.getX());
		int xmax = Math.max(pt2.getX(), pt1.getX());
		int ymin = Math.min(pt2.getY(), pt1.getY());
		int ymax = Math.max(pt2.getY(), pt1.getY());
		if(p0.getX()>=xmin && p0.getX()<=xmax &&
				p0.getY()>=ymin && p0.getY()<=ymax)
			return true;
		else
			return false;
	}

	public String toString() {
		return "Rect: "+pt1+","+pt2; 
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

	public P2d getupperLeft() {
		int xmin = Math.min(pt1.getX(), pt2.getX());
		int ymin = Math.min(pt1.getY(), pt2.getY());
		P2d upperLeft = new P2d(xmin,ymin);
		return upperLeft;
    }
    
    public int getHeight() {
    	return Math.abs(pt1.getY()-pt2.getY());
    }
    
    public int getWidth() {
    	return Math.abs(pt1.getX()-pt2.getX());
    }
}
