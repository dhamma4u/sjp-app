package in.sjp.app.excel;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class TimeRangeRandomizer {

    Logger logger = Logger.getLogger(TimeRangeRandomizer.class.getName());

    private List<String> baseRanges;
    private List<String> backUpBaseRanges;

    public TimeRangeRandomizer(@NonNull List<String> timeRanges) {
        this.baseRanges = new ArrayList<>(timeRanges);
        this.backUpBaseRanges = new ArrayList<>(timeRanges);
    }

    public String getRandomRangeWithoutRepeatUntilAllRanges() {
        if (CollectionUtils.isEmpty(baseRanges) && !CollectionUtils.isEmpty(backUpBaseRanges)) {
            logger.info("Base Ranges Cleared.. Now Repeating..again with ranges: " +  baseRanges);
            baseRanges = new ArrayList<>(backUpBaseRanges);
        }
        int rand = (int) (Math.random() * baseRanges.size());
        String range = baseRanges.get(rand);
        logger.info("Random Index : " + rand + ", Value: " + baseRanges.get(rand));
        baseRanges.remove(range);
        return range;
    }
}
