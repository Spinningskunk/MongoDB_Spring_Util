package com.ikun.cm.mongo.criteria;

import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.Map;

/**
 * @author: HeKun
 * @date: 2024/10/31 01:12
 * @description: Build a criteria by bson-like string
 * -This utility class is used to build query criteria from BSON-like strings.
 * - Since this is just a criteria-building tool, we don't need to worry about security concerns directly within this class.
 * - It only focuses on parsing the input string and constructing the appropriate Criteria object.
 * - Ensure that the input BSON-like string is properly validated and formatted before usage.
 * - For security, validation of the constructed criteria should be done separately when used in database queries.
 */
public class CriteriaBuilder {

    /**
     * Converts a Document into a Criteria.
     *
     * In Spring, we can directly pass a BSON-like string as a `Document`
     * (such as in the dangerous query example where we get data via `@RequestBody Document`),
     * and Spring automatically handles the conversion from BSON to a Document object.
     * This method uses that functionality to build a Criteria object.
     *
     * @param document The Document to convert into Criteria.
     * @return A Criteria object representing the conditions in the Document.
     */

    public static Criteria fromDocument(Document document) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Document) {
                Document subDocument = (Document) value;
                for (Map.Entry<String, Object> subEntry : subDocument.entrySet()) {
                    String operator = subEntry.getKey();
                    Object operand = subEntry.getValue();
                    applyOperator(criteria.and(key), operator, operand);
                }
            } else {
                criteria.and(key).is(value);
            }
        }
        return criteria;
    }

    /**
     * Parses the query logic and converts it into a Criteria object.
     * This method applies the specified operator and value to the given Criteria
     * based on the type of operator provided (e.g., $gt, $lte, $in, etc.).
     *
     * @param criteria The Criteria object to which the operator will be applied.
     * @param operator The operator used in the query (e.g., $gt, $lt, $eq).
     * @param value The value associated with the operator.
     *
     * @throws IllegalArgumentException If an unsupported operator or invalid value type is encountered.
     */
    private static void applyOperator(Criteria criteria, String operator, Object value) {
        switch (operator) {
            case "$gt":
                criteria.gt(value);
                break;
            case "$gte":
                criteria.gte(value);
                break;
            case "$lt":
                criteria.lt(value);
                break;
            case "$lte":
                criteria.lte(value);
                break;
            case "$ne":
                criteria.ne(value);
                break;
            case "$in":
                criteria.in((List<?>) value);
                break;
            case "$nin":
                criteria.nin((List<?>) value);
                break;
            case "$regex":
                criteria.regex(value.toString());
                break;
            case "$eq":
                // 使用 is() 方法相当于 $eq
                criteria.is(value);
                break;
            case "$elemMatch":
                if (value instanceof Document) {
                    criteria.elemMatch((Criteria) value);
                } else {
                    throw new IllegalArgumentException("需要提供一个 Document 作为值进行 elemMatch 查询");
                }
                break;
            case "$exists":
                if (value instanceof Boolean) {
                    criteria.exists((Boolean) value);
                } else {
                    throw new IllegalArgumentException("$exists 操作符的值必须是 Boolean 类型");
                }
                break;
            default:
                throw new IllegalArgumentException("不支持的操作类型: " + operator);
        }
    }
}
