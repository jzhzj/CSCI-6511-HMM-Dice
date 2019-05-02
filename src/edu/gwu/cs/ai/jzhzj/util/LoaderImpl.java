package edu.gwu.cs.ai.jzhzj.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qijiuzhi
 * @date 2019-05-01
 */
public class LoaderImpl implements Loader {
    private Data loadedData;
    private BufferedReader reader;

    public LoaderImpl(BufferedReader reader) {
        this.reader = reader;
        try {
            load();
        } catch (IOException e) {
            System.err.println("Can not resolve input file");
            e.printStackTrace();
            System.exit(0xDEADBEEF);
        }
    }

    private void load() throws IOException {
        String line;

        // #probability of not switching
        line = reader.readLine();

        // probability
        line = reader.readLine();

        double prOfNotSwitching = Double.parseDouble(line);
        // #Emission probabilities
        line = reader.readLine();

        // p11-p13
        double p[][] = new double[3][3];
        for (int i = 0; i < 3; i++) {
            line = reader.readLine();
            String[] nums = line.split(",");
            for (int j = 0; j < 3; j++) {
                p[i][j] = Double.parseDouble(nums[j]);
            }
        }

        // #Emissions
        line = reader.readLine();
        // emissions
        line = reader.readLine();
        line = line.substring(1, line.length() - 1);
        String[] emissionsStr = line.split(", ");
        List<Integer> emissions = new ArrayList<>();
        for (String s : emissionsStr) {
            emissions.add(Integer.valueOf(s));
        }

        loadedData = new DataImpl(prOfNotSwitching, p, emissions);
    }

    @Override
    public Data getLoadedData() {
        return loadedData;
    }
}
