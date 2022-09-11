package com.company;

import com.company.GrafLogic.Graf;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class GrafExelWork {

    public static String crateGrafExel(Graf graf, String path) throws IOException {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Graf"); //создала лист
            Cell cell; //ячейка
            Row row; //строка (ребро)
            int rownum = 0; //номер строки (ребра)
            row = sheet.createRow(rownum); //создала 1 строку
            cell = row.createCell(0, CellType.STRING); //ячейка произвольная
            cell.setCellValue("Rebra/Grany");
            for (int i = 1; i <= graf.vertexList.size(); i++) { //нумерация граней/вершин
                cell = row.createCell(i, CellType.NUMERIC);
                cell.setCellValue(i);
            }
            for (int rebroNum = 0; rebroNum < graf.rebraList.size(); rebroNum++) { //начало разбора по ребрам
                rownum++; //каждое ребро - новая строка
                row = sheet.createRow(rownum);
                cell = row.createCell(0, CellType.NUMERIC); //номер ребра
                cell.setCellValue(rebroNum + 1);
                for (int granNum = 0; granNum < graf.vertexList.size(); granNum++) { //начало разбора по вершина ребра (связи граней с ребром)
                    cell = row.createCell(granNum + 1, CellType.BOOLEAN); //ячейка значения
                    cell.setCellValue((graf.rebraList.get(rebroNum).startPoint == graf.vertexList.get(granNum)) //сравнение с начальной точкой
                            || (graf.rebraList.get(rebroNum).finishPoint == graf.vertexList.get(granNum)));//сравнение с конечной
                }//значене ячейки зависит от того, принадлежит ли вершине ребро
            }
            if (!path.substring(path.length() - 4).equals(".xls")){ //если раширение не указано, то указывается по умолчанию xls
                path=path + ".xls";
            }
            File file = new File(path);

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            return "Граф успешно сохранен в " + file.getAbsolutePath();
        } catch (NullPointerException ex) {
            return "Я отказываюсь сохрянять твой пустой граф";
        }
    }

    public static String readGrafExel(Graf graf, String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);

        Graf newgraf = new Graf();//записываемый граф
        int rowNum=0; //номер считываемой строки (ребро)
        int columnNum; //номер считываемого столбца (вершина)

        Iterator<Row> rowIterator = sheet.iterator(); //читаем строку
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();//читаем ячейку в строке
            columnNum=0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) { //разбираемся с типами
                    case _NONE:
                    case BLANK:
                        case STRING://ничего не происходит
                    case FORMULA:
                        break;
                    case BOOLEAN://значение связи
                        if (cell.getBooleanCellValue()){ //если связь имеется
                            if (!newgraf.rebraStartCheck(newgraf.getRebra(rowNum))){ //если начальная вершина не принадлежит графу, то ставится как первая
                                newgraf.getRebra(rowNum).startPoint=newgraf.getGrany(columnNum);
                            }
                            if (!newgraf.rebraFinishCheck(newgraf.getRebra(rowNum))){ //если конечная вершина не принадлежит графу, то становится конечной
                                newgraf.getRebra(rowNum).finishPoint=newgraf.getGrany(columnNum);
                            }
                        }
                        break;
                    case NUMERIC: //номер вершины/ребра
                        if (rowNum==0){//вершина
                            newgraf.addVertex();
                        }
                        else if (columnNum==0){//ребро
                            newgraf.addRebra();
                        }
                        break;
                    case ERROR:
                        return "Возникла ошибка внутри файла Exel";
                }
                columnNum+=1;
            }
            rowNum+=1;
        }
        if (newgraf.vertexList.size()>=1){ //ограничения на существование графа
            graf.cloneGraf(newgraf);
            return "Файл успешно прочитан. Граф передан";
        }
        return "Пустой граф, ошибка";
    }
}
