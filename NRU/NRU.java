package jnachos.kern.mem;

import java.util.LinkedList;
import java.util.Random;
import jnachos.kern.JNachos;
import jnachos.kern.NachosProcess;
import jnachos.machine.Machine;
import jnachos.machine.TranslationEntry;


public class NRU implements PageReplacementAlgorithm {
	Random rand = new Random(System.currentTimeMillis());
	
	public NRU() {
		
	}
	@Override
	public int chooseVictimPage() {
		LinkedList<Integer> R0D0 = new LinkedList<Integer>();
		LinkedList<Integer> R0D1 = new LinkedList<Integer>();
		LinkedList<Integer> R1D0 = new LinkedList<Integer>();
		LinkedList<Integer> R1D1 = new LinkedList<Integer>();
		int pageFrame;
		
		for(int i = 0; i < Machine.NumPhysPages; i++) {
			TranslationEntry entry = translationEntryForPhysicalPage(i);
			
			if(!entry.use && !entry.dirty) {
				R0D0.add(i);
			}
			if(!entry.use && entry.dirty) {
				R0D1.add(i);
			}
			if(entry.use && !entry.dirty) {
				R1D0.add(i);
			}
			if(entry.use && entry.dirty) {
				R1D1.add(i);
			}
		}
		
		if(!R0D0.isEmpty()) {
			pageFrame = R0D0.get(rand.nextInt(R0D0.size()));
			return pageFrame;
		}
		if(!R0D1.isEmpty()) {
			pageFrame = R0D1.get(rand.nextInt(R0D1.size()));
			return pageFrame;
		}
		if(!R1D0.isEmpty()) {
			pageFrame = R1D0.get(rand.nextInt(R1D0.size()));
			return pageFrame;
		}
		if(!R1D1.isEmpty()) {
			pageFrame = R1D1.get(rand.nextInt(R1D1.size()));
			return pageFrame;
		}
		return -1;
	}
	
	public static void update() {
		for(int i = 0; i < Machine.NumPhysPages; i++) {
			TranslationEntry entry = translationEntryForPhysicalPage(i);
			if(entry != null) {
				entry.use = false;
			}
		}
	}
	
	public static TranslationEntry translationEntryForPhysicalPage(int physicalPage) {
	    int victimPid = JNachos.getPageFrameMap()[physicalPage];
	    if (victimPid < 0) {
	    	return null;
	    }
	    NachosProcess victim = NachosProcess.mProcessHash.get(victimPid);
	    if (victim == null) {
	    	return null;
	    }
	    TranslationEntry entry = null;
	    for (int i = 0; i < victim.getSpace().mPageTable.length; i++) {
	    	if (victim.getSpace().mPageTable[i].physicalPage == physicalPage) {
	    		entry = victim.getSpace().mPageTable[i];
	    		break;
	    	}
	    }
	    return entry;
	}
}