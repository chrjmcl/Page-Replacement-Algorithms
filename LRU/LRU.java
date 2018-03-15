package jnachos.kern.mem;

import java.util.LinkedList;


public class LRU implements PageReplacementAlgorithm {
	public static LinkedList<Integer> queue = new LinkedList<Integer>();
	
	public LRU() {
		
	}
	@Override
	public int chooseVictimPage() {
		int pageFrame;
		
		if(!queue.isEmpty()) {
			pageFrame = queue.removeLast();
			queue.addFirst(pageFrame);
			return pageFrame;
		}
		return -1;
	}
}
