package in.sjp.app.excel.constants;

import java.util.Arrays;

public enum StatusValue {
    P,
    WO,
    A;

    /**
     *
     * @param checkOverValue value to check in enum
     * @return boolean , either true | false
     */
    public static boolean contains(String checkOverValue) {
        return Arrays.stream(StatusValue.values()).anyMatch(statusValue -> statusValue.name().equalsIgnoreCase(checkOverValue));
    }
}
