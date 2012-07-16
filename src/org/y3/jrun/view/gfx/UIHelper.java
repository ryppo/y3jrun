package org.y3.jrun.view.gfx;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.y3.jrun.view.i18n.Messages;

public class UIHelper {

	/**
	 * Create badge using given string text
	 * 
	 * @param bagdeText
	 *            badge text
	 * @return badge text filled badge
	 */
	public static ImageIcon createBadge(String bagdeText) {

		BufferedImage image = null;
		try {
			image = ImageIO
					.read(UIHelper.class
							.getResource("/org/y3/jrun/view/resource/Blank_Badge_Grey.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.BLACK);
		int xPix_length_1 = 4;
		int yPix_length_1 = 13;
		int xPix_length_2 = 1;
		int yPix_length_2 = 13;
		
		int useX = xPix_length_1;
		int useY = yPix_length_1;
		if (bagdeText != null && bagdeText.length() > 1) {
			useX = xPix_length_2;
			useY = yPix_length_2;
		}
		graphic.drawString(bagdeText, useX, useY);
		return new ImageIcon(image);
	}
	
	public static void startWaiting(JFrame waitingFrame) {
		if (waitingFrame != null) {
			waitingFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}
	
	public static void startWaiting(JDialog waitingDialog) {
		if (waitingDialog != null) {
			waitingDialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}
	
	public static void stopWaiting(JFrame waitingFrame) {
		if (waitingFrame != null) {
			waitingFrame.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public static void stopWaiting(JDialog waitingDialog) {
		if (waitingDialog != null) {
			waitingDialog.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public static boolean showSureRequest(String actionToRequest, Component parent) {
		String message = "<HTML>" + 
				Messages.getString(Messages.YOU_EXECUTE_THE_FOLLOWING_ACTIVITY) + 
				"<BR>" +
				actionToRequest;
		int userAnswer = JOptionPane.showConfirmDialog(parent, message);
		if (userAnswer == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
	}

}
