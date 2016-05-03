package pap.ass03;

public class Circle implements Shape {

	private P2d center;
    private int radius;

    public Circle(P2d center, int radius) {
        this.center = center;
        this.radius = radius;
    }
	
	@Override
	public void move(V2d v) {
		center = center.sum(v);
	}

	@Override
	public double getPerim() {
		return 2*radius*Math.PI;
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
		if((double) radius >= P2d.distance(p0, center)) return true;
		return false;
	}
	
	public String toString(){
        return "Circle: Center "+center+" Radius "+radius;
    }
	
	@Override
	public BBox getBBox(){
		P2d upperLeft = new P2d(center.getX()-radius,center.getY()-radius);
		P2d bottomRight = new P2d(center.getX()+radius,center.getY()+radius);
		BBox minBBox = new BBox(upperLeft,bottomRight);
		return minBBox;
	}
	
	public P2d getCenter(){
		return center;
	}
	
	public int getRadius(){
		return radius;
	}
}
