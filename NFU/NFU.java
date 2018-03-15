package jnachos.kern.mem;

import jnachos.kern.JNachos;
import jnachos.kern.NachosProcess;
import jnachos.machine.Machine;
import jnachos.machine.TranslationEntry;


public class NFU implements PageReplacementAlgorithm {
	static int[] counters = new int[Machine.NumPhysPages];
	
	public NFU() {
		
	}
	@Override
	public int chooseVictimPage() {
		int min = counters[0];
		int indexOfMin = 0;
		for(int i = 0; i < Machine.NumPhysPages; i++) {
			if(counters[i] < min) {
				min = counters[i];
				indexOfMin = i;
			}
		}
		return indexOfMin;
	}
	
	public static void update() {
		for(int i = 0; i < Machine.NumPhysPages; i++) {
			if(translationEntryForPhysicalPage(i) != null){
				if(translationEntryForPhysicalPage(i).use) {
					counters[i]++;
					translationEntryForPhysicalPage(i).use = false;
				}
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
