package ebs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WorkerPublication implements Runnable {
    private final int start, end;
    private final String outputFile;

    private static final String[] COMPANIES = {"Google", "Apple", "Microsoft", "Amazon", "Meta"};

    public WorkerPublication(int start, int end, String outputFile) {
        this.start = start;
        this.end = end;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        Random rnd = new Random(start);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = start; i < end; i++) {
                String company = COMPANIES[rnd.nextInt(COMPANIES.length)];
                double value = Math.round(rnd.nextDouble() * 1000.0 * 100.0) / 100.0;
                double drop = Math.round(rnd.nextDouble() * 100.0 * 100.0) / 100.0;
                double variation = Math.round(rnd.nextDouble() * 1.0 * 100.0) / 100.0;
                int day = 1 + rnd.nextInt(28);
                int month = 1 + rnd.nextInt(12);
                int year = 2020 + rnd.nextInt(5);

                bw.write("{(company,\"" + company + "\");(value," + value + ");(drop," + drop + ");(variation," + variation + ");(date," + day + "." + month + "." + year + ")}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
