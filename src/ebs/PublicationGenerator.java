package ebs;

import java.util.ArrayList;
import java.util.List;

public class PublicationGenerator {
    private final GeneratorConfig config;

    public PublicationGenerator(GeneratorConfig config) {
        this.config = config;
    }

    public void generate() throws Exception {
        int chunkSize = (int) Math.ceil((double) config.numMessages / config.numThreads);
        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < config.numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, config.numMessages);

            if (start >= end) break;

            Thread t = new Thread(new WorkerPublication(start, end, config.outputFilePrefix + "_" + i + ".txt"));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("[Publications]  Generated " + config.numMessages + " messages using " + config.numThreads + " thread(s) in " + (endTime - startTime) + " ms -> " + config.outputFilePrefix + "_*.txt");
    }
}
