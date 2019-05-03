package edu.gwu.cs.ai.jzhzj.util;

import java.util.List;

/**
 * @author qijiuzhi
 * @date 2019-05-01
 */
public interface Data {

    /**
     * get the log of the probability of switching
     *
     * @return log of the probability
     */
    double getPrOfSwitching();

    /**
     * get the probability of not switching
     *
     * @return the probability
     */
    double getPrOfNotSwitching();


    /**
     * get the probability of a certain rolling number with a certain dice
     *
     * @param dice dice
     * @param num  num
     * @return the probability
     */
    double getPrOfNum(int dice, int num);

    /**
     * get the emissions
     *
     * @return the emissions
     */
    List<Integer> getEmissions();
}
