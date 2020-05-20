package com.zk.graduation.flink.util;

import com.zk.graduation.metadata.common.Column;
import org.apache.flink.types.Row;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static au.com.bytecode.opencsv.CSVWriter.INITIAL_STRING_SIZE;
import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;

/**
 * csv格式处理
 *
 * @author pengchenglin
 * @create 2020-05-20 14:26
 */
public class CsvUtil implements Serializable {

    /**
     * @param row
     * @param columns            columns
     * @param fieldDelimiter  字段分隔符
     * @param dateFormat      日期格式
     * @param timestampFormat 时间戳格式
     * @param csvFormat       true：quotechar与escape生效
     * @param quotechar       边界符
     * @param escape          转义符
     * @param dateFlag        true：将date和Timestamp转为long输出
     * @return
     * @Description:
     */
    public static String csvRowToString(Row row,
                                        List columns,
                                        String fieldDelimiter,
                                        String dateFormat,
                                        String timestampFormat,
                                        boolean csvFormat,
                                        String quotechar,
                                        String escape,
                                        boolean dateFlag) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < row.getArity(); i++) {
            if (i > 0) {
                str.append(fieldDelimiter);
            }
            if (null == row.getField(i)) {
                continue;
            }
            Column column = (Column) columns.get(i);
            switch (column.getDataType()) {
                case VARCHAR:
                    String csvLine;
                    if (csvFormat) {
                        csvLine = CsvUtil.toCsvLine(row.getField(i).toString(), fieldDelimiter.charAt(0), quotechar.charAt(0), escape.charAt(0));
                    } else {
                        csvLine = row.getField(i).toString();
                    }
                    str.append(csvLine);
                    break;
                case INT:
                    str.append(Integer.parseInt(row.getField(i).toString()));
                    break;
                case DOUBLE:
                    str.append(Double.parseDouble(row.getField(i).toString()));
                    break;
                case LONG:
                    str.append(Long.parseLong(row.getField(i).toString()));
                    break;
                case DATE:
                    if (dateFlag) {
                        //ture 表示将data转为long
                        str.append(getLongTime(row.getField(i)));
                    } else {
                        str.append(timestampFormat(row.getField(i), dateFormat));
                    }
                    break;
                case TIMESTAMP:
                    if (dateFlag) {
                        str.append(getLongTime(row.getField(i)));
                    } else {
                        str.append(timestampFormat(row.getField(i), timestampFormat));
                    }

                    break;
                default:
                    break;
            }
        }
        return str.toString();
    }

    /**
     *
     * @Description: 将String格式化为csv格式
     * @param nextElement
     * @param separator
     * @param quotechar
     * @param escapechar
     * @return
     */
    public static String toCsvLine(String nextElement, char separator, char quotechar, char escapechar) {
        if (nextElement == null)
            return null;
        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        if (nextElement.indexOf(separator)!=-1||stringContainsSpecialCharacters(nextElement, quotechar, escapechar))
            sb.append(quotechar);

        sb.append(stringContainsSpecialCharacters(nextElement, quotechar, escapechar) ?
                processLine(nextElement, quotechar, escapechar) : nextElement);

        if (nextElement.indexOf(separator)!=-1||stringContainsSpecialCharacters(nextElement, quotechar, escapechar))
            sb.append(quotechar);
        return sb.toString();
    }

    /**
     * 字符串是否包含特殊字符
     *
     * @param line
     * @param quotechar
     * @param escapechar
     * @return
     */
    private static boolean stringContainsSpecialCharacters(String line, char quotechar, char escapechar) {
        return line.indexOf(quotechar) != -1 || line.indexOf(escapechar) != -1;
    }

    /**
     * 处理含有特殊字符的字段
     *
     * @param nextElement
     * @param quotechar
     * @param escapechar
     * @return
     */
    private static StringBuilder processLine(String nextElement, char quotechar, char escapechar) {
        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        for (int j = 0; j < nextElement.length(); j++) {
            char nextChar = nextElement.charAt(j);
            if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                sb.append(escapechar).append(nextChar);
            } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                sb.append(escapechar).append(nextChar);
            } else {
                sb.append(nextChar);
            }
        }
        return sb;
    }

    /**
     *
     * @param date
     * @return
     * @Description:sql.Timestamp,sql.date,localDate,localDateTime -> long
     */
    public static long getLongTime(Object date) {
        if (date instanceof LocalDate) {
            return Date.valueOf((LocalDate)date).getTime();
        } else if (date instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime)date).getTime();
        } else if (date instanceof Date){
            return ((Date) date).getTime();
        } else if (date instanceof Timestamp){
            return ((Timestamp) date).getTime();
        }
        return 0;
    }

    /**
     * @param timestamp
     * @param timestampFormat
     * @return String
     * @Description:LocalDateTime/LocalDate-->String
     */
    public static String timestampFormat(Object timestamp, String timestampFormat) {
        if (timestamp == null) return null;
        Timestamp ts = new Timestamp(getLongTime(timestamp));
        DateFormat df = new SimpleDateFormat(timestampFormat);
        return df.format(ts);
    }




}
