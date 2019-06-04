package org.fuzzy.opt;

import org.fuzzy.opt.cont.FuzzyControllerOpt;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

public class FuzzyContOpt {

	private static final int DEFAULT_NUMBER_OF_CORES = 1;

	public static void main(String[] args) {
	    DoubleProblem problem;
	    Algorithm<DoubleSolution> algorithm;
	    DifferentialEvolutionSelection selection;
	    DifferentialEvolutionCrossover crossover;
	    SolutionListEvaluator<DoubleSolution> evaluator ;


//		FuzzyControllerOpt cont = new FuzzyControllerOpt();
//		cont.plotMembershipFunctions();
//		cont.plotControlSurface();
	}

}
