package org.example;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.File;

import static org.junit.Assert.*;

public class ExpenseHandlerTest {

    private DefaultListModel<String> model;
    private JList<String> list;
    private JTextField amountInput;
    private JTextField dateInput;
    private JTextField commentInput;
    private JComboBox<String> categoryBox;
    private ExpenseHandler handler;

    @Before
    public void setUp() {
        model = new DefaultListModel<>();
        list = new JList<>(model);
        amountInput = new JTextField();
        dateInput = new JTextField();
        commentInput = new JTextField();
        categoryBox = new JComboBox<>(new String[]{"Їжа", "Транспорт"});
        handler = new ExpenseHandler(model, list, amountInput, dateInput, commentInput, categoryBox);
    }

    @Test
    public void testAddExpense() {
        categoryBox.setSelectedItem("Їжа");
        amountInput.setText("100");
        dateInput.setText("01.01.2024");
        commentInput.setText("Обід");

        handler.addExpense();

        // Зміна категорії для перевірки помилки
        assertEquals(1, model.size());
        assertTrue(model.getElementAt(0).contains("Транспорт")); // Це викличе помилку, бо категорія "Їжа"
    }

    @Test
    public void testRemoveExpense() {
        model.addElement("Їжа - 100 грн - 01.01.2024 - Обід");
        list.setSelectedIndex(0);

        handler.removeExpense();

        assertEquals(0, model.size());
    }

    @Test
    public void testClearExpenses() {
        model.addElement("Транспорт - 50 грн - 01.01.2024 - Таксі");

        handler.clearExpenses();

        assertEquals(0, model.size());
    }

    @Test
    public void testSortExpenses() {
        model.addElement("Їжа - 200 грн - 01.01.2024 - Суп");
        model.addElement("Транспорт - 50 грн - 01.01.2024 - Метро");

        handler.sortExpenses();

        assertEquals("Транспорт - 50 грн - 01.01.2024 - Метро", model.get(0));
    }

    @Test
    public void testFilterExpenses() {
        model.addElement("Їжа - 200 грн - 01.01.2024 - Суп");
        model.addElement("Транспорт - 50 грн - 01.01.2024 - Метро");

        categoryBox.setSelectedItem("Їжа");

        handler.filterExpenses();

        assertEquals(1, list.getModel().getSize());
        assertTrue(list.getModel().getElementAt(0).startsWith("Їжа"));
    }

    @Test
    public void testExportExpensesToFile() {
        model.addElement("Їжа - 100 грн - 01.01.2024 - Сніданок");

        handler.exportExpensesToFile();

        File file = new File("expenses.txt");
        assertTrue(file.exists());

        file.delete();
    }
}
