package org.example;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class ExpenseHandler {
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JTextField amountInput, dateInput, commentInput;
    private JComboBox<String> categoryBox;
    private Map<String, String> expenseDetails;

    public ExpenseHandler(DefaultListModel<String> expenseListModel, JList<String> expenseList,
                          JTextField amountInput, JTextField dateInput, JTextField commentInput,
                          JComboBox<String> categoryBox) {
        this.expenseListModel = expenseListModel;
        this.expenseList = expenseList;
        this.amountInput = amountInput;
        this.dateInput = dateInput;
        this.commentInput = commentInput;
        this.categoryBox = categoryBox;
        this.expenseDetails = new HashMap<>();
    }

    public void addExpense() {
        String category = (String) categoryBox.getSelectedItem();
        String amount = amountInput.getText().trim();
        String date = dateInput.getText().trim();
        String comment = commentInput.getText().trim();

        if (!amount.isEmpty() && !date.isEmpty()) {
            String expenseEntry = category + " - " + amount + " грн - " + date + " - " + comment;
            expenseListModel.addElement(expenseEntry);
            expenseDetails.put(expenseEntry, "Деталі: " + comment);
            amountInput.setText("");
            dateInput.setText("ДД.ММ.РРРР");
            commentInput.setText("");
        }
    }

    public void removeExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedExpense = expenseListModel.getElementAt(selectedIndex);
            expenseListModel.remove(selectedIndex);
            expenseDetails.remove(selectedExpense);
        }
    }

    public void editExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedExpense = expenseListModel.getElementAt(selectedIndex);
            String[] parts = selectedExpense.split(" - ", 4);
            if (parts.length == 4) {
                String newComment = JOptionPane.showInputDialog("Редагувати коментар:", parts[3]);
                if (newComment != null && !newComment.trim().isEmpty()) {
                    String updatedExpense = parts[0] + " - " + parts[1] + " - " + parts[2] + " - " + newComment.trim();
                    expenseListModel.set(selectedIndex, updatedExpense);
                }
            }
        }
    }

    public void sortExpenses() {
        List<String> expenses = new ArrayList<>();
        for (int i = 0; i < expenseListModel.size(); i++) {
            expenses.add(expenseListModel.get(i));
        }
        expenses.sort(Comparator.comparing(expense -> Double.valueOf(expense.split(" - ")[1].replace(" грн", ""))));
        expenseListModel.clear();
        expenses.forEach(expenseListModel::addElement);
    }

    public void filterExpenses() {
        String filterCategory = (String) categoryBox.getSelectedItem();
        DefaultListModel<String> filteredModel = new DefaultListModel<>();
        for (int i = 0; i < expenseListModel.size(); i++) {
            String expense = expenseListModel.get(i);
            if (expense.startsWith(filterCategory)) {
                filteredModel.addElement(expense);
            }
        }
        expenseList.setModel(filteredModel);
    }

    public void showStatistics() {
        double total = 0;
        for (int i = 0; i < expenseListModel.size(); i++) {
            String expense = expenseListModel.get(i);
            total += Double.parseDouble(expense.split(" - ")[1].replace(" грн", ""));
        }
        JOptionPane.showMessageDialog(null, "Загальна сума витрат: " + total + " грн", "Статистика", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearExpenses() {
        expenseListModel.clear();
        expenseDetails.clear();
    }

    // Додаємо метод для експорту витрат у файл
    public void exportExpensesToFile() {
        try {
            File file = new File("expenses.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < expenseListModel.size(); i++) {
                writer.write(expenseListModel.getElementAt(i));
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(null, "Витрати збережено у файл: expenses.txt",
                    "Експорт завершено", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Помилка при збереженні файлу!",
                    "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
