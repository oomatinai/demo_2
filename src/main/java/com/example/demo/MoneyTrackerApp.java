package com.example.demo;

import com.example.demo.CategoryEnum.ChoiseCategory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MoneyTrackerApp extends Application {
    private double balance = 0.0;
    private Label balanceValueLabel;

    private TextField expenseTextField;
    private TextField incomeTextField;
    private PieChart pieChart;
    private ChoiceBox<ChoiseCategory> expenseCategoryChoiceBox;
    private ChoiseCategory selectedCategory;

    private Map<ChoiseCategory, Double> categoryBalances = new HashMap<>();

    public MoneyTrackerApp() {
        balanceValueLabel = new Label(Double.toString(balance));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Money Tracker App");

        categoryBalances.put(ChoiseCategory.TRANSPORT, 0.0);
        categoryBalances.put(ChoiseCategory.FOOD, 0.0);
        categoryBalances.put(ChoiseCategory.EDUCATION, 0.0);
        categoryBalances.put(ChoiseCategory.SHOPPING, 0.0);
        categoryBalances.put(ChoiseCategory.HOUSING, 0.0);
        categoryBalances.put(ChoiseCategory.LIFE, 0.0);
        categoryBalances.put(ChoiseCategory.INVESTMENTS, 0.0);
        categoryBalances.put(ChoiseCategory.OTHERS, 0.0);

        Label balanceLabel = new Label("Баланс: ");

        pieChart = new PieChart();
        pieChart.setPrefSize(300, 300);

        expenseTextField = new TextField();
        incomeTextField = new TextField();

        expenseCategoryChoiceBox = new ChoiceBox<ChoiseCategory>();
        expenseCategoryChoiceBox.getItems().addAll(ChoiseCategory.values());
        expenseCategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCategory = newValue;
            expenseCategoryChoiceBox.setValue(selectedCategory);
        });

        Button expenseButton = new Button("Списать");
        Button incomeButton = new Button("Добавить");

        expenseButton.setOnAction(e -> {
            try {
                double expense = Double.parseDouble(expenseTextField.getText());
                if (expense > 0) {
                    double currentBalance = categoryBalances.get(selectedCategory);
                    categoryBalances.put(selectedCategory, currentBalance - expense);

                    balance -= expense;
                    balanceValueLabel.setText(Double.toString(balance));
                    expenseTextField.clear();
                    updatePieChart();
                } else {
                    showAlert("Ошибка", "Введите корректную сумму расхода.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму расхода.");
            }
        });

        incomeButton.setOnAction(e -> {
            try {
                double income = Double.parseDouble(incomeTextField.getText());
                if (income > 0) {
                    balance += income;
                    balanceValueLabel.setText(Double.toString(balance));
                    incomeTextField.clear();
                    updatePieChart();
                } else {
                    showAlert("Ошибка", "Введите корректную сумму.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(balanceLabel, balanceValueLabel, pieChart, expenseCategoryChoiceBox, expenseTextField, expenseButton, incomeTextField, incomeButton);

        Scene scene = new Scene(root, 800, 600);
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

    private void updatePieChart() {
        pieChart.getData().clear();
        double totalBalance = categoryBalances.values().stream().mapToDouble(Double::doubleValue).sum();

        for (ChoiseCategory category : categoryBalances.keySet()) {
            double categoryBalance = categoryBalances.get(category);
            double percentage = (categoryBalance / totalBalance) * 100;
            PieChart.Data data = new PieChart.Data(category.toString() + " (" + String.format("%.2f", percentage) + "%)", categoryBalance);
            data.nameProperty().addListener((observable, oldValue, newValue) -> {
                data.setName(newValue);
            });
            pieChart.getData().add(data);
        }
    }
}
