package org.y3.jrun.view.model;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.y3.jrun.model.Model;
import org.y3.jrun.view.i18n.Messages;

public abstract class ModelForm extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static enum MODE {
		VIEWER, EDITOR, ADMINISTRATOR
	}
	
	private MODE actualMode = MODE.VIEWER;
	private TitledBorder border;
	
	/**
	 * Create the panel.
	 */
	public ModelForm(MODE mode) {
		if (mode != null) {
			actualMode = mode;
		}
		init();
	}
	
	public void editModel(Model _model) {
		setModel(_model);
		switchMode(MODE.EDITOR);
	}
	
	public void administrateModel(Model _model) {
		setModel(_model);
		switchMode(MODE.ADMINISTRATOR);
	}
	
	public void showModel(Model _model) {
		setModel(_model);
		switchMode(MODE.VIEWER);
	}
	
	protected void init() {
		border = new TitledBorder(null, null, TitledBorder.LEADING,
				TitledBorder.TOP, null, null);
		setBorder(border);
		initForm();
		switchMode(actualMode);
	}
	
	
	protected abstract void initForm();
	
	public void switchMode(MODE mode) {
		actualMode = mode;
		String modeText = "";
		switch (actualMode) {
		case VIEWER:
			modeText = Messages.getString(Messages.MODE_VIEW);
			switchFormToViewMode();
			break;
		case EDITOR:
			modeText = Messages.getString(Messages.MODE_EDIT);
			switchFormToEditMode();
			break;
		case ADMINISTRATOR:
			modeText = Messages.getString(Messages.MODE_ADMINISTRATE);
			switchFormToAdministrationMode();
		}
		border.setTitle(modeText);
		repaint();
	}
	
	public abstract void bindData();
	
	protected abstract void switchFormToEditMode();
	
	protected abstract void switchFormToAdministrationMode();
	
	protected abstract void switchFormToViewMode();
	
	public abstract void setModel(Model _model);
	
	public abstract Model getModel();
	
	public abstract JComponent getFirstFocusableEditorComponent();
	
	public void grabFocus() {
		requestFocus();
		JComponent firstEditor = getFirstFocusableEditorComponent();
		if (firstEditor != null) {
			firstEditor.grabFocus();
		}
	} 

}
