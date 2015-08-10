package com.analysis.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author chenhao
 * 
 */
public class ExcelBuilder {

	/**
	 * @���ܣ�
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// HSSFWorkbook wb = new HSSFWorkbook();
		// HSSFSheet sheet = wb.createSheet();
		ExcelBuilder eb = new ExcelBuilder();
		// eb.createHead(sheet, wb);
		List list = new ArrayList();
		Object[] objs = new Object[] { new BigDecimal(12), "����������˾", new BigDecimal(20) };
		list.add(objs);
		HSSFWorkbook wb = eb.createExcel(list, null);
		// eb.writeDataToExcel(list, sheet, wb, 2);

		FileOutputStream fileOut = new FileOutputStream("d:\\���ѷ�̯2.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	@SuppressWarnings("unchecked")
	public HSSFWorkbook createExcel(List list, String[] titles) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		this.createHead(sheet, wb, titles);
		this.writeDataToExcel(list, sheet, wb, 2);
		return wb;
	}

	private int[] getColors(String str) {
		int[] color = new int[3];
		color[0] = Integer.parseInt(str.substring(1, 3), 16);
		color[1] = Integer.parseInt(str.substring(3, 5), 16);
		color[2] = Integer.parseInt(str.substring(5, 7), 16);
		return color;
	}

	/**
	 * 
	 * @���ܣ�������ͷ
	 * 
	 * @param sheet
	 * @param wb
	 */
	public void createHead(HSSFSheet sheet, HSSFWorkbook wb, String[] titles) {
		sheet.setColumnWidth(0, 2200);
		sheet.setColumnWidth(1, 8500);
		sheet.setColumnWidth(2, 3500);

		HSSFRow row = null;
		HSSFCellStyle style = null;
		row = getHSSFRow(sheet, 0, 400);
		// style = getHSSFCellStyle(wb, HSSFColor.LIGHT_GREEN.index);//
		// ������ʽ����Ԫ���ײ���ɫΪ����

		int[] colors = this.getColors("#63cefd");
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.BLACK.index, (byte) colors[0], (byte) colors[1], (byte) colors[2]);

		style = getHSSFCellStyle(wb, HSSFColor.LIGHT_GREEN.index);
		// style = wb.createCellStyle();
		this.setFont(wb, style, "����", 200, HSSFFont.BOLDWEIGHT_BOLD);// ��������
		setBorder(style, 1, HSSFColor.BLACK.index);// ���ñ߿�
		style.setWrapText(true);

		// ���ñ�ͷ��һ��
		createCell(style, row, 0, titles[0]);
		createCell(style, row, 1, titles[1]);
		createCell(style, row, 2, titles[2]);
	}

	/**
	 * 
	 * @���ܣ�����ͷ��ֻ��һ��
	 * 
	 * @param sheet
	 * @param colNames
	 *            Ҫ��ʾ��ͷ������
	 */
	public void createCommonHead(HSSFWorkbook wb, HSSFSheet sheet, String[] colNames) {
		if (colNames == null || colNames.length == 0) {
			wb = null;
			sheet = null;
			return;
		}
		HSSFCellStyle style = null;

		// style = getHSSFCellStyle(wb, HSSFColor.LIGHT_GREEN.index);//
		// ������ʽ����Ԫ���ײ���ɫΪ����
		style = getHSSFCellStyle(wb, HSSFColor.LIGHT_BLUE.index);
		this.setFont(wb, style, "����", 200, HSSFFont.BOLDWEIGHT_BOLD);// ��������
		setBorder(style, 1, HSSFColor.BLACK.index);// ���ñ߿�
		style.setWrapText(true);
		HSSFRow row = null;

		// ���ñ�ͷ��һ��
		row = this.getHSSFRow(sheet, 0, 600);
		for (int i = 0; i < colNames.length; i++) {
			this.createCell(style, row, i, colNames[i]);
		}
	}

	/**
	 * 
	 * @���ܣ�����ݼ�д�������
	 * 
	 * @param list
	 *            ��ݼ�
	 * @param sheet
	 * @param wb
	 * @param fromRow
	 *            д��Ŀ�ʼ��
	 */
	@SuppressWarnings("unchecked")
	public void writeDataToExcel(List list, HSSFSheet sheet, HSSFWorkbook wb, int fromRow) {
		if (list == null || list.isEmpty()) {
			return;
		}
		HSSFRow row = null;
		// �½���Ԫ����ʽ
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		setBorder(style, 1, HSSFColor.BLACK.index);
		Object[] objs = null;
		for (int i = 0; i < list.size(); i++) {
			objs = (Object[]) list.get(i);
			if (objs == null)
				continue;
			row = getHSSFRow(sheet, i + fromRow - 1, 400);// �ӵ����п�ʼ��������
			for (int j = 0; j < objs.length; j++) {
				if (objs[j] instanceof String || objs[j] == null || "".equals(objs[j])) {
					this.createCell(style, row, j, objs[j], HSSFCell.CELL_TYPE_STRING);
				} else if (objs[j] instanceof BigDecimal) {
					this.createCell(style, row, j, objs[j], HSSFCell.CELL_TYPE_NUMERIC);
				}
			}
		}
	}

	/**
	 * 
	 * @���ܣ���Ԫ����дֵ
	 * 
	 * @param style
	 *            ��Ԫ����ʽ
	 * @param r
	 *            ��
	 * @param col
	 *            ��
	 * @param value
	 *            ֵ
	 */
	public void createCell(HSSFCellStyle style, HSSFRow row, int col, Object value, int cellType) {
		HSSFCell cell = row.createCell(col);
		if (cellType == HSSFCell.CELL_TYPE_STRING) {
			if (value != null && value instanceof BigDecimal) {
				BigDecimal dec = (BigDecimal) value;
				cell.setCellValue(dec.doubleValue());
				dec = null;
			} else
				cell.setCellValue(new HSSFRichTextString((String) (value == null ? "" : value)));
		} else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			if (value != null) {
				BigDecimal dec = (BigDecimal) value;
				cell.setCellValue(dec.doubleValue());
				dec = null;
			}
		}
		cell.setCellStyle(style);

		cell = null;
	}

	/**
	 * 
	 * @���ܣ�����һ��
	 * 
	 * @param sheet
	 * @param r
	 *            �к�
	 * @param heigth
	 *            �и�
	 * @return
	 */
	public HSSFRow getHSSFRow(HSSFSheet sheet, int r, int heigth) {
		HSSFRow row = null;
		row = sheet.createRow(r);
		row.setHeight((short) heigth);

		return row;
	}

	/**
	 * 
	 * @���ܣ����õ�Ԫ��ı߿�
	 * 
	 * @param style
	 * @param border
	 *            �߿���
	 * @param color
	 *            �߿���ɫ
	 */
	public void setBorder(HSSFCellStyle style, int border, short color) {
		style.setBorderLeft((short) border);// ��߅��
		style.setBottomBorderColor(color);// ߅���ɫ
		style.setBorderRight((short) border);// ��߅��
		style.setBottomBorderColor(color);// ߅���ɫ
		style.setBorderTop((short) border);// �߅��
		style.setBottomBorderColor(color);// ߅���ɫ
		style.setBorderBottom((short) border);// ��߅��
		style.setBottomBorderColor(color);// ߅���ɫ
	}

	public void setFont(HSSFWorkbook wb, HSSFCellStyle style, String fontName, int fontHeight, short bw) {
		HSSFFont font = wb.createFont();
		font.setFontName(fontName);
		font.setFontHeight((short) fontHeight);
		font.setBoldweight(bw);
		style.setFont(font);
		font = null;
	}

	/**
	 * 
	 * @���ܣ���Ԫ����дֵ
	 * 
	 * @param style
	 *            ��Ԫ����ʽ
	 * @param r
	 *            ��
	 * @param col
	 *            ��
	 * @param value
	 *            ֵ
	 */
	public void createCell(HSSFCellStyle style, HSSFRow row, int col, String value) {
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
		cell = null;

	}

	/**
	 * 
	 * @���� ��ʽ
	 * 
	 * @param wb
	 * @param fgColor
	 * @return
	 */
	public HSSFCellStyle getHSSFCellStyle(HSSFWorkbook wb, short fgColor) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(fgColor);// ���õ�Ԫ�񱳾�ɫ
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ˮƽ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		return style;
	}

	/**
	 * 
	 * @���ܣ�����
	 * 
	 * @param wb
	 * @param response
	 * @param fileName
	 *            �ļ����
	 */
	public void downloadExcel(HSSFWorkbook wb, HttpServletResponse response, String fileName) {
		if (wb == null || wb.getSheetAt(0) == null)
			throw new NullPointerException("���Excel�ļ�����!");
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
			os = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String encodingFileName(String fileName) {
		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(fileName, "UTF-8");
			if (returnFileName.length() > 150) {
				returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnFileName;
	}
}
