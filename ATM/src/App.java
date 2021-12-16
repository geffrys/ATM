
import javax.swing.JOptionPane;

import login.*;

public class App {
    public static void main(String[] args){
        menu();
    }
    public static void menu(){
        int opc;
        JOptionPane.showMessageDialog(null, "Bienvenido al ATM");
        boolean flag = true;
        while(true && flag != false){
            try {
                opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Escoge la accion que desees realizar\n\n1. Ingresar a cuenta. \n2. Registrar cuenta.\n3. Salir\n\n"));
            switch(opc){
                case 1:
                    new Account(Account.ingresaCuenta(), Account.ingresarContrasena(),opc);
                    break;
                case 2:
                    new Account(Account.ingresaCuenta(), Account.ingresarContrasena(),opc);
                    break;
                case default:
                    JOptionPane.showMessageDialog(null,"Gracias!");
                    flag = false;
                    break;
            }
            } catch (NumberFormatException x) {
                JOptionPane.showMessageDialog(null, "Digite una opcion");
                continue;
            }
        }
    }
}
