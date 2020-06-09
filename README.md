# InvPendFuzzyController

1) Simulates and report behaviour of an Inverted Pendulum system under the control of four different fuzzy controllers.

Those are fuzzy controllers that have:

- Triangular membership functions.
- Gaussian membership functions.
- Gaussian membership functions that are generated through Standard Fuzzy Transfer using the dictionary.
- Gaussian membership functions that are generated through Normalized Fuzzy Transfer using the dictionary.

Libraries used:

- Juzzy, http://juzzy.wagnerweb.net/
- JMathPlot, http://code.google.com/p/jmathplot


2) Optimizes parameters (centers, sigmas) of fuzzy controllers that controls inverted pendulum using Differential Evolution algorithm.

Additional libraries used:

- JMetal, https://github.com/jMetal/jMetal
