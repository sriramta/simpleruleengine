
package org.acme.rule.domain;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "field",
    "type",
    "input",
    "operator",
    "value",
    "customInput",
    "valueText"
})
public class SubRule {

    @JsonProperty("id")
    private String id;
    @JsonProperty("field")
    private String field;
    @JsonProperty("type")
    private String type;
    @JsonProperty("input")
    private String input;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("value")
    private String value;
    @JsonProperty("customInput")
    private String customInput;
    @JsonProperty("valueText")
    private String valueText;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    @JsonProperty("field")
    public void setField(String field) {
        this.field = field;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("input")
    public String getInput() {
        return input;
    }

    @JsonProperty("input")
    public void setInput(String input) {
        this.input = input;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("customInput")
    public String getCustomInput() {
        return customInput;
    }

    @JsonProperty("customInput")
    public void setCustomInput(String customInput) {
        this.customInput = customInput;
    }

    @JsonProperty("valueText")
    public String getValueText() {
        return valueText;
    }

    @JsonProperty("valueText")
    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

}
