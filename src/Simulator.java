import org.diffeq.InvertedPendulum;
import org.diffeq.State;

public class Simulator {

	public static void main(String[] args) {

		double mp = 0.1;	//	kg
		double mc = 1.0;	//	kg
		double l = 2.0;		//	meter
		double g = 9.8;		//	meter/sn2

		State s = new State(0.0, 0.0, 0.0, 0.0);

		InvertedPendulum p = new InvertedPendulum(mp, mc, l, g, s);

		double step = 0.1;	//	sn
		double duration = 10.0;
		double t = 0.0;

		double f = 1.0;	//	newton

		while (t < duration) {
			p.move(step, f);
			System.out.println(p);
			t += step;
			f = 0.0;
		}
	}

}
