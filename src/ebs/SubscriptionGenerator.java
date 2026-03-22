package ebs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SubscriptionGenerator {
    private final GeneratorConfig config;
    
    public SubscriptionGenerator(GeneratorConfig config) {
        this.config = config;
    }
    
    public void generate() throws Exception {
        boolean[] hasCompany = exactDistribution(config.numMessages, config.freqCompany);
        boolean[] hasValue = exactDistribution(config.numMessages, config.freqValue);
        boolean[] hasDrop = exactDistribution(config.numMessages, config.freqDrop);
        boolean[] hasVariation = exactDistribution(config.numMessages, config.freqVariation);
        boolean[] hasDate = exactDistribution(config.numMessages, config.freqDate);
        
        // Exact operators for company. If hasCompany[i] is true, companyOpEq[i] will be true for exactly eqFreq percentage of the trues.
        boolean[] companyOpEq = exactSubDistribution(hasCompany, config.companyEqFreq);
        
        int chunkSize = (int) Math.ceil((double)config.numMessages / config.numThreads);
        List<Thread> threads = new ArrayList<>();
        
        long startTime = System.currentTimeMillis();
        
        for(int i=0; i<config.numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, config.numMessages);

            if (start >= end)
                break;
            
            Thread t = new Thread(new WorkerSequence(
                start, end, config.outputFilePrefix + "_" + i + ".txt",
                hasCompany, hasValue, hasDrop, hasVariation, hasDate, companyOpEq
            ));
            threads.add(t);
            t.start();
        }
        
        for(Thread t : threads) {
            t.join();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Generated " + config.numMessages + " messages using " + config.numThreads + " thread(s) in " + (endTime - startTime) + " ms");
    }
    
    private boolean[] exactDistribution(int total, double percentage) {
        boolean[] arr = new boolean[total];
        int count = (int) Math.round(total * percentage);

        for(int i=0; i<count; i++)
            arr[i] = true;

        Random rnd = new Random();
        for(int i = total - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            boolean temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }
    
    private boolean[] exactSubDistribution(boolean[] parent, double parentPercentage) {
        int totalParent = 0;

        for(boolean b : parent)
            if(b)
                totalParent++;
        
        int targetEq = (int) Math.round(totalParent * parentPercentage);
        
        int[] parentIndices = new int[totalParent];

        int idx = 0;
        for(int i=0; i<parent.length; i++) {
            if(parent[i]) {
                parentIndices[idx++] = i;
            }
        }
        
        Random rnd = new Random();

        for(int i = totalParent - 1; i > 0; i--) {
            int r = rnd.nextInt(i + 1);
            int temp = parentIndices[r];
            parentIndices[r] = parentIndices[i];
            parentIndices[i] = temp;
        }
        
        boolean[] eqOps = new boolean[parent.length];
        for(int i=0; i<targetEq; i++) {
            eqOps[parentIndices[i]] = true;
        }
        return eqOps;
    }
}
