import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 7/3/13
 * Time: 5:35 PM
 * Rather than using mock generator all files are being generates by test class as we need a structure
 * to the data and a proper series.
 */
public class MockQuotesGenerator {

    private static Map<String,List<PriceSet>> masterData;
    private final static String START_TIME = "093000000";
    private final static String END_TIME = "170000000";
    private long incrementTimeMilliSeconds = 100;


    //172338831SMSFT            000059165560013260000053862140018247J    NI000000000161924136CNAD A0
    private static String quoteTemplateAsPerTAQ = "#timestamp#A#symbol##bidPrice##bidSize##askPrice##askSize##quoteCondition##bidExchange##askExchange#";

    public static void generateMockQuotes(){

        long startTime = Long.valueOf(START_TIME);
        List<String> quoteData = new ArrayList<String>();
        long endTime = Long.valueOf(END_TIME);
        while (startTime<=endTime) {
            for (String quote:masterData.keySet()) {
                for(PriceSet priceSet:masterData.get(quote)) {
                    quoteData.add(generateQuoteRecord(quote,priceSet));
                }
            }
        }
        persistQuotes(quoteData);
    }

    class PriceSet {
        String askPrice;
        String askQuantity;
        String bidPrice;
        String bidQuantity;
    }

}
