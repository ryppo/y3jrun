package org.y3.jrun.view.model;

import javax.swing.JMenu;

public class ModelMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private ModelPanel parentModelPanel;
	
	public ModelMenu(ModelPanel _parentModelPanel) {
		parentModelPanel = _parentModelPanel;
		init();
	}
	
	private void init() {
		
		setText(parentModelPanel.getPanelTitle());
		
	}
	
	
}
