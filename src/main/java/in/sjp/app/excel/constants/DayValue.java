package in.sjp.app.excel.constants;

import java.util.Arrays;

public enum DayValue {
    M,
    T,
    W,
    Th,
    F,
    St,
    S;

    /**
     *
     * @param checkOverValue value to check in enum
     * @return boolean , either true | false
     */
    public static boolean contains(String checkOverValue) {
        return Arrays.stream(DayValue.values()).anyMatch(dayValue -> dayValue.name().equalsIgnoreCase(checkOverValue));
    }
}
