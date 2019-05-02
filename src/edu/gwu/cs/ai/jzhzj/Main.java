package edu.gwu.cs.ai.jzhzj;

import edu.gwu.cs.ai.jzhzj.util.Data;
import edu.gwu.cs.ai.jzhzj.util.LoaderImpl;
import edu.gwu.cs.ai.jzhzj.util.Loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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

        List<Integer> result = handle(data, false);
        List<Integer> resultByLog = handle(data, true);

        showResult(result);
        showResult(resultByLog);
    }

    private static void showResult(List<Integer> res) {
        System.out.println(res.toString());
    }

    private static List<Integer> handle(Data data, boolean isLog) {
        double[] prLastLayer = null;
        int[][] fromWhere = new int[data.getEmissions().size()][3];
        int current = 0;
        int largestLastLayer = 0;
        final double initPr = isLog ? Math.log(1.0 / 3) : (1.0 / 3);
        for (Integer emission : data.getEmissions()) {
            if (prLastLayer == null) {
                prLastLayer = new double[3];
                for (int i = 0; i < 3; i++) {
                    if (isLog) {
                        prLastLayer[i] = initPr + data.getPrOfNum(i + 1, emission, true);
                    } else {
                        prLastLayer[i] = initPr * data.getPrOfNum(i + 1, emission, false);
                    }
                }
            } else {
                double[][] newProbability = new double[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        double pTransfer = i == j ?
                                data.getPrOfNotSwitching(isLog)
                                : data.getPrOfSwitching(isLog);
                        if (isLog) {
                            newProbability[i][j] = prLastLayer[i] + pTransfer + data.getPrOfNum(j + 1, emission, isLog);
                        } else {
                            newProbability[i][j] = prLastLayer[i] * pTransfer * data.getPrOfNum(j + 1, emission, isLog);
                        }
                    }
                }
                double globalMax = isLog ? -Double.MAX_VALUE : 0;
                for (int i = 0; i < 3; i++) {
                    double max = isLog ? -Double.MAX_VALUE : 0;
                    int maxId = 0;
                    for (int j = 0; j < 3; j++) {
                        if (newProbability[i][j] > max) {
                            max = newProbability[i][j];
                            maxId = j;
                        }
                    }
                    prLastLayer[i] = max;
                    fromWhere[current][i] = maxId;
                    if (globalMax < max) {
                        globalMax = max;
                        largestLastLayer = i;
                    }
                }
            }
            current += 1;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < data.getEmissions().size(); ++i) {
            result.add(largestLastLayer + 1);
            largestLastLayer = fromWhere[data.getEmissions().size() - 1 - i][largestLastLayer];
        }
        Collections.reverse(result);
        return result;
    }

    private static void showUsage() {
        System.err.println("Usage:");
        System.err.println("java -jar HMM_Dice.jar <input file>");
        System.exit(1);
    }
}
