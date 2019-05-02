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
     * @param isLog indicating whether getting log of the probability
     * @return log of the probability
     */
    double getPrOfSwitching(boolean isLog);

    /**
     * get the probability of not switching
     *
     * @param isLog indicating whether getting log of the probability
     * @return the probability
     */
    double getPrOfNotSwitching(boolean isLog);


    /**
     * get the probability of a certain rolling number with a certain dice
     *
     * @param dice  dice
     * @param num   num
     * @param isLog indicating whether getting log of the probability
     * @return the probability
     */
    double getPrOfNum(int dice, int num, boolean isLog);

    /**
     * get the emissions
     *
     * @return the emissions
     */
    List<Integer> getEmissions();
}
