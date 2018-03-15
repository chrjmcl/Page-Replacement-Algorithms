package jnachos.kern.mem;

import java.util.Random;
import jnachos.machine.Machine;


public class RandomPageFrame implements PageReplacementAlgorithm {
	Random rand = new Random(System.currentTimeMillis());
	
	public RandomPageFrame() {
		
	}
	public int chooseVictimPage() {
		return (rand.nextInt(Machine.NumPhysPages));
	}
}