package org.y3.jrun.view.model;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class ModelPanelButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ModelPanelButtonPanel(ModelPanel _parentModelPanel) {
		init();
	}
	
	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
}
