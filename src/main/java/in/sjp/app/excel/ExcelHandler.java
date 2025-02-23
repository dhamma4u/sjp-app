package in.sjp.app.excel;

import in.sjp.app.excel.constants.DayValue;
import in.sjp.app.excel.constants.RowIdentifier;
import in.sjp.app.excel.constants.StatusValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Slf4j

public class ExcelHandler {

    static Logger logger = Logger.getLogger(ExcelHandler.class.getName());


    /**
     * If you're using .xlsx excel files,
     * you may want to use Apache Poi XSSF objects (as XSSFWorkbook, XSSFRow and XSSFCell).
     * More about XSSF vs HSSF on this post.
     *
     * @param inputDetails input details with Required fields
     */
    public static String manipulateAttendance(InputDetails inputDetails) {
        logger.info("[excel-handler ] " + inputDetails.getExcelFile().getPath());
        logger.info("Start Time Intervals Start [ " + inputDetails.getInTimeRangeStart()
                + " , "
                + inputDetails.getInTimeRangeEnd() + " ]: ");
        try {
            List<String> inTimeRanges = TimeHandler.extractTimeRanges(inputDetails.getInTimeRangeStart(), inputDetails.getInTimeRangeEnd());
            List<String> outTimeRanges = TimeHandler.extractTimeRanges(inputDetails.getOutTimeRangeStart(), inputDetails.getOutTimeRangeEnd());
            logger.info("[excel-handler:in-time-all-ranges ] " + inTimeRanges);
            logger.info("[excel-handler:out-time-all-ranges ] " + outTimeRanges);
            TimeRangeRandomizer inTimeRangeRandomizer = new TimeRangeRandomizer(inTimeRanges);
            TimeRangeRandomizer outTimeRangeRandomizer = new TimeRangeRandomizer(outTimeRanges);

            // Creating input stream
            FileInputStream inputStream = new FileInputStream(inputDetails.getExcelFile());

            // Creating workbook from input stream
            Workbook workbook = WorkbookFactory.create(inputStream);

            // Getting the first sheet from workbook
            Sheet sheet = workbook.getSheetAt(0);

            //Getting the count of existing records
            int rowCount = sheet.getLastRowNum();
            logger.info("Rows In Excel : " + rowCount);
            Map<RowIdentifier, Row> identifierRowMap = new HashMap<>();
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                RowIdentifier currentRowIdentifier = null;
                StringBuilder rowString = new StringBuilder();
                for (Cell cell : row) {
                    rowString.append(" | ").append(formatter.formatCellValue(cell));
                    RowIdentifier rowIdentifier = findRowIdentifier(formatter, cell);
                    if (Objects.nonNull(rowIdentifier)) {
                        identifierRowMap.put(rowIdentifier, row);
                        currentRowIdentifier = rowIdentifier;
                        break;
                    }
                }

                logger.info("Row  :: " + rowString.toString());
                if (RowIdentifier.IN_TIME_ROW.equals(currentRowIdentifier)) {
                    updateColumnValueWithTimeRangeValue(row, formatter, identifierRowMap, inTimeRangeRandomizer);
                }

                if (RowIdentifier.OUT_TIME_ROW.equals(currentRowIdentifier)) {
                    updateColumnValueWithTimeRangeValue(row, formatter, identifierRowMap, outTimeRangeRandomizer);
                }
            }

            return createNewExcelWithModifications(inputDetails, workbook);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateColumnValueWithTimeRangeValue(Row row, DataFormatter formatter, Map<RowIdentifier, Row> identifierRowMap, TimeRangeRandomizer timeRangeRandomizer) {
        for (Cell cell : row) {
            String statusRowCellValue = formatter.formatCellValue(identifierRowMap.get(RowIdentifier.STATUS_ROW).getCell(cell.getColumnIndex()));
            String dayRowCellValue = formatter.formatCellValue(identifierRowMap.get(RowIdentifier.DAYS_ROW).getCell(cell.getColumnIndex()));

            StatusValue statusValue = StatusValue.contains(statusRowCellValue) ? StatusValue.valueOf(statusRowCellValue) : null;
            DayValue dayValue = DayValue.contains(dayRowCellValue) ? DayValue.valueOf(dayRowCellValue.split("\\s+")[1].trim()) : null;

            if (!DayValue.S.equals(dayValue)
                    && !StatusValue.WO.equals(statusValue)
                    && !StatusValue.A.equals(statusValue)
                    && formatter.formatCellValue(cell).contains(":")) {
                cell.setCellValue(timeRangeRandomizer.getRandomRangeWithoutRepeatUntilAllRanges());
            }
        }
    }

    /**
     * @param inputDetails Input Details for File Path and Name
     * @param workbook     Current Workbook/Excel Sheet Which need to write/
     * @return {@link String} Path Where new File Created.
     * @throws IOException in case of Writing Failure
     */
    private static String createNewExcelWithModifications(InputDetails inputDetails, Workbook workbook) throws IOException {
        //  update the original e-x-cel file with all changes!
        Calendar calendar = Calendar.getInstance();
        String dateFormat = calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.MONTH) + "_" + calendar.get(Calendar.YEAR);
        String outputFileNameWithPath = inputDetails.getExcelFile().getParent() + File.separator + dateFormat + "_output.xlsx";
        FileOutputStream outputStream = new FileOutputStream(outputFileNameWithPath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        logger.info("Created Updated Excel File.. at Path: " + outputFileNameWithPath);
        return outputFileNameWithPath;
    }

    /**
     * @param formatter formatter to chck values
     * @param cell      Cell/Column of Active/Current
     * @return {@link RowIdentifier} enum Value
     */
    private static RowIdentifier findRowIdentifier(DataFormatter formatter, Cell cell) {
        return Arrays.stream(RowIdentifier.values())
                .filter(rowIdentifier -> formatter.formatCellValue(cell).contains(rowIdentifier.getRowIdentifierTextValue()))
                .findFirst().orElse(null);
    }


}
