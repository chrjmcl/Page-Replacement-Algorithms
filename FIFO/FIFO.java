package jnachos.kern.mem;

import java.util.LinkedList;


public class FIFO implements PageReplacementAlgorithm {
	public static LinkedList<Integer> queue = new LinkedList<Integer>();
	
	public FIFO() {
		
	}
	@Override
	public int chooseVictimPage() {
		int pageFrame;

		if(!queue.isEmpty()) {
			pageFrame = queue.removeFirst();
			queue.addLast(pageFrame);
			return pageFrame;
		}
		return -1;
	}
}