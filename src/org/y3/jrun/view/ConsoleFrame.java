package org.y3.jrun.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.Font;

public class ConsoleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	int height = 200;
	int distance = 10;

	private JTextArea textarea_console;

	/**
	 * Create the frame.
	 */
	public ConsoleFrame() {
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(distance, screen.height - height - distance, screen.width - 2*distance, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textarea_console = new JTextArea();
		textarea_console.setFont(new Font("Consolas", Font.PLAIN, 9));
		textarea_console.setLineWrap(true);
		textarea_console.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) textarea_console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textarea_console);
	}
	
	public void logMessage(String message) {
		textarea_console.append(message);
	}

}
