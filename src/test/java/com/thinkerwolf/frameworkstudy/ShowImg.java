package com.thinkerwolf.frameworkstudy;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class ShowImg extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public ShowImg(URL url) {
		JPanel panel = new JPanel(new BorderLayout());
		ImageIcon ii = new ImageIcon(url);
		JLabel label2 = new JLabel(ii);
		panel.add(label2, BorderLayout.CENTER);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);

		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("显示图像");
		this.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			ShowImg showImage = new ShowImg(new URL("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
