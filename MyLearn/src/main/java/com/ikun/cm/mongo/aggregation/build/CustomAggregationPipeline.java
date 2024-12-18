package com.ikun.cm.mongo.aggregation.build;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/11/15 00:27
 * @description: It's DANGEROUS! Because we allow all kinds of Aggregation operations from bson-like string
 */
@Slf4j
public class CustomAggregationPipeline {

    private final String pipeline = "pipeline";
    private final List<AggregationOperation> operations;


    /**
     * Constructor that initializes the aggregation pipeline based on the provided operation document.
     *
     * @param operation a BSON-like document representing the aggregation pipeline or a single stage.
     *                  If it contains a "pipeline" field, it is treated as a multi-stage pipeline.
     *                  Otherwise, it is treated as a single aggregation stage.
     */
    public CustomAggregationPipeline(Document operation) {
        this.operations = new ArrayList<>();

        try {
            if (operation.containsKey(pipeline)) {
                List<Document> pipelineStages = (List<Document>) operation.get("pipeline");
                for (Document stage : pipelineStages) {
                    // Print each pipeline stage to verify its structure
                    log.info("Pipeline Stage Document: " + stage.toJson());

                    // Convert each pipeline stage into an AggregationOperation
                    operations.add(context -> stage);
                }
            } else {
                // If the document does not contain a "pipeline" field,
                // treat the entire document as a single aggregation stage.
                operations.add(context -> operation);
            }
        }catch (Exception e){
            log.info("failed to convert operations into Aggregation:"+operation.toJson());
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the list of aggregation operations parsed from the input document.
     *
     * @return a list of AggregationOperation objects representing the pipeline stages.
     */
    public List<AggregationOperation> getOperations() {
        return operations;
    }
}
