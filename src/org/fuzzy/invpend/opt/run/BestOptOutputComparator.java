package org.fuzzy.invpend.opt.run;

import java.util.Comparator;

public class BestOptOutputComparator implements Comparator<BestOptOutput> {
	@Override
	public int compare(BestOptOutput o1, BestOptOutput o2) {
		if (o1.getObjCoefRmseT() > o2.getObjCoefRmseT())
			return 1;
		else
			if (o1.getObjCoefRmseT() < o2.getObjCoefRmseT())
				return -1;
			else
				if (o1.getObjCoefDissim() > o2.getObjCoefDissim())
					return 1;
				else
					if (o1.getObjCoefDissim() < o2.getObjCoefDissim())
						return -1;
					else
						return 0;
	}
}
