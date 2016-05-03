package pap.ass05.minDistance;

public class P3d implements java.io.Serializable {

	    private long x,y,z;

	    public P3d(long x,long y,long z){
	        this.x = x;
	        this.y = y;
	        this.z = z;
	        
	    }

	    public long getX(){
	    	return x;
	    }
	    
	    public long distanceFrom(P3d q){
	    	double distance = Math.sqrt(
	    			Math.pow((x-q.getX()),2)
	    			+Math.pow((y-q.getY()),2)
	    			+Math.pow((z-q.getZ()),2)
	    			);
	    	return (long) distance;
	    }
	    
	    public long getY(){
	    	return y;
	    }
	    
	    public long getZ(){
	    	return z;
	    }
	    public String toString(){
	        return "P3d("+x+","+y+","+z+")";
	    }

}

