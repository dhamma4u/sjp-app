package in.sjp.app.excel.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RowIdentifier {

    NAME_ROW("Emp. Code"),
    DAYS_ROW("Days"),
    STATUS_ROW("Status"),
    IN_TIME_ROW("InTime"),
    OUT_TIME_ROW("OutTime"),
    TOTAL_ROW("Total");

    final String rowIdentifierTextValue;

    RowIdentifier(String textToIdentifyRow) {
        this.rowIdentifierTextValue = textToIdentifyRow;
    }

    /**
     *
     * @param checkOverValue value to check in enum
     * @return boolean , either true | false
     */
    public static boolean contains(String checkOverValue) {
        return Arrays.stream(RowIdentifier.values()).anyMatch(dayValue -> dayValue.name().equalsIgnoreCase(checkOverValue));
    }

}
