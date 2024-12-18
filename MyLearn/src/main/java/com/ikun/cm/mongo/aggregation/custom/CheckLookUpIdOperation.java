package com.ikun.cm.mongo.aggregation.custom;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Arrays;

/**
 * @author: HeKun
 * @date: 2024/12/16 23:34
 * @description: Custom AggregationOperation: Ensures the specified field exists in the document.
 * If the field is missing, this operation will add it with a value of null.
 *
 * This operation is particularly useful in scenarios where:
 * 1. A field is optional in the source collection.
 * 2. Subsequent aggregation stages, such as $lookup, require the field to exist.
 * 3. Missing fields could lead to incorrect joins, as documents without the field may unintentionally match.
 *
 * Why do we need it? Here is an example:
 *
 * Main Collection (lookUp1):
 *  _id                lookupId    name
 *  eac7766b507a4248   ghijk       chang
 *  5d06288f66124809               xu            - Missing the lookupId field (interpreted as not having this field).
 *  aea1769eed504243   null        wang          - Null explicitly represents a real null value.
 *  c5a9b7ce30d447dc   abcdef      ikun
 *
 * Joined Collection (lookUp2):
 *  _id                lookupId    name
 *  9ea14b97d9d3486a   abcdef      english
 *  f5f35292b7be4741   ghijk       math
 *  8d2e188a5fa24f29   null        chinese       - Null explicitly represents a real null value.
 *  5b94a6559f654aad               art           - Missing the lookupId field (interpreted as not having this field).
 *
 * Problem Analysis:
 * When performing a $lookup operation between lookUp1 and lookUp2 on the lookupId field:
 * - The document for "xu" (missing lookupId, treated as `undefined`) will match:
 *   1. Documents in lookUp2 where lookupId is explicitly null (e.g., "chinese").
 *   2. Documents in lookUp2 where lookupId is missing (e.g., "art").
 * - The document for "wang" (lookupId explicitly null) will not match any documents
 *   because MongoDB treats explicit `null` differently from `undefined` (missing fields).
 *
 * Actual Behavior:
 * - "xu" matches:
 *   1. {"_id": "8d2e188a5fa24f29", "lookupId": null, "name": "chinese"}
 *   2. {"_id": "5b94a6559f654aad", "name": "art"}
 * - "wang" matches: (No documents).
 *
 * Expected Behavior:
 * - "xu" matches all documents with missing lookupId (undefined) or lookupId explicitly null.
 * - "wang" matches only documents with lookupId explicitly null.

 *
 * Solution:
 * This custom aggregation operation uses $addFields with $ifNull to ensure that:
 * 1. Documents missing the lookupId field will have a lookupId added with a null value.
 * 2. Subsequent aggregation stages (like $lookup) can handle these cases more predictably.
 *
 * MongoDB Equivalent for this Operation:
 * <pre>
 * {
 *   $addFields: {
 *     lookupId: { $ifNull: ["$lookupId", null] }
 *   }
 * }
 * </pre>
 *
 * Usage Example in Code:
 * <pre>{@code
 * Aggregation aggregation = Aggregation.newAggregation(
 *     new CheckLookUpIdOperation("lookupId"),
 *     Aggregation.lookup("lookUp2", "lookupId", "lookupId", "lookupData")
 * );
 * }</pre>
 */

public class CheckLookUpIdOperation implements AggregationOperation {

    /** The name of the field to check or add */
    private final String fieldName;

    /**
     * Constructor to initialize the field name that needs to be checked or added.
     *
     * @param fieldName the name of the field to ensure exists in the document
     */
    public CheckLookUpIdOperation(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Converts this operation into a MongoDB aggregation stage represented as a Document.
     *
     * @param context the aggregation operation context
     * @return a Document representing the $addFields stage in the MongoDB aggregation pipeline
     */
    @Override
    public Document toDocument(AggregationOperationContext context) {
        // Build the $addFields stage to add the field with null if it is missing
        return new Document("$addFields",
            new Document(fieldName, new Document("$ifNull", Arrays.asList("$" + fieldName, null)))
        );
    }
}
