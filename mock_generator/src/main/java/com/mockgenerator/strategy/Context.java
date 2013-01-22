package com.mockgenerator.strategy;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 8:26 PM
 */
public class Context {

    private static final String RANDOM="random";
    private static final Logger LOG = Logger.getLogger(Context.class);

    private static final Map<String,FileGenerationStrategy> strategies;
    static {
        strategies = new HashMap<String, FileGenerationStrategy>();
        strategies.put(RANDOM,new RandomFileGenerationStrategy());

    }

    public static FileGenerationStrategy strategyForType(String fileGenerationType) {
        if (!strategies.containsKey(fileGenerationType)) {
            LOG.debug("Invalid file generation type specified :"+fileGenerationType);
        }
        return strategies.get(fileGenerationType);
    }
}
