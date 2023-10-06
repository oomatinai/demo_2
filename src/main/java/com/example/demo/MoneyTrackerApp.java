package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MoneyTrackerApp extends Application {
    private double balance = 0.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Money Tracker App");

        // Создаем элементы интерфейса
        Label balanceLabel = new Label("Баланс: ");
        Label balanceValueLabel = new Label(Double.toString(balance));
        Label expenseLabel = new Label("Расход: ");
        TextField expenseTextField = new TextField();
        Button expenseButton = new Button("Списать");
        ChoiceBox<String> expenseCategory = new ChoiceBox<>();
        expenseCategory.getItems().addAll("Транспорт", "Еда", "Покупки", "Шоппинг");
        expenseCategory.setValue("Прочее");

        Label incomeLabel = new Label("Доход: ");
        TextField incomeTextField = new TextField();
        Button incomeButton = new Button("Добавить");

        // Обработчик кнопки "Списать"
        expenseButton.setOnAction(e -> {
            try {
                double expense = Double.parseDouble(expenseTextField.getText());
                if (expense > 0) {
                    balance -= expense;
                    balanceValueLabel.setText(Double.toString(balance));
                    expenseTextField.clear();
                } else {
                    showAlert("Ошибка", "Введите корректную сумму расхода.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму расхода.");
            }
        });

        // Обработчик кнопки "Добавить"
        incomeButton.setOnAction(e -> {
            try {
                double income = Double.parseDouble(incomeTextField.getText());
                if (income > 0) {
                    balance += income;
                    balanceValueLabel.setText(Double.toString(balance));
                    incomeTextField.clear();
                } else {
                    showAlert("Ошибка", "Введите корректную сумму дохода.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму дохода.");
            }
        });

        // Создаем макет
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // Добавляем стили для элементов
        gridPane.setStyle("-fx-background-color: #f0f0f0;");
        balanceLabel.setStyle("-fx-font-weight: bold;");
        balanceValueLabel.setStyle("-fx-font-weight: bold;");
        incomeLabel.setStyle("-fx-font-weight: bold;");
        expenseButton.setStyle("-fx-background-color: #ff5555; -fx-text-fill: white;");
        incomeButton.setStyle("-fx-background-color: #55ff55; -fx-text-fill: white;");

        // Добавляем элементы на макет
        GridPane.setConstraints(balanceLabel, 0, 0);
        GridPane.setConstraints(balanceValueLabel, 1, 0);
        GridPane.setConstraints(expenseLabel, 0, 1);
        GridPane.setConstraints(expenseTextField, 1, 1);
        GridPane.setConstraints(expenseCategory, 2, 1);
        GridPane.setConstraints(expenseButton, 0, 2);

        GridPane.setConstraints(incomeLabel, 0, 3);
        GridPane.setConstraints(incomeTextField, 1, 3);
        GridPane.setConstraints(incomeButton, 0, 4);

        gridPane.getChildren().addAll(balanceLabel, balanceValueLabel, expenseLabel, expenseTextField, expenseCategory, expenseButton,
                incomeLabel, incomeTextField, incomeButton);

        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
