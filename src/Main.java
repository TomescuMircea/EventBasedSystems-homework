import ebs.GeneratorConfig;
import ebs.PublicationGenerator;
import ebs.SubscriptionGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        int numMessages = 1_000_000;

        System.out.println("=========================================");
        System.out.println("Running SEQUENTIAL generation (1 thread)");
        System.out.println("=========================================");
        GeneratorConfig seqConfig = new GeneratorConfig(
            numMessages, 1,
            0.9, // 90% company
            0.5, // 50% value
            0.5, // 50% drop
            0.4, // 40% variation
            0.3, // 30% date
            0.7, // 70% company equality operator
            "sub_seq"
        );
        new SubscriptionGenerator(seqConfig).generate();
        new PublicationGenerator(new GeneratorConfig(
            numMessages, 1,
            0.9, // 90% company
            0.5, // 50% value
            0.5, // 50% drop
            0.4, // 40% variation
            0.3, // 30% date
            0.7, // 70% company equality operator
            "pub_seq"
        )).generate();

        System.out.println("\n=========================================");
        System.out.println("Running PARALLEL generation (4 threads)");
        System.out.println("=========================================");
        GeneratorConfig parConfig = new GeneratorConfig(
            numMessages, 4,
            0.9, // 90% company
            0.5, // 50% value
            0.5, // 50% drop
            0.4, // 40% variation
            0.3, // 30% date
            0.7, // 70% company equality operator
            "sub_par"
        );
        new SubscriptionGenerator(parConfig).generate();
        new PublicationGenerator(new GeneratorConfig(
            numMessages, 4,
            0.9, // 90% company
            0.5, // 50% value
            0.5, // 50% drop
            0.4, // 40% variation
            0.3, // 30% date
            0.7, // 70% company equality operator
            "pub_par"
        )).generate();
    }
}
