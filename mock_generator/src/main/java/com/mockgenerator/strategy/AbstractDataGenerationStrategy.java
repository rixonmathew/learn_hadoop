package com.mockgenerator.strategy;

import com.mockgenerator.configuration.Schema;
import com.mockgenerator.generator.Options;
import com.mockgenerator.strategy.record.RecordCreationContext;
import com.mockgenerator.strategy.record.RecordCreationStrategy;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 7/3/13
 * Time: 7:06 PM
 * The base class for DataGenerationStrategy. This class is responsible for spawning multiple threads to generate data
 *
 */
public abstract class AbstractDataGenerationStrategy implements FileGenerationStrategy {

    protected final Logger LOG = Logger.getLogger(RandomFileGenerationStrategy.class);
    protected Schema schema;
    protected Options options;
    protected File outputDirectory;
    protected final Map<Long, File> filesForSplit;

    public AbstractDataGenerationStrategy(){
      filesForSplit = new HashMap<Long, File>();
    }

    @Override
    public void generateFileData(Schema schema, Options options) {
        this.schema = schema;
        this.options = options;
        try {
            prepareForDataGeneration();
            generateOutputDirectories();
            createFilesForSplits();
            populateDataUsingWorkers();
            cleanup();
        } catch (IOException e) {
            LOG.error("An error occurred while creating files for splits");
        }
    }

    /**
     * This method can be used by specific strategies to do any pre-processing
     * before data generation
     */
    protected abstract void prepareForDataGeneration();

    /**
     * This method can be used by specific strategies to do clean up
     * after data generation.
     */
    protected  void cleanup() {

    }

    protected void generateOutputDirectories() {
        if (options.getOutputDirectory() == null || options.getOutputDirectory().isEmpty()) {
            String name = "output_" + System.currentTimeMillis();
            outputDirectory = new File(name);
        } else {
            outputDirectory = new File(options.getOutputDirectory());
        }
        outputDirectory.mkdirs();

    }

    protected void createFilesForSplits() throws IOException {
        String fileName = schema.getName() + "-part";
        for (int split = 0; split < options.getNumberOfFileSplits(); split++) {
            String splitFileName = fileName + "-" + split;
            File outputFile = new File(outputDirectory.getAbsolutePath() + File.separator + splitFileName);
            outputFile.createNewFile();
            filesForSplit.put((long) split, outputFile);
        }
    }

    protected void populateDataUsingWorkers() {
        ProgressReporter progressReporter = new ProgressReporter();
        ExecutorService executorService = Executors.newFixedThreadPool(options.getNumberOfThreads());
        for (Long split : filesForSplit.keySet()) {
            Worker worker = new Worker(split,progressReporter);
            executorService.execute(worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                updateProgress(progressReporter.overallProgress());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        updateProgress(100.0d);
        System.out.println();
    }

    private void updateProgress(double progressPercentage) {
        final int width = 90; // progress bar width in chars

        System.out.print("\r[");
        int i = 0;
        for (; i <= (int)(progressPercentage*width*0.01f); i++) {
            System.out.print("=");
        }
        for (; i < width; i++) {
            System.out.print(" ");
        }
        System.out.print("] ==>"+progressPercentage+"%");
    }

    /**
     * This method will do the work required for populating the split. The AbstractDataGenerationStrategy takes
     * care of creating the thread pool for executing the tasks. Each of the worker thread will call this method
     * for creating data for splits. All subclasses needs to provide an implementation as per the variating they
     * expect
     * @param split the split for which data is to be generated
     */
    protected abstract void populateDataForSplit(long split);

    class ProgressReporter{
        private final Map<String,Float> taskProgress = new HashMap<String, Float>();

        synchronized void updateThreadProgress(String taskId,Float progress) {
            taskProgress.put(taskId,progress);
        }

        synchronized float overallProgress() {
            int total=0;
            int count=0;
            for(Float value:taskProgress.values()) {
                total+=value;
                count++;
            }

            float overallProgress=0;
            if(count>0)
                overallProgress=total/count;
            return overallProgress;
        }
    }

    class Worker implements Runnable {
        final Long split;
        final ProgressReporter progressReporter;
        final String taskId;

        Worker(Long split, ProgressReporter progressReporter) {
            this.progressReporter = progressReporter;
            this.split = split;
            this.taskId = "task:" + split;
            progressReporter.updateThreadProgress(taskId, 0.0f);
        }

        @Override
        public void run() {
            populateDataForSplit(split);
        }
    }
}
