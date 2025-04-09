package org.example;

import javax.swing.*;
import java.awt.*;

public class ExpenseTracker {
    private JFrame frame;
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JTextField amountInput, dateInput, commentInput;
    private JComboBox<String> categoryBox;
    private JButton addButton, removeButton, editButton, sortButton, filterButton, statsButton, clearButton, exportButton;

    public ExpenseTracker() {
        frame = new JFrame("Трекер Витрат");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(173, 216, 230));

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        frame.add(new JScrollPane(expenseList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(new Color(180, 220, 240));

        categoryBox = new JComboBox<>(new String[]{"Їжа", "Транспорт", "Розваги", "Комунальні послуги", "Інше"});
        amountInput = new JTextField();
        dateInput = new JTextField("ДД.ММ.РРРР");
        commentInput = new JTextField();
        addButton = UIHelper.createStyledButton("Додати Витрату");

        inputPanel.add(new JLabel("Категорія:"));
        inputPanel.add(categoryBox);
        inputPanel.add(new JLabel("Сума:"));
        inputPanel.add(amountInput);
        inputPanel.add(new JLabel("Дата:"));
        inputPanel.add(dateInput);
        inputPanel.add(new JLabel("Коментар:"));
        inputPanel.add(commentInput);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10)); // Змінено на 4 стовпці
        buttonPanel.setBackground(new Color(180, 220, 240));

        removeButton = UIHelper.createStyledButton("Видалити");
        editButton = UIHelper.createStyledButton("Редагувати");
        sortButton = UIHelper.createStyledButton("Сортувати");
        filterButton = UIHelper.createStyledButton("Фільтр");
        statsButton = UIHelper.createStyledButton("Статистика");
        clearButton = UIHelper.createStyledButton("Очистити все");
        exportButton = UIHelper.createStyledButton("Експортувати");

        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exportButton); // Додаємо кнопку експорту

        frame.add(buttonPanel, BorderLayout.SOUTH);

        ExpenseHandler handler = new ExpenseHandler(expenseListModel, expenseList, amountInput, dateInput, commentInput, categoryBox);

        addButton.addActionListener(e -> handler.addExpense());
        removeButton.addActionListener(e -> handler.removeExpense());
        editButton.addActionListener(e -> handler.editExpense());
        sortButton.addActionListener(e -> handler.sortExpenses());
        filterButton.addActionListener(e -> handler.filterExpenses());
        statsButton.addActionListener(e -> handler.showStatistics());
        clearButton.addActionListener(e -> handler.clearExpenses());
        exportButton.addActionListener(e -> handler.exportExpensesToFile()); // Додаємо обробник події для експорту

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTracker::new);
    }
}
