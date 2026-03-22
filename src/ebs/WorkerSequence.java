package ebs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WorkerSequence implements Runnable {
    private final int start, end;
    private final String outputFile;
    private final boolean[] hasCompany, hasValue, hasDrop, hasVariation, hasDate, companyOpEq;

    private static final String[] COMPANIES = {"Google", "Apple", "Microsoft", "Amazon", "Meta"};
    private static final String[] NON_EQ_OPERATORS = {">", "<", ">=", "<=", "!="};
    private static final String[] ALL_OPERATORS = {"=", ">", "<", ">=", "<=", "!="};
    
    public WorkerSequence(int start, int end, String outputFile, boolean[] hasCompany, boolean[] hasValue, 
                          boolean[] hasDrop, boolean[] hasVariation, boolean[] hasDate, boolean[] companyOpEq) {
        this.start = start;
        this.end = end;
        this.outputFile = outputFile;
        this.hasCompany = hasCompany;
        this.hasValue = hasValue;
        this.hasDrop = hasDrop;
        this.hasVariation = hasVariation;
        this.hasDate = hasDate;
        this.companyOpEq = companyOpEq;
    }
    
    @Override
    public void run() {
        Random rnd = new Random(start);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for(int i=start; i<end; i++) {
                StringBuilder sb = new StringBuilder("{");
                boolean first = true;
                
                if (hasCompany[i]) {
                    String op = companyOpEq[i] ? "=" : NON_EQ_OPERATORS[rnd.nextInt(NON_EQ_OPERATORS.length)];
                    String val = COMPANIES[rnd.nextInt(COMPANIES.length)];
                    sb.append("(company,").append(op).append(",\"").append(val).append("\")");
                    first = false;
                }
                
                if (hasValue[i]) {
                    if (!first) sb.append(";");
                    String op = ALL_OPERATORS[rnd.nextInt(ALL_OPERATORS.length)];
                    double val = Math.round(rnd.nextDouble() * 1000.0 * 100.0) / 100.0;
                    sb.append("(value,").append(op).append(",").append(val).append(")");
                    first = false;
                }
                
                if (hasDrop[i]) {
                    if (!first) sb.append(";");
                    String op = ALL_OPERATORS[rnd.nextInt(ALL_OPERATORS.length)];
                    double val = Math.round(rnd.nextDouble() * 100.0 * 100.0) / 100.0;
                    sb.append("(drop,").append(op).append(",").append(val).append(")");
                    first = false;
                }
                
                if (hasVariation[i]) {
                    if (!first) sb.append(";");
                    String op = ALL_OPERATORS[rnd.nextInt(ALL_OPERATORS.length)];
                    double val = Math.round(rnd.nextDouble() * 1.0 * 100.0) / 100.0;
                    sb.append("(variation,").append(op).append(",").append(val).append(")");
                    first = false;
                }
                
                if (hasDate[i]) {
                    if (!first) sb.append(";");
                    String op = ALL_OPERATORS[rnd.nextInt(ALL_OPERATORS.length)];
                    int day = 1 + rnd.nextInt(28);
                    int month = 1 + rnd.nextInt(12);
                    int year = 2020 + rnd.nextInt(5);
                    sb.append("(date,").append(op).append(",").append(day).append(".").append(month).append(".").append(year).append(")");
                }
                
                sb.append("}\n");
                bw.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
