package org.y3.jrun.view.model;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;

public abstract class ModelFormDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public ApplicationFrame appFrame;
	public ApplicationController controller;
	public ModelForm modelForm;
	public Model model;
	private boolean operationSucceeded = false;
	
	public ModelFormDialog(Model _model, ApplicationFrame applicationFrame, ApplicationController applicationController) {
		super(applicationFrame, true);
		appFrame = applicationFrame;
		model = _model;
		controller = applicationController;
		init();
	}
	
	public abstract ModelForm getModelForm();
	
	private void init() {
		JPanel panel_buttons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_buttons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_buttons, BorderLayout.SOUTH);
		
		JButton button_cancel = new JButton(Messages.getString(Messages.CANCEL), IconDictionary.getImageIcon(IconDictionary.CANCEL));
		button_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionCancel();
			}
		});
		panel_buttons.add(button_cancel);
		
		JButton button_save = new JButton(Messages.getString(Messages.SAVE), IconDictionary.getImageIcon(IconDictionary.SAVE));
		button_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (actionSave()) {
					operationSucceeded = true;
					setVisible(false);
				}
			}
		});
		panel_buttons.add(button_save);
		
		modelForm = getModelForm();
		getContentPane().add(modelForm, BorderLayout.CENTER);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			bindData();
			pack();
			setLocationRelativeTo(appFrame);
		}
		super.setVisible(visible);
	}
	
	public void bindData() {
		modelForm.bindData();
		modelForm.setModel(model);
	}
	
	public abstract boolean actionSave();

	private void actionCancel() {
		setVisible(false);
	}

	public boolean isOperationSucceeded() {
		return operationSucceeded;
	}
	
	public abstract Model getModel();
	
	@Override
	protected JRootPane createRootPane() {  
        JRootPane rootPane = super.createRootPane();  
        KeyStroke strokeCancel = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);  
        rootPane.registerKeyboardAction(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                // The action to be executed when Escape is pressed  
                ModelFormDialog.this.setVisible(false);  
            }  
        }, strokeCancel, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;  
    }
}
