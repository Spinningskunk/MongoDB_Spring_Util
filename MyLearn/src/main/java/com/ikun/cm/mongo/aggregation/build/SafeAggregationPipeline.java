package com.ikun.cm.mongo.aggregation.build;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: HeKun
 * @date: 2024/11/15 00:43
 * @description: We can use it to build a safe Aggregation by bson-like string
 *
 * Here is my Note:
 * -The use of operators such as $set、$out、$merge、$update or any other similar ones is restricted.
 * because those they can potentially modify or store data in a way that is unsafe.
 * -This tool is just convert find-query from bson-like string to spring-mongo Aggregation to help us use flexible function.
 * -So we do not intend for it to modify our target collection or any other collections.
 * -$set and $update can modify existing documents, which could lead to unintentional data changes.
 * -$out and $merge can overwrite data or store results in different collections, which can
 *  lead to unauthorized data manipulation or loss.
 *
 */
@Slf4j
public class SafeAggregationPipeline {

    private final String pipeline = "pipeline";
    private final List<AggregationOperation> operations;

    private static final Set<String> UNSAFE_OPERATORS;

    /**
     * The name of the collection to which the aggregation pipeline will be applied.
     */
    private String collectionName;

    static {
        Set<String> unsafeOperators = new HashSet<>();
        unsafeOperators.add("$set");
        unsafeOperators.add("$out");
        unsafeOperators.add("$merge");
        unsafeOperators.add("$update");
        UNSAFE_OPERATORS = Collections.unmodifiableSet(unsafeOperators);
    }

    /**
     * Constructor that initializes the aggregation pipeline based on the provided operation document,
     * ensuring safety by validating the operations.
     *
     * @param operation A BSON-like document representing the aggregation pipeline or a single stage.
     */
    public SafeAggregationPipeline(Document operation) {
        this.operations = new ArrayList<>();
        try {
            if (operation.containsKey("aggregate")) {
                // Get the collection name from the aggregate operation
                collectionName = operation.getString("aggregate");
                log.debug("Collection name retrieved: {}", collectionName);
            }
            if (operation.containsKey(pipeline)) {
                Object pipelineStages = operation.get(pipeline);
                if (pipelineStages instanceof List) {
                    List<Document> stageList = (List<Document>) pipelineStages;
                    for (Document stage : stageList) {
                        validateOperation(stage);
                        operations.add(context -> stage);
                    }
                } else {
                    log.error("Pipeline field is not a list: {}", pipelineStages);
                    throw new IllegalArgumentException("Invalid pipeline structure.");
                }
            } else {
                validateOperation(operation);
                operations.add(context -> operation);
            }
        } catch (Exception e) {
            log.error("Failed to build aggregation pipeline: {}", operation.toJson(), e);
            // Add a no-op as a fallback
            operations.add(context -> new Document());
        }
    }

    /**
     * Validates the operation to ensure no unsafe operations are included.
     *
     * @param operation The operation document to be validated.
     */
    private void validateOperation(Document operation) {
        for (String key : operation.keySet()) {
            if (key.startsWith("$") && UNSAFE_OPERATORS.contains(key)) {
                throw new IllegalArgumentException("Operation " + key + " is not allowed in aggregation.");
            }
        }
    }

    /**
     * Retrieves the list of aggregation operations parsed from the input document.
     *
     * @return A list of AggregationOperation objects representing the pipeline stages.
     */
    public List<AggregationOperation> getOperations() {
        return operations;
    }

    /**
     * Retrieves the collection name used in the aggregation pipeline.
     *
     * @return The name of the target collection.
     */
    public String getCollectionName() {
        if (collectionName == null) {
            throw new IllegalStateException("Collection name is not specified in the operation.");
        }
        return collectionName;
    }
}
