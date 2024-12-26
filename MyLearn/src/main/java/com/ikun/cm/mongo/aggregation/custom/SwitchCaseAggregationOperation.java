package com.ikun.cm.mongo.aggregation.custom;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author HeKun
 * @date 2024/12/4 14:20
 * @description:
 * A custom aggregation operation that implements a switch-case logic for MongoDB aggregation pipeline.
 * This operation allows you to conditionally transform values based on the field's content, similar to a
 * switch-case statement in programming languages.
 *
 * It takes a field name, a map of case values and their corresponding results, and a default value to
 * handle cases where no match is found. The result of the aggregation will be stored in the specified
 * field name (or a default name if not provided).
 *
 * This class is useful for simplifying complex conditional logic in MongoDB aggregation pipelines
 * without manually constructing complex `$switch` operations each time.
 *
 * Example usage:
 * <pre>
 * SwitchCaseAggregationOperation operation = new SwitchCaseAggregationOperation("status", "status_result",
 *     Map.of("active", 1, "inactive", 0), -1);
 * </pre>
 *
 * The above example will return `1` if `status` is `"active"`, `0` if `"inactive"`, or `-1` as a
 * default value for all other cases.
 *
 */
public class SwitchCaseAggregationOperation implements AggregationOperation {

    /**
     * the field name to evaluate in the case statements
     */
    private final String fieldName;

    /**
     * the field name to store the result (default is "caseResult" if not provided)
     */
    private final String caseResultFieldName;
    /**
     * a map of key-value pairs for the case conditions and corresponding results
     */
    private final Map<String, Object> valueMap;

    /**
     * the default value to return if no case matches
     */
    private final Object defaultValue;

    /**
     * Builds an aggregation operation that handles a switch-case logic.
     * @param fieldName the field name to evaluate in the case statements
     * @param caseResultFieldName the field name to store the result (default is "caseResult" if not provided)
     * @param valueMap a map of key-value pairs for the case conditions and corresponding results
     * @param defaultValue the default value to return if no case matches
     */
    public SwitchCaseAggregationOperation(String fieldName, String caseResultFieldName,Map<String, Object> valueMap,Object defaultValue) {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("fieldName cannot be null or empty");
        }else{
            this.fieldName = fieldName;
        }
        this.caseResultFieldName = (caseResultFieldName != null && !caseResultFieldName.isEmpty()) ? caseResultFieldName : "caseResult";
        this.valueMap = valueMap;
        this.defaultValue = defaultValue;
    }

    /**
     * Converts this aggregation into a MongoDB Document.
     * This method constructs a $switch aggregation operation with the specified conditions and default value.
     * @param context the context of the aggregation operation.
     * @return a Document representing the $switch aggregation operation.
     */
    @Override
    public Document toDocument(AggregationOperationContext context) {
        // Build the list of condition branches for the $switch operation
        List<Document> branches = buildBranches();

        // Construct the $switch aggregation operation
        return new Document("$addFields", new Document(this.caseResultFieldName,
                new Document("$switch", new Document("branches", branches)
                        // Provide a default value when there are no conditions match
                        .append("default", this.defaultValue))
        ));
    }

    /**
     * Builds the condition branches for the $switch operation based on the valueMap.
     * Each entry in the valueMap creates a case condition where the field value is compared
     * against the provided keys in the map.
     * @return
     */
    private List<Document> buildBranches() {
        // Dynamically create the condition branches based on the provided valueMap
        return valueMap.entrySet().stream()
                .map(entry -> {
                    // Build each condition branch
                    return new Document("case", new Document("$eq", Arrays.asList("$" + fieldName, entry.getKey())))
                            .append("then", entry.getValue());
                })
                .collect(Collectors.toList());
    }
}
