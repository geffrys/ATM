package menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class Menu {
    private String filename;

    public Menu(int acc) {
        int opc;
        this.filename = "src/menu/InformacionCuentas/" + acc + ".txt";
        boolean flag = true;
        while (true && flag != false) {
            try {
                opc = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Escoge la accion que desees realizar\n\n1. billetera. \n2. Depositar\n3. Retirar\n4. Transferir dinero\n5. Salir"));
                switch (opc) {
                    case 1:
                        if (leerArchivoListados() == null) {
                            JOptionPane.showMessageDialog(null, "Cuenta sin fondos\n0$");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "El saldo de la cuenta es: \n" + leerArchivoListados() + "$");
                        }
                        break;
                    case 2:
                        ingresarDinero(JOptionPane.showInputDialog(null, "La cantidad de dinero a ingresar"));
                        JOptionPane.showMessageDialog(null,
                                "El saldo de la cuenta es: \n" + leerArchivoListados() + "$");
                        break;
                    case 3:
                        retirarDinero(JOptionPane.showInputDialog(null, "La cantidad de dinero a retirar"));
                        JOptionPane.showMessageDialog(null,
                                "El saldo de la cuenta es: \n" + leerArchivoListados() + "$");
                        break;
                    case 4:
                        String accTrans = "src/menu/InformacionCuentas/" + JOptionPane.showInputDialog(null,
                                "Escriba el numero de la cuenta a realizar la transferencia") + ".txt";
                        transferirDinero(
                                JOptionPane.showInputDialog(null, "Escriba el monto a transferir"),
                                accTrans);
                        JOptionPane.showMessageDialog(null,
                                "El saldo de la cuenta es: \n" + leerArchivoListados() + "$");
                        break;
                    case default:
                        JOptionPane.showMessageDialog(null, "Gracias!");
                        flag = false;
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese una opcion valida");
            }

        }
    }

    private String leerArchivoListados() {
        String renglon = null;
        try (BufferedReader entrada = new BufferedReader(new FileReader(this.filename))) {
            renglon = entrada.readLine();
        } catch (Exception e) {

        }
        return renglon;
    }

    private String leerArchivoListados(String fileName) {
        String renglon = null;
        try (BufferedReader entrada = new BufferedReader(new FileReader(fileName))) {
            renglon = entrada.readLine();
        } catch (Exception e) {

        }
        return renglon;
    }

    private void ingresarDinero(String deposit) {
        File archivo = new File(this.filename);
        String concat = "";
        if (leerArchivoListados() != null) {
            concat += (Integer.parseInt(leerArchivoListados()) + Integer.parseInt(deposit));
        } else {
            concat = deposit;
        }
        try (FileWriter output = new FileWriter(archivo, false)) {
            output.write(concat);
        } catch (Exception e) {

        }
    }

    private boolean retirarDinero(String retire) {
        File archivo = new File(this.filename);
        String concat = "";
        if (leerArchivoListados() != null) {
            if (Integer.parseInt(leerArchivoListados()) - Integer.parseInt(retire) >= 0) {
                concat += (Integer.parseInt(leerArchivoListados()) - Integer.parseInt(retire));
            } else {
                JOptionPane.showMessageDialog(null, "La cantidad supera los fondos poseidos en la cuenta");
                return false;
            }

        } else {
            concat = retire;
        }
        try (FileWriter output = new FileWriter(archivo, false)) {
            output.write(concat);
        } catch (Exception e) {

        }
        return true;
    }

    private boolean transferirDinero(String deposit, String AccNum) {
        File archivo = new File(AccNum);
        String montoOriginal = leerArchivoListados();
        String concat2 = ""; // para poner la diferencia;
        String concat = "";
        boolean isAccepted = this.retirarDinero(deposit); // retiramos el dinero de la cuenta
        if (isAccepted) {
            if (archivo.exists()) {
                if (leerArchivoListados(AccNum) != null) {
                    concat += (Integer.parseInt(leerArchivoListados(AccNum)) + Integer.parseInt(deposit));
                } else {
                    concat = deposit;
                }
                try (FileWriter output1 = new FileWriter(archivo, false)) {
                    output1.write(concat);
                } catch (Exception e) {

                }
                concat2 += Integer.parseInt(montoOriginal) - Integer.parseInt(deposit);
                try (FileWriter output = new FileWriter(this.filename, false)) {
                    output.write(concat2);
                } catch (Exception e) {

                }
            } else {
                retornarDinero(montoOriginal);
                JOptionPane.showMessageDialog(null, "La cuenta ingresada no existe o no ha realizado operaciones");
            }
        } else {
            retornarDinero(montoOriginal); // retorna el dinero a la cuenta
            JOptionPane.showMessageDialog(null, "No fue aceptada la transferencia");
            return false;
        }
        return true;
    }

    private void retornarDinero(String deposit) {
        File archivo = new File(this.filename);
        String concat = "";

        concat = deposit;

        try (FileWriter output = new FileWriter(archivo, false)) {
            output.write(concat);
        } catch (Exception e) {

        }
    }
}
