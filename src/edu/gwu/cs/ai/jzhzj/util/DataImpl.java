package edu.gwu.cs.ai.jzhzj.util;

import java.util.List;

/**
 * @author qijiuzhi
 * @date 2019-05-01
 */
public class DataImpl implements Data {
    private double prOfSwitching;
    private double prOfNotSwitching;
    private double logPrOfNotSwitching;
    private double logPrOfSwitching;
    private double[][] p;
    private double[][] logP;
    private List<Integer> emissions;


    DataImpl(double prOfNotSwitching, double[][] p, List<Integer> emissions) {
        this.prOfSwitching = (1 - prOfNotSwitching) / 2;
        this.prOfNotSwitching = prOfNotSwitching;
        this.logPrOfNotSwitching = Math.log(this.prOfNotSwitching);
        this.logPrOfSwitching = Math.log((1 - this.prOfNotSwitching) / 2);
        this.p = p;
        this.emissions = emissions;
        logP = new double[p.length][p[0].length];
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                logP[i][j] = Math.log(p[i][j]);
            }
        }
    }


    @Override
    public double getPrOfSwitching(boolean isLog) {
        return isLog ? logPrOfSwitching : prOfSwitching;
    }

    @Override
    public double getPrOfNotSwitching(boolean isLog) {
        return isLog ? logPrOfNotSwitching : prOfNotSwitching;
    }

    @Override
    public double getPrOfNum(int dice, int num, boolean isLog) {
        return isLog ? logP[dice - 1][num - 1] : p[dice - 1][num - 1];
    }

    @Override
    public List<Integer> getEmissions() {
        return emissions;
    }
}
