module poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos {
    requires javafx.controls;
    requires javafx.fxml;

    opens poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos to javafx.fxml, javafx.graphics;

    exports poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;
}
