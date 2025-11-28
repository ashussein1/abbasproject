package abbas.project.hotel.util;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class DataExporter {

    public static <T> void exportTableToCSV(TableView<T> table, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Table Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Set default filename
        fileChooser.setInitialFileName("export_data.csv");

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveFile(table, file);
        }
    }

    private static <T> void saveFile(TableView<T> table, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            // 1. Write Headers
            StringBuilder headers = new StringBuilder();
            for (TableColumn<T, ?> col : table.getColumns()) {
                headers.append(col.getText()).append(",");
            }
            // Remove last comma and add new line
            if (headers.length() > 0) headers.setLength(headers.length() - 1);
            writer.println(headers.toString());

            // 2. Write Data Rows
            ObservableList<T> rows = table.getItems();
            for (T item : rows) {
                StringBuilder rowString = new StringBuilder();
                for (TableColumn<T, ?> col : table.getColumns()) {
                    // Get the data from the cell
                    if (col.getCellData(item) != null) {
                        String data = col.getCellData(item).toString();
                        // Handle commas in data by wrapping in quotes
                        if (data.contains(",")) {
                            data = "\"" + data + "\"";
                        }
                        rowString.append(data);
                    }
                    rowString.append(",");
                }
                // Remove last comma
                if (rowString.length() > 0) rowString.setLength(rowString.length() - 1);
                writer.println(rowString.toString());
            }

            System.out.println("Export successful to: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}