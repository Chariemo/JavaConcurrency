package basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class PublishingVehicleTrackerDemo {

	private final Map<String, SafePoint> locations;
	private final Map<String, SafePoint> unmodifiableMap;

	public PublishingVehicleTrackerDemo(Map<String, SafePoint> locations) {

		this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
		this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
	}

	public Map<String, SafePoint> getLocations() {

		return unmodifiableMap;
	}

	public SafePoint getLocation(String id) {

		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {

		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException("invalid vehicle name: " + id);
		}
		locations.get(id).set(x, y);
	}

	public static void main(String[] args) {
		
		
	}
}

@ThreadSafe
class SafePoint {

	@GuardedBy("this")
	private int x, y;

	private SafePoint(int[] a) {

		this(a[0], a[1]);
	}

	public SafePoint(SafePoint point) {

		this(point.get());
	}

	public SafePoint(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public synchronized int[] get() {

		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {

		this.x = x;
		this.y = y;
	}
}