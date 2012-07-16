package org.y3.jrun.view.model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.DefaultModelListModel;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;

public class ModelListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList list_modelList;
	private ModelForm modelForm;
	private JLabel label_countModels;
	private JTextField jt_search;
	private ApplicationController controller;
	private ApplicationFrame appFrame;
	
	public ModelListPanel(ApplicationFrame _appFrame, ApplicationController _controller, ModelForm _modelForm) {
		modelForm = _modelForm;
		controller = _controller;
		appFrame = _appFrame;
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		
		JPanel jp_search = new JPanel(new BorderLayout());
		jt_search = new JTextField();
		jt_search.addKeyListener(getKeyListenerForActionSearchModel());
		jp_search.add(jt_search, BorderLayout.CENTER);
		JButton jb_search = new JButton(IconDictionary.getImageIcon(IconDictionary.SEARCH));
		jb_search.setToolTipText(Messages.getString(Messages.SEARCH));
		jb_search.addActionListener(getActionListenerForActionSearchModel());
		jp_search.add(jb_search, BorderLayout.EAST);
		add(jp_search, BorderLayout.NORTH);
		
		list_modelList = new JList();
		list_modelList.setCellRenderer(new Model_ListCellRenderer());
		list_modelList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Object selection = list_modelList.getSelectedValue();
				if (selection instanceof Model) {
					modelForm.editModel((Model) selection);
				} else {
					modelForm.showModel(null);
				}
			}
		});
		list_modelList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					ListModel modelList = list_modelList.getModel();
					if (modelList == null
							|| (modelList != null && (list_modelList
									.getSelectedIndex() == 0 || modelList
									.getSize() == 0))) {
						focusSearchField();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					ListModel modelList = list_modelList.getModel();
					if (modelList != null) {
						focusModelEditor();
					}
				}
			}
		});
		JScrollPane scrollpane_modelList = new JScrollPane(list_modelList);
		add(scrollpane_modelList, BorderLayout.CENTER);
		
		label_countModels = new JLabel("");
		add(label_countModels, BorderLayout.SOUTH);
	}
	
	public void editModel() {
		if (list_modelList.getModel().getSize() == 1) {
			list_modelList.setSelectedIndex(0);
			focusModelEditor();
		}
	}
	
	public Model getSelectedModel() {
		Object selectedObject = list_modelList.getSelectedValue();
		if (selectedObject != null && selectedObject instanceof Model) {
			return (Model) selectedObject;
		} else {
			return null;
		}
	}
	
	public Model[] getSelectedModels() {
		Object[] selectedObjects = list_modelList.getSelectedValues();
		if (selectedObjects != null) {
			Model[] selectedModels = new Model[selectedObjects.length];
			int modelNo = 0;
			for (Object object: selectedObjects) {
				if (object instanceof Model) {
					selectedModels[modelNo] = (Model) object;
					modelNo++;
				}
			}
			return selectedModels;
		}
		return null;
	}
	
	public void setModel(ModelListModel modelListModel) {
		list_modelList.setModel(modelListModel);
		int countModels = 0;
		String modelText = getName() + ": ";
		if (modelListModel != null) {
			countModels = modelListModel.getSize();
		}
		modelText += countModels;
		label_countModels.setText(modelText);
	}
	
	public ModelListModel getModel() {
		ListModel model = list_modelList.getModel();
		if (model != null && model instanceof ModelListModel) {
			return (ModelListModel) model;
		} else {
			return null;
		}
	}
	
	public void setSelectedIndex(int index) {
		list_modelList.setSelectedIndex(index);
	}
	
	public void setSelectedValue(Object value, boolean shouldScroll) {
		list_modelList.setSelectedValue(value, shouldScroll);
	}
	
	public ActionListener getActionListenerForActionSearchModel() {
		return new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				searchModel();
			}
		};
	}
	
	public KeyListener getKeyListenerForActionSearchModel() {
		return new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					searchModel();
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					selectFirstModelFromModelList();
				} else if (arg0.getKeyCode() == KeyEvent.VK_F2) {
					editModel();
				}
			}
		};
	}
	
	public void selectFirstModelFromModelList() {
		list_modelList.grabFocus();
	}
	
	public void focusSearchField() {
		jt_search.grabFocus();
	}
	
	public void focusModelEditor() {
		modelForm.grabFocus();
	}
	
	public void searchModel() {
		UIHelper.startWaiting(appFrame);
		ModelListModel newModels = null;
		Model exampleModel = modelForm.getModel();
		String searchString = jt_search.getText();
		try {
			newModels = controller.searchModels(searchString, exampleModel);
		} catch (SQLException e) {
			appFrame.showUserMessage(e, exampleModel);
		}
		if (newModels != null) {
			setModel(newModels);
			if (newModels.getSize() == 1) {
				setSelectedValue(newModels.getElementAt(0), true);
			}
		} else {
			setModel(new DefaultModelListModel(null));
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	
}
