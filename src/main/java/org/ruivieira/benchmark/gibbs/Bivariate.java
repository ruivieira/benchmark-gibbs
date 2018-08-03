package org.ruivieira.benchmark.gibbs;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Bivariate {

    private void run(int iterations, int thin) {

        double x = 0.0;
        double y = 0.0;


        for (int i = 0; i < iterations; i++) {

            for (int j = 0 ; j < thin ; j ++) {
                x = new GammaDistribution(3.0, y*y+4.0).sample();
                y = new NormalDistribution(1.0/(x+1.0), 1.0 / Math.sqrt(2.0*x+2.0)).sample();            }

        }
    }

    public static void main(String[] args) {
        final Bivariate sampler = new Bivariate();
        int iterations = 50000;
        int thin = 100;
        if (args.length != 0) {
            iterations = Integer.parseInt(args[0]);
            thin = Integer.parseInt(args[1]);
        }
            System.out.println("NOT storing sample history.");
            sampler.run(iterations, thin);

    }

}
