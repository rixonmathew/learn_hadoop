package com.mockgenerator.generator;

/**
 * User: rixonmathew
 * Date: 20/01/13
 * Time: 1:10 PM
 * This class represents the input options for generating mock files
 */
public class Options {
    private static final String DEFAULT_GENERATION_TYPE = "random";
    private static final String OUTPUT_DIR = "output_" + System.currentTimeMillis();
    private String generationType;
    private long numberOfFileSplits;
    private long numberOfRecordsPerSplit;
    private String outputDirectory;
    private boolean compressOutput;

    public Options() {
        this.generationType = DEFAULT_GENERATION_TYPE;
        this.numberOfFileSplits = 1;
        this.numberOfRecordsPerSplit = 10000;
        this.outputDirectory = OUTPUT_DIR;
        this.compressOutput = false;
    }

    public String getGenerationType() {
        return generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

    public long getNumberOfFileSplits() {
        return numberOfFileSplits;
    }

    public void setNumberOfFileSplits(long numberOfFileSplits) {
        this.numberOfFileSplits = numberOfFileSplits;
    }

    public long getNumberOfRecordsPerSplit() {
        return numberOfRecordsPerSplit;
    }

    public void setNumberOfRecordsPerSplit(long numberOfRecordsPerSplit) {
        this.numberOfRecordsPerSplit = numberOfRecordsPerSplit;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public boolean isCompressOutput() {
        return compressOutput;
    }

    public void setCompressOutput(boolean compressOutput) {
        this.compressOutput = compressOutput;
    }
}
