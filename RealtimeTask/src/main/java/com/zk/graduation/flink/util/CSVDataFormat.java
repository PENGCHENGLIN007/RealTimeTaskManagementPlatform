package com.zk.graduation.flink.util;

import com.zk.graduation.metadata.common.Column;
import com.zk.graduation.metadata.common.DataType;
import com.zk.graduation.metadata.common.KafkaSinkInfo;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.types.Row;

import java.util.LinkedList;
import java.util.List;


public class CSVDataFormat {


	public static  DataStream<String>  outputFormat(DataStream<Row> dataStream, KafkaSinkInfo kafkaSinkInfo) {
		
    	//List<Column> list = kafkaSinkInfo.getSinkColumnList();
		List<Column> list = new LinkedList<>();
		Column column = new Column();
		column.setName("total");
		column.setDataType(DataType.DOUBLE);
		list.add(column);

		    String fieldDelimiter = ",";
	        String dateFormat = "";
	        String timestampFormat = "";
		    String quotechar ="\"";
	        String escape = "\\";
	        boolean csvFormat = false;
		return dataStream.map((MapFunction<Row, String>) row -> {
			String line = CsvUtil.csvRowToString(row, list,
					fieldDelimiter, dateFormat,
					timestampFormat, csvFormat, quotechar, escape, true);
			return line;
		});
	}
	

	}

