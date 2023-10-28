
package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        expenseCategory.getItems().addAll("Транспорт", "Еда и напитки", "Покупки", "Жилье", "Коммунальные услуги", "Автомобиль", "Учеба","Финансовые расходы", "Здоровье", "Связь", "Другое");
        expenseCategory.setValue("Категории");

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
                    showAlert("Ошибка", "Введите корректную сумму.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму.");
            }
        });

        // Создаем макет
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(20);
        gridPane.setStyle("-fx-background-color: #E8E8E8;");

        // Добавляем стили для элементов
        balanceLabel.setStyle("-fx-font-weight: bold;");
        balanceValueLabel.setStyle("-fx-font-weight: bold;");
        balanceValueLabel.setFont(Font.font(16));
        expenseLabel.setStyle("-fx-font-weight: bold;");
        expenseButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white;");
        incomeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        incomeLabel.setStyle("-fx-font-weight: bold;");
        expenseCategory.setStyle("-fx-font-size: 14;");

        // Создаем разделы для доходов и расходов
        TitledPane expensePane = new TitledPane("Расходы", createExpensePane(expenseLabel, expenseTextField, expenseCategory, expenseButton));
        TitledPane incomePane = new TitledPane("Доходы", createIncomePane(incomeLabel, incomeTextField, incomeButton));

        // Создаем аккордеон для разделов
        Accordion accordion = new Accordion(expensePane, incomePane);

        // Добавляем элементы на макет
        GridPane.setConstraints(balanceLabel, 0, 0);
        GridPane.setConstraints(balanceValueLabel, 1, 0);
        GridPane.setConstraints(accordion, 0, 1, 3, 1);

        gridPane.getChildren().addAll(balanceLabel, balanceValueLabel, accordion);

        Scene scene = new Scene(gridPane, 400, 300);
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

    private VBox createExpensePane(Label expenseLabel, TextField expenseTextField, ChoiceBox<String> expenseCategory, Button expenseButton) {
        VBox expensePane = new VBox(10);
        expensePane.setPadding(new Insets(10, 10, 10, 10));
        expensePane.getChildren().addAll(expenseLabel, expenseTextField, expenseCategory, expenseButton);
        return expensePane;
    }

    private VBox createIncomePane(Label incomeLabel, TextField incomeTextField, Button incomeButton) {
        VBox incomePane = new VBox(10);
        incomePane.setPadding(new Insets(10, 10, 10, 10));
        incomePane.getChildren().addAll(incomeLabel, incomeTextField, incomeButton);
        return incomePane;
    }
}
