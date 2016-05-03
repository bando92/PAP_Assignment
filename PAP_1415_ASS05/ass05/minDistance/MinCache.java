package pap.ass05.minDistance;

public class MinCache {

	private long minDistance;
    private P3d minPoint;
	
	public MinCache(long distance, P3d point){
		this.minDistance = distance;
	}
	
	public void setMinDistance(long distance){
		synchronized (this) {
			this.minDistance = distance;
		}
	}
	
	public long getMinDistance(){
		synchronized (this) {
			return minDistance;
		}
	}
	
	public void setMinPoint(P3d point){
		synchronized (this) {
			this.minPoint = point;
		}
	}

	public P3d getMinPoint(){
		return minPoint;
	}

}
