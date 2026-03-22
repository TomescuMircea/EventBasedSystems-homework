package ebs;

public class GeneratorConfig {
    public final int numMessages;
    public final int numThreads;
    
    public final double freqCompany;
    public final double freqValue;
    public final double freqDrop;
    public final double freqVariation;
    public final double freqDate;

    public final double companyEqFreq;
    
    public final String outputFilePrefix;
    
    public GeneratorConfig(int numMessages, int numThreads, double freqCompany, double freqValue, 
                           double freqDrop, double freqVariation, double freqDate, 
                           double companyEqFreq, String outputFilePrefix) {
        this.numMessages = numMessages;
        this.numThreads = numThreads;
        this.freqCompany = freqCompany;
        this.freqValue = freqValue;
        this.freqDrop = freqDrop;
        this.freqVariation = freqVariation;
        this.freqDate = freqDate;
        this.companyEqFreq = companyEqFreq;
        this.outputFilePrefix = outputFilePrefix;
    }
}
