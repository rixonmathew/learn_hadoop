package com.mockgenerator.generator;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.strategy.FileGenerationContext;
import com.mockgenerator.strategy.FileGenerationStrategy;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 1:17 PM
 * This class is responsible for creating the actual output files based on options
 */
public class FileGenerator {

    private final Options options;
    private final Schema schema;

    public FileGenerator(Options options, Schema schema) {
        this.options = options;
        this.schema = schema;
    }

    public void generateFiles() {
        FileGenerationStrategy strategy = FileGenerationContext.strategyForType(options.getGenerationType());
        strategy.generateFileData(schema,options);
    }
}
