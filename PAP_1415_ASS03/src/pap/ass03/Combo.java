package pap.ass03;

import java.util.List;

public class Combo implements Shape {

	List <Shape> combo;
	
    public Combo(List<Shape> combo) {
        this.combo = combo;
    }
	@Override
	public void move(V2d v) {
		for (Shape el : combo){
			el.move(v);
		}
	}

	@Override
	public double getPerim() {//considero il perimetro totale dato dalla somma dei perimetri delle figure della combo
		double totalPerim = 0;
		for (Shape el : combo){
			totalPerim = totalPerim + el.getPerim();
		}
		return totalPerim;
	}

	@Override
	public boolean isInside(BBox bbox) {
		for (Shape el : combo){
			if(!el.isInside(bbox)) return false;
		}
		return true;
	}

	@Override
	public boolean contains(P2d p0) {
		for (Shape el : combo){
			if(el.contains(p0)) return true;
		}
		return false;
	}
	
	public String toString() {
		String comb = "Combo List: {\n";
		for (Shape el : combo){
			comb += el.toString()+"\n";

		}
		return comb+"}\n";
    }
	
	@Override
	public BBox getBBox(){
		int xmin = Integer.MAX_VALUE;
		int xmax = 0;
		int ymin = Integer.MAX_VALUE;
		int ymax = 0;
		for (Shape el : combo){
			if(el.getBBox().getBottomRight().getX()>=xmax)
				xmax = el.getBBox().getBottomRight().getX();
			if(el.getBBox().getBottomRight().getY()<=ymin)
				ymin = el.getBBox().getBottomRight().getY();
			if(el.getBBox().getUpperLeft().getX()<=xmin)
				xmin = el.getBBox().getUpperLeft().getX();
			if(el.getBBox().getUpperLeft().getY()>=ymax)
				ymax = el.getBBox().getUpperLeft().getY();
		}
		P2d upperLeft = new P2d(xmin,ymin);
		P2d bottomRight = new P2d(xmax,ymax);
		BBox minBBox = new BBox(upperLeft,bottomRight);
		return minBBox;
	}
	
	public List<Shape> getList() {
		return combo;
	}
}
