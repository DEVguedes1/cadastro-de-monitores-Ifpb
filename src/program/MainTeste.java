package program;

import controller.LoginController;
import view.LoginView;

public class MainTeste {

    public static void main(String[] args) {
    	LoginView tela = new LoginView();     
    	tela.setVisible(true);
    	new LoginController(tela);
        
    }
}