package minesweeper.swingui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import minesweeper.BestTimes;
import minesweeper.Minesweeper;

public class BestTimesDialog extends JDialog {

	private BestTimes bestTimes;
	private JTextArea textArea;

	public BestTimesDialog() {
		this.setName("Best times");
		setLocationRelativeTo(this);
		getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 206);
		getContentPane().add(textArea);
		textArea.setColumns(10);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(362, 228, 62, 23);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

			}
		});
		bestTimes = Minesweeper.getInstance().getBestTimes();
		textArea.setText(bestTimes.toString());
	}

	public BestTimesDialog(ActionListener actionListener, boolean b) {
		new BestTimesDialog();
	}
}
