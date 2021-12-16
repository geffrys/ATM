package login;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import menu.Menu;

public class Account {
    private char passw;
    private int accNumber;
    // default constructor
    public Account(int accNumber, String passw,  int opc) {
        setPass(passw);
        this.accNumber = accNumber;
        if(opc == 1 && this.isAccountinlist()){
            new Menu(this.accNumber);
        }
        if(opc == 1 && !this.isAccountinlist()){
            JOptionPane.showMessageDialog(null, "El numero de cuenta no se encuentra en la base de datos, por favor registrese");
        }
        if(opc == 2){
            agregarCuenta();
        }
    }
    // aux constructor for file construction
    public Account(char passw, int accNumber) {
        this.passw = passw;
        this.accNumber = accNumber;
    }

    private void setPass(String passw) {
        char[] pass = passw.toCharArray();
        if (pass.length == 4) {
            boolean digit = true;
            for (int i = 0; i < pass.length; i++) {
                if (Character.isDigit(pass[i])) {
                    digit = true;
                } else {
                    digit = false;
                    break;
                }
            }
            if (digit == true) {
                this.passw = (char)Integer.parseInt(passw);
            } else {
                JOptionPane.showMessageDialog(null, "Contrasena invalida porfavor intente en otra oportunidad");
            }
        }
    }

    private void agregarCuenta() {
        File archivo = new File("src/login/AccountLists/AccList.dat");
        try (DataOutputStream salida = new DataOutputStream(
                new FileOutputStream(archivo, true))) {
            salida.writeInt(this.accNumber);
            salida.writeChar(this.passw);
            salida.writeUTF("\n");
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
    private ArrayList<Account> leerArchivo() {
        ArrayList<Account> lista = new ArrayList<>();
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(new File("src/login/AccountLists/AccList.dat")))) {
            while (true) {
                int acc = entrada.readInt();
                char passw = entrada.readChar();
                String line = entrada.readUTF(); //pues le pusimos un salto de linea al listado de cuentas para ver mas claramente las cosas
                System.out.println(line); // pa que esta basura escape xdd
                Account e = new Account(passw, acc);
                lista.add(e);
            }
        }
        //End of file exception 
        catch(EOFException exc){
            System.out.println("Se leyeron todos los archivos");
        }
        //La clase EOFException si se atrapa esta la de arriba ya habria sido realizada,
         catch (IOException e) {

        }
        return lista;
    
    }
    private boolean isAccountinlist(){
        ArrayList<Account> list = this.leerArchivo();
        for (Account account : list) {
            if(account.accNumber == this.accNumber){
                if(account.passw == this.passw){
                    return true;
                }
            }
        }
        return false;
    }

    public static int ingresaCuenta(){
        return Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el numero de cuenta"));
    }
    public static String ingresarContrasena(){
        return JOptionPane.showInputDialog(null, "Ingrese la contrasena(4 digitos)");
    }
}
