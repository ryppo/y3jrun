package org.y3.jrun.view.model;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.y3.jrun.model.Model;
import org.y3.jrun.view.i18n.Messages;

public class ModelStatisticsDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame parentFrame;
	private JPanel specificPanel;
	private Model model;
	private int minSize = 400;

	/**
	 * Create the dialog.
	 */
	public ModelStatisticsDialog(JFrame _parentFrame, Model _model, JPanel _specificPanel) {
		parentFrame = _parentFrame;
		model = _model;
		specificPanel = _specificPanel;
		init();
	}

	private void init() {
		setTitle(Messages.getString(Messages.STATISTIC));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		ModelGenericStatisticsPanel contentPanel = new ModelGenericStatisticsPanel(model);
		tabbedPane.addTab(Messages.getString(Messages.COMMON), contentPanel);
		tabbedPane.addTab(Messages.getString(Messages.SPECIFIC), specificPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		pack();
		if (getSize().height < minSize && getSize().width < minSize) {
			setSize(minSize, minSize);
		}
		setLocationRelativeTo(parentFrame);
	}

}
