package qa.efremova;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;

import static org.assertj.core.api.Assertions.assertThat;
public class FileParsingZipTest {

    @Test
    void parseZipFile() throws Exception {

            ZipFile zipFile = new ZipFile("src\\test\\resources\\sample-zip-file.zip");// открыть архив
            // Работаем с CSV
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

            // работаем с PDF
            ZipEntry zipEntryPdf= zipFile.getEntry("Pdf-test.pdf");// извлекаем файл из архива
            try (InputStream inputStream = zipFile.getInputStream(zipEntryPdf)) {
            PDF parsed = new PDF(inputStream);
            assertThat(parsed.content).contains("Отправка товара");
        }

        // работаем с xls
           ZipEntry zipEntryXls= zipFile.getEntry("Xls-test.xls");// извлекаем файл из архива
           try (InputStream inputStream = zipFile.getInputStream(zipEntryXls)) {
               XLS parsed = new XLS(inputStream);
          assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue())
                   .isEqualTo("тест");
                   }

        }
    }





