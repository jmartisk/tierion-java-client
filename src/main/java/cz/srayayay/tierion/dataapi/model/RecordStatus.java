package cz.srayayay.tierion.dataapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RecordStatus {

    queued("queued"),
    unpublished("unpublished"),
    complete("complete");

    private final String status;

    RecordStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static RecordStatus forValue(String value) {
        return RecordStatus.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return status;
    }

}
