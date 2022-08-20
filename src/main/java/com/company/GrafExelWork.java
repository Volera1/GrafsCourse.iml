package com.company;

import com.company.GrafLogic.Graf;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GrafExelWork {

    public static String crateGrafExel(Graf graf,String path)throws IOException{
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Graf"); //создала лист
            Cell cell; //ячейка
            Row row; //строка (ребро)
            int rownum = 0; //номер строки (ребра)
            row = sheet.createRow(rownum); //создала 1 строку
            cell = row.createCell(0, CellType.STRING); //ячейка произвольная
            cell.setCellValue("Rebra/Grany");
            for (int i = 1; i <= graf.granyList.size(); i++) { //нумерация граней/вершин
                cell = row.createCell(i, CellType.NUMERIC);
                cell.setCellValue(i);
            }
            for (int rebroNum = 0; rebroNum < graf.rebraList.size(); rebroNum++) { //начало разбора по ребрам
                rownum++; //каждое ребро - новая строка
                row = sheet.createRow(rownum);
                cell = row.createCell(0, CellType.NUMERIC); //номер ребра
                cell.setCellValue(rebroNum + 1);
                for (int granNum = 0; granNum < graf.granyList.size(); granNum++) { //начало разбора по граням ребра (связи граней с ребром)
                    cell = row.createCell(granNum + 1, CellType.BOOLEAN); //ячейка значения
                    cell.setCellValue((graf.rebraList.get(rebroNum).startPoint == graf.granyList.get(granNum)) //сравнение с начальной точкой
                            || (graf.rebraList.get(rebroNum).finishPoint == graf.granyList.get(granNum)));//сравнение с конечной
                }//значене ячейки зависит от того, принадлежит ли вершине ребро
            }
            File file = new File(path + ".xls");

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            return "Граф успешно сохранен в "+ file.getAbsolutePath();
        }
        catch (NullPointerException ex){
            return "Я отказываюсь сохрянять твой пустой граф";
        }
    }

}
