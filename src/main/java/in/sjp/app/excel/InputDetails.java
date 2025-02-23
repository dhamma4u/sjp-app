package in.sjp.app.excel;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;


@Builder
@Setter
@Getter
public class InputDetails {

    private File excelFile;

    private String inTimeRangeStart;

    private String inTimeRangeEnd;

    private String outTimeRangeStart;

    private String outTimeRangeEnd;


}
