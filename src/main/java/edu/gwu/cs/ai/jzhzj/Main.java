package edu.gwu.cs.ai.jzhzj;

import edu.gwu.cs.ai.jzhzj.util.Data;
import edu.gwu.cs.ai.jzhzj.util.Loader;
import edu.gwu.cs.ai.jzhzj.util.LoaderImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qijiuzhi
 * @date 2019-05-01
 */


public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            showUsage();
        }

        Loader loader;
        Data data = null;

        try {
            loader = new LoaderImpl(new BufferedReader(new FileReader(new File(args[0]))));
            data = loader.getLoadedData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int[] obs = new int[data.getEmissions().size()];
        for (int i = 0; i < data.getEmissions().size(); i++) {
            obs[i] = data.getEmissions().get(i);
        }
        int[] states = {0, 1, 2};
        double[] start_p = {1.0 / 3, 1.0 / 3, 1.0 / 3};
        double[][] trans_p = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    trans_p[i][j] = data.getPrOfNotSwitching();
                } else {
                    trans_p[i][j] = data.getPrOfSwitching();
                }
            }
        }
        double[][] emis_p = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                emis_p[i][j] = data.getPrOfNum(i + 1, j + 1);
            }
        }

        viterbiDp(obs, states, start_p, trans_p, emis_p);
    }

    private static void viterbiDp(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emis_p) {
        // record the highest probability to state i with observation j
        double[][] maxProb = new double[states.length][obs.length];
        // record state j-1 in the path
        int[][] path = new int[states.length][obs.length];

        // initialize
        for (int state : states) {
            maxProb[state][0] = start_p[state] * emis_p[state][obs[0] - 1];
            path[state][0] = state;
        }

        //observation sequence
        for (int j = 1; j < obs.length; j++) {
            // temp array to record max path
            int[][] temp = new int[states.length][obs.length];

            //states sequence
            for (int i : states) {
                double prob = -1;
                int curState;
                for (int y0 : states) {
                    double newProb = maxProb[y0][j - 1] * trans_p[y0][i] * emis_p[i][obs[j] - 1];
                    if (newProb > prob) {
                        prob = newProb;
                        curState = y0;
                        // record the max prob
                        maxProb[i][j] = prob;
                        // record path
                        System.arraycopy(path[curState], 0, temp[i], 0, j);
                        temp[i][j] = i;
                    }
                }
            }
            path = temp;
        }

        double prob = -1;
        int state = 0;
        for (int i : states) {
            if (maxProb[i][obs.length - 1] > prob) {
                prob = maxProb[i][obs.length - 1];
                state = i;
            }
        }


        List<Integer> result = new ArrayList<>();
        for (int i : path[state]) {
            result.add(i + 1);
        }
        System.out.println("Most possible dices sequence:");
        System.out.println(result);
        System.out.println("Probability of path: " + prob);
    }


    private static void showUsage() {
        System.err.println("Usage:");
        System.err.println("java -jar HMM_Dice.jar <input file>");
        System.exit(1);
    }
}
