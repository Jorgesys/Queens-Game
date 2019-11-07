package queens;

/*
 * Jorgesys 2019
 */
import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.border.Border;

public class QueensGame extends JFrame implements ActionListener {

    static int reinas = 3;
    String[][] algoritmo = new String[reinas][reinas];
    String[][] comprobacion = new String[reinas][reinas];
    int count = 0;
    int anchoAlto = 50; //Alto celda
    int margen = 25; //Margen superior, donde se posiciona botón finalizar.
    int espacio = 50; //Ancho celda
    JPanel jpanel = (JPanel) this.getContentPane();
    JLabel ex = new JLabel();
    JLabel label[] = new JLabel[reinas];
    JLabel tablero[][] = new JLabel[reinas][reinas];
    Border border = BorderFactory.createLineBorder(Color.black, 1);

    String[] parts;
    int x, y, cooX, cooY, newI, newJ;

    JButton boton1;

    public static void main(String[] args) {
        reinas = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de reinas"));
        QueensGame op = new QueensGame();
        op.setBounds(0, 0, (60 * reinas), (60 * reinas));
        op.setVisible(true);
        op.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public QueensGame() {
        // Declaracion de reinas
        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            // r + r + r + r
            label[i].setBounds(margen + (espacio * i), margen, anchoAlto, anchoAlto);
            label[i].setText("Q" + (i + 1));
            label[i].setForeground(Color.red);
            label[i].setBorder(border);
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            label[i].addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent evt) {
                    arrastreReina(evt);
                }
            });
            label[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent evt) {
                    //System.out.println("mouseReleased " + evt.toString());
                    arregloTablero(evt);
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    //System.out.println("mousePressed " + evt.toString());
                    valoresIniciales(evt);
                }
            });
            jpanel.add(label[i]);

            for (int j = 0; j < algoritmo.length; j++) {
                if (i == 0) {
                    algoritmo[i][j] = Integer.toString((margen + (espacio * j))) + "|" + Integer.toString(margen);
                } else {
                    algoritmo[i][j] = "0|0";
                }
            }
        }

        // Declaracion del tablero
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = new JLabel();
                tablero[i][j].setBounds(margen + (espacio * i), margen + (espacio * j), anchoAlto, anchoAlto);
                tablero[i][j].setBorder(border);

                if ((i % 2 == 0) == (j % 2 == 0)) {
                    tablero[i][j].setBackground(Color.white);
                } else {
                    tablero[i][j].setBackground(Color.black);
                }

                tablero[i][j].setOpaque(true);
                tablero[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jpanel.add(tablero[i][j]);
            }
        }

        // Declaracion del boton
        setLayout(null);
        boton1 = new JButton("Finalizar");
        boton1.setBounds(25, 3, 90, 20);
        jpanel.add(boton1);
        boton1.addActionListener(this);

        ex.setBounds(margen, margen, anchoAlto, anchoAlto);
        jpanel.add(ex);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton1) {
            for (int i = 0; i < comprobacion.length; i++) {
                for (int j = 0; j < comprobacion.length; j++) {
                    if (algoritmo[i][j].equals("0|0")) {
                        comprobacion[i][j] = "*";
                    } else {
                        comprobacion[i][j] = "+";
                    }
                }
            }

            imprimirArreglo(comprobacion);

            int contUno = 0;
            int contDos = 0;
            int contTres = 0;

            int diagS, diagI;

            for (int i = 0; i < comprobacion.length; i++) {
                for (int j = 0; j < comprobacion.length; j++) {
                    // Comprobacion de filas
                    if (comprobacion[i][j].equals("+")) {
                        contUno++;
                        if (contUno == 2) {
                            JOptionPane.showMessageDialog(null, "Existe más de una reina en una fila");
                            break;
                        }
                    }

                    // Comprobacion de columnas
                    if (comprobacion[j][i].equals("+")) {
                        contDos++;
                        if (contDos == 2) {
                            JOptionPane.showMessageDialog(null, "Existe más de una reina en una columna");
                            break;
                        }

                        // Comprobacion de diagonales
                        diagS = j - 1;
                        diagI = j + 1;

                        if (diagS < 0) {
                            diagS = 0;
                        } else if (diagI >= comprobacion.length) {
                            diagI = (comprobacion.length - 1);
                        }

                        System.out.println("S/I: " + diagS + "/" + diagI);

                        if (i < comprobacion.length - 1) {
                            if (comprobacion[diagS][i + 1].equals("+") || comprobacion[diagI][i + 1].equals("+")) {
                                JOptionPane.showMessageDialog(null, "En alguna diagonal de las reinas existe mas de una reina");
                                contTres = 2;
                                break;
                            }
                        }
                    }
                }
                if (contUno == 2 || contDos == 2 || contTres == 2) {
                    break;
                }
                contUno = 0;
                contDos = 0;
                contTres = 0;
            }

            if (contUno == 0 && contDos == 0 && contTres == 0) {
                JOptionPane.showMessageDialog(null, "Felicidades, completaste el juego de N Reinas");
            }
        }
    }

    public void arrastreReina(MouseEvent evt) {
        if (evt.getSource() instanceof JLabel) {
            ((JLabel) evt.getSource()).setLocation(posicion(evt)[0], posicion(evt)[1]);
        }
    }

    public void valoresIniciales(MouseEvent evt) {
        if (evt.getSource() instanceof JLabel) {
            this.newI = (posicion(evt)[0] - 25) / 50;
            this.newJ = (posicion(evt)[1] - 25) / 50;

            x = posicion(evt)[0];
            y = posicion(evt)[1];
            System.out.println("* initial values  X/Y: " + this.newI + "/" + this.newJ);

            //Reemplaza valor inicial con "0|0"
            algoritmo[newJ][newI] = "0|0";
            imprimirArreglo(algoritmo);
        }
    }

    //Posibles valores en array.
    // 25/25  25/75  25/125
    // 25/75  75/75  125/75
    // 25/125 75/125 125/125
    public void arregloTablero(MouseEvent evt) {
        if (evt.getSource() instanceof JLabel) {

            int[] posicion = posicion(evt);
            int newX = posicion(evt)[0];
            int newY = posicion(evt)[1];

            newI = (newX - 25) / 50;
            newJ = (newY - 25) / 50;

            System.out.println("* final values newI/newJ: " + newI + "/" + newJ);

            //Reemplaza con valor de posición.
            algoritmo[newJ][newI] = newX + "|" + newY;

            count++;
            System.out.println("Movimientos: " + count);
            imprimirArreglo(algoritmo);
        }
    }

    public void imprimirArreglo(String[][] arreglo) {
        System.out.println("/------------/");
        for (String[] elemento : arreglo) {
            for (int j = 0; j < arreglo.length; j++) {
                System.out.print(elemento[j] + "    ");
            }
            System.out.println("");
        }
        System.out.println("/------------/");
    }

    public int[] posicion(MouseEvent evt) {
        JLabel label = (JLabel) evt.getSource();
        Point labelLocation = label.getLocation();
        int newX = labelLocation.x + evt.getX();
        int newY = labelLocation.y + evt.getY();

        newX = Math.min(Math.max(0, ((newX - margen) / espacio)), reinas - 1);
        newY = Math.min(Math.max(0, ((newY - margen) / espacio)), reinas - 1);
        newX = (newX * espacio) + margen;
        newY = (newY * espacio) + margen;

        int retorno[] = {newX, newY};
        return retorno;

    }

}
