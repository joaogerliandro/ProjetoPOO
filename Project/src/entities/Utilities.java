package entities;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public final class Utilities
{
	public static void ShowPopup(String text, String title, int type)
	{
		JOptionPane.showMessageDialog(null, text, "ZéBigod's " + title, type);
	}

	public static void ShowPopup(String text, int type)
	{
		JOptionPane.showMessageDialog(null, text, "", type);
	}

	public static void ShowPopupInfo(String text)
	{
		JOptionPane.showMessageDialog(null, text, "ZéBigod's Information", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void ShowPopupWarn(String text)
	{
		JOptionPane.showMessageDialog(null, text, "ZéBigod's Warning", JOptionPane.WARNING_MESSAGE);
	}

	public static void ShowPopupError(String text)
	{
		JOptionPane.showMessageDialog(null, text, "ZéBigod's Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void CentralizeAllTableCells(JTable table)
	{
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        for (int columnIndex = 0; columnIndex < table.getModel().getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(render);
        }
	}

}
