import java.awt.FileDialog;
import java.awt.Frame;




public class Test_FileDialog
{
	public static void main(String[] args) {
	FileDialog d= new FileDialog(new Frame(), "Ouvrir", FileDialog.LOAD);
	d.setVisible(true);
   }
}
