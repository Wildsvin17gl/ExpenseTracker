package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UIHelper {
    private static final Color LIGHT_BLUE = new Color(135, 206, 250);
    private static final Color DARK_BLUE = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(102, 178, 255);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BORDER_COLOR = new Color(50, 50, 50);

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        // Динамічна перевірка темної або світлої теми
        boolean isDarkMode = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");
        Color backgroundColor = isDarkMode ? DARK_BLUE : LIGHT_BLUE;
        Color hoverColor = isDarkMode ? new Color(100, 149, 237) : HOVER_COLOR;
        Color textColor = isDarkMode ? Color.WHITE : TEXT_COLOR;

        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(160, 45));
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2, true));

        // Додаємо анімацію при наведенні
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }
}
