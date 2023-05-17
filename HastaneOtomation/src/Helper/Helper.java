package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {

	
public static void optionPaneChangeButtonText() {
	UIManager.put("OptionPane.cancelButtonText", "İptal");
	UIManager.put("OptionPane.yesButtonText", "Evet");
	UIManager.put("OptionPane.noButtonText", "Hayır");
	UIManager.put("OptionPane.okButtonText", "Tamam");
}
	
public static void showMsg(String str) {
	optionPaneChangeButtonText();
	String msg;
	switch(str) {
	case "fill":
		msg = "Tüm Alanları Doldurduğunuzdan Emin Olunuz!";
		break;
	case "success":
		msg="İşlem Başarılı!";
		break;
	case "error":
		msg = "İşlem Gerçekleşmedi! HATA!";
		break;
	default:
		msg = str;
		break;
	}
	JOptionPane.showMessageDialog(null, msg,"Mesaj",JOptionPane.INFORMATION_MESSAGE);
	
}

public static boolean confirm(String str) {
	optionPaneChangeButtonText();
	String msg;
	switch(str) {
	case "sure":
		msg = "Bu İşlemi Yapmak İstediğinize Emin Misiniz? Yaptığınız İşlemin Geri Dönüşü Yoktur!";
		break;
	default:
		msg = str;
		break;
	}
	int res = JOptionPane.showConfirmDialog(null, msg,"DİKKAT!",JOptionPane.YES_NO_OPTION);
	
	if(res == 0) {
		return true;
	}else {
		return false;
	}
}
}
