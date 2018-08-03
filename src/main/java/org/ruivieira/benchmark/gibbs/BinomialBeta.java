package org.ruivieira.benchmark.gibbs;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;

public class BinomialBeta {

    // Priors
    private final double[] betaPriors = {2.0, 4.0};
    private final int trials = 16;


    private void run(int iterations, int thin) {

        double previous_x = 1.0;
        double previous_theta = 0.5;

        for (int i = 0; i < iterations; i++) {

            for (int j = 0 ; j < thin ; j ++) {
                previous_x = new BinomialDistribution(trials, previous_theta).sample();
                previous_theta = new BetaDistribution(betaPriors[0] + previous_x, betaPriors[1] + trials - previous_x).sample();
            }

        }
    }

    private void runStore(int iterations, int thin) {

        double x[] = new double[iterations];
        double theta[] =new double[iterations];

        x[0] = 1.0;
        theta[0] = 0.5;

        for (int i = 1; i < iterations; i++) {

            for (int j = 0 ; j < thin ; j ++) {
                x[i] = new BinomialDistribution(trials, theta[i-1]).sample();
                theta[i] = new BetaDistribution(betaPriors[0] + x[i-1], betaPriors[1] + trials - x[i-1]).sample();
            }

        }
    }

    public static void main(String[] args) {
        final BinomialBeta sampler = new BinomialBeta();
        int iterations = 50000;
        int thin = 100;
        boolean store = false;
        if (args.length != 0) {
            store = args[0].equals("store");
            iterations = Integer.parseInt(args[1]);
            thin = Integer.parseInt(args[2]);
        }
        if (store) {
            System.out.println("Storing sample history.");
            sampler.runStore(iterations, thin);
        } else {
            System.out.println("NOT storing sample history.");
            sampler.run(iterations, thin);
        }

    }

}
