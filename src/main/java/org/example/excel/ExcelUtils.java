package org.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.example.entity.ExportedEntity;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * excel导出工具
 *
 * @author cliod
 * @since 10/24/20 9:31 AM
 */
public class ExcelUtils {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private ExcelUtils() {
	}

	/**
	 * 导出
	 *
	 * @param response 响应
	 * @param data     导出数据
	 * @param head     导出对象类型
	 */
	public static void write(HttpServletResponse response, List<?> data, Class<?> head) throws IOException {
		write(response, data, head(head), LocalDate.now().format(FORMATTER) + "送货单导出", "Sheet1", ExcelTypeEnum.XLS);
	}

	/**
	 * 导出
	 *
	 * @param response  响应
	 * @param data      导出数据
	 * @param fileName  文件名称
	 * @param sheetName 表格名称
	 * @param head      导出对象类型
	 */
	public static void write(HttpServletResponse response, List<?> data, List<List<String>> head, String fileName, String sheetName,
	                         ExcelTypeEnum fileType) throws IOException {
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("utf8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + fileType.getValue());
		EasyExcel.write(response.getOutputStream()).head(head).excelType(fileType).sheet(sheetName).doWrite(data);
	}

	public static void export(HttpServletResponse response, List<ExportedEntity> list) throws IOException {
		List<ExportedModel> models = new ArrayList<>();
		for (ExportedEntity entity : list) {
			models.addAll(ExportedModel.from(entity));
		}
		write(response, models, ExportedModel.class);
	}

	private static List<List<String>> head(Class<?> head) {
		List<List<String>> list = new ArrayList<>();

		String date = LocalDate.now().format(FORMATTER);

		ExcelProperty property;
		for (Field field : head.getDeclaredFields()) {
			property = field.getAnnotation(ExcelProperty.class);
			List<String> sHead = new ArrayList<>();
			sHead.add(date + "送货单导出");
			if (Objects.nonNull(property)) {
				sHead.addAll(Arrays.asList(property.value()));
			} else {
				sHead.addAll(Collections.singletonList(field.getName()));
			}
			list.add(sHead);
		}
		return list;
	}
}
