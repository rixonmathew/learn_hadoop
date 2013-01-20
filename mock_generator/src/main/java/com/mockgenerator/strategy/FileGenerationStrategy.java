package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 1:58 PM
 * This interface is responsible for generating file as per a given strategy
 */
public interface FileGenerationStrategy {


    /**
     * This method is responsible for generating the data required for the files as per the startegy
     * @param schema the schema that represents the structure of the file
     * @param options options required for generating file
     */
    public void generateFileData(Schema schema,Options options);
}
