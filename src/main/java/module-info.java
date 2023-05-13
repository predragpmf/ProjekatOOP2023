module pmf.projekatoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires waffle.jna;

    opens pmf.projekatoop.gui to javafx.controls, javafx.fxml, javafx.graphics;
    opens pmf.projekatoop.database to java.sql, waffle.jna;

    exports pmf.projekatoop;
}