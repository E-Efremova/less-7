package qa.efremova;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;
public class FileParsingZipTest {

    @Test
    void parseZipFile() throws Exception {

            ZipFile zipFile = new ZipFile("src\\test\\resources\\sample-zip-file.zip");// открыть архив
//            // Работаем с CSV
            ZipEntry zipEntryCsv = zipFile.getEntry("test.csv");// извлекаем файл из архива
            try (InputStream inputStream = zipFile.getInputStream(zipEntryCsv)) {
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                List<String[]> list = reader.readAll();
                assertThat(list)
                        .hasSize(5)
                        .contains(
                                new String[]{"номер", "имя", "адрес"},
                                new String[]{"1", "Сережа", "Ленина 6"},
                                new String[]{"2", "Василий", "Мира 0"}
                        );
            }

//            // работаем с PDF
            ZipEntry zipEntryPdf= zipFile.getEntry("Pdf-test.pdf");// извлекаем файл из архива
            try (InputStream inputStream = zipFile.getInputStream(zipEntryPdf)) {
            PDF parsed = new PDF(inputStream);
                assertThat(parsed.text).contains("Отправка товара");
                assertThat(parsed.author).contains("Elena Efremova");
        }

        // работаем с xls
        ZipEntry zipEntryXls= zipFile.getEntry("Xls-test.xlsx");// извлекаем файл из архива
           try (InputStream inputStream = zipFile.getInputStream(zipEntryXls)) {
              XLS parsed = new XLS(inputStream);
          assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue())
                   .isEqualTo("тест");
                   }

        }
    }





