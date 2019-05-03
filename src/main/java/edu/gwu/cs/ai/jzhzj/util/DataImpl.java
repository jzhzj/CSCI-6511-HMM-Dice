package edu.gwu.cs.ai.jzhzj.util;

import java.util.List;

/**
 * @author qijiuzhi
 * @date 2019-05-01
 */
public class DataImpl implements Data {
    private double prOfSwitching;
    private double prOfNotSwitching;
    private double[][] p;
    private List<Integer> emissions;


    DataImpl(double prOfNotSwitching, double[][] p, List<Integer> emissions) {
        this.prOfSwitching = (1 - prOfNotSwitching) / 2;
        this.prOfNotSwitching = prOfNotSwitching;
        this.p = p;
        this.emissions = emissions;
    }


    @Override
    public double getPrOfSwitching() {
        return prOfSwitching;
    }

    @Override
    public double getPrOfNotSwitching() {
        return prOfNotSwitching;
    }

    @Override
    public double getPrOfNum(int dice, int num) {
        return p[dice - 1][num - 1];
    }

    @Override
    public List<Integer> getEmissions() {
        return emissions;
    }
}
