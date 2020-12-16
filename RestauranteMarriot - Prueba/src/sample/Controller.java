package sample;

import Model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable, Observer {

    @FXML
    public AnchorPane layout;

    @FXML
    public Rectangle ledOrden;


    static int numClientes = 40;
    int cupoReservaciones = 2;
    int cupoRestaurante =10;
    int numMeseros = 1;
    int numCocineros = 1;

    public static Circle[] clientesArray = new Circle[numClientes];
    public static Circle[] meserosArray = new Circle[1];
    public static Circle[] cocinerosArray = new Circle[1];


    DisponibilidadMesero disponibilidadMesero = new DisponibilidadMesero(numMeseros);
    Recepcionista recepcionista = new Recepcionista(cupoRestaurante, cupoReservaciones);
    MonitorMesero monitorMesero = new MonitorMesero(numMeseros, disponibilidadMesero);
    CocineroMonitor cocineroMonitor = new CocineroMonitor(numCocineros);

    int xx = 54, y = 400;//clientes afuera
    int ex = 700, ey = 560;//mesero
    int cx = 437, cy = 716;//cocinero

    Mesero[] meseros = new Mesero[numMeseros];
    Cocinero[] cocineros = new Cocinero[numCocineros];

    Cliente[] clientes;
    Thread hilo;
    boolean access;

    Color[] ballColors = {Color.BLUE, Color.RED, Color.GREEN, Color.PURPLE, Color.GOLD, Color.YELLOW, Color.ORANGE,
            Color.GRAY, Color.AZURE, Color.LIGHTCYAN, Color.AQUA, Color.WHITE, Color.CHOCOLATE, Color.PALEGOLDENROD, Color.KHAKI,
            Color.VIOLET, Color.THISTLE, Color.TURQUOISE, Color.SKYBLUE, Color.STEELBLUE, Color.SALMON, Color.SEASHELL, Color.MISTYROSE,
            Color.OLIVE, Color.LIGHTPINK, Color.BLUE, Color.RED, Color.GREEN, Color.PURPLE, Color.GOLD, Color.YELLOW, Color.ORANGE,
            Color.GRAY, Color.AZURE, Color.LIGHTCYAN, Color.AQUA, Color.WHITE, Color.CHOCOLATE, Color.PALEGOLDENROD, Color.KHAKI,
            Color.VIOLET, Color.THISTLE, Color.TURQUOISE, Color.SKYBLUE, Color.STEELBLUE, Color.SALMON, Color.SEASHELL, Color.MISTYROSE,
            Color.OLIVE, Color.LIGHTPINK};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image img = new Image(getClass().getResourceAsStream("images/waitress.png"));
        Image img1 = new Image(getClass().getResourceAsStream("images/chef.png"));
        Image img2 = new Image(getClass().getResourceAsStream("images/chef.png"));


        meserosArray[0] = new Circle(ex, ey, 25, ballColors[0]);
       // meserosArray[1] = new Circle(ex + 98, ey, 25, ballColors[3]);
       // meserosArray[2] = new Circle(ex + 190, ey, 25, ballColors[10]);

        for (int i = 0; i < meserosArray.length; i++ ) {
            meserosArray[i].setFill(new ImagePattern(img));
            layout.getChildren().add(meserosArray[i]);
        }

        cocinerosArray[0] = new Circle(cx, cy, 23, Color.RED);
        //cocinerosArray[1] = new Circle(cx + 98, cy, 23, Color.BLACK);
        //cocinerosArray[2] = new Circle(cx + 190, cy, 23, Color.GREEN);

        for (int i = 0; i < cocinerosArray.length; i++ ) {
            cocinerosArray[i].setFill(new ImagePattern(img1));
            layout.getChildren().add(cocinerosArray[i]);
        }
    }

    @FXML
    public void startRestaurant() {
        //Image clients = new Image(getClass().getResourceAsStream("images/user.png"));

        clientes = new Cliente[numClientes];
        for (int x = 0; x < numClientes; x++) {
            clientes[x] = new Cliente(x, recepcionista, meseros, monitorMesero, xx, y);
            hilo = new Thread(clientes[x]);
            clientes[x].addObserver(this);
            hilo.setDaemon(true);
            hilo.start();

            Circle clienteV = new Circle(xx, y, 30, ballColors[x]);
            clientesArray[x] = clienteV;
            //clientesArray[x].setFill(new ImagePattern(clients));

            layout.getChildren().add(clienteV);

        }

        for (int x = 0; x < numMeseros; x++) {
            meseros[x] = new Mesero(cocineroMonitor, cocineros, x, clientes, monitorMesero, ex, ey);
            hilo = new Thread(meseros[x]);
            hilo.setDaemon(true);
            hilo.start();
        }

        for (int x = 0; x < numCocineros; x++) {
            cocineros[x] = new Cocinero(meseros, cocineroMonitor);
            hilo = new Thread(cocineros[x]);
            hilo.setDaemon(true);
            hilo.start();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {

            if (o instanceof Cliente) {

                if (((Cliente) o).getIdMesa() == 0) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0") && ((Cliente) o).getIdMesa() == 0) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        Timer timer;
                        timer = new Timer();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {

                                    //RUTA 01
                                    if (clientesArray[((Cliente) o).getIdCliente()].getCenterY() >= 23 && (clientesArray[((Cliente) o).getIdCliente()].getCenterY() <= 392) && !access) {
                                        ((Cliente) o).setEjeY(((Cliente) o).getEjeY() + 40);
                                        clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                                    }


                                    //RUTA 02
                                    if (clientesArray[((Cliente) o).getIdCliente()].getCenterY() >= 382 && (clientesArray[((Cliente) o).getIdCliente()].getCenterX() <= 190)) {
                                        ((Cliente) o).setEjeX(((Cliente) o).getEjeX() + 5);
                                        clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                                    }

                                    //RUTA 03
                                    if (clientesArray[((Cliente) o).getIdCliente()].getCenterX() >= 180 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() >= 140 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() <= 400 && clientesArray[((Cliente) o).getIdCliente()].getCenterX() <= 205 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() != 227 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() != 354) {
                                        access = true;
                                        ((Cliente) o).setEjeY(((Cliente) o).getEjeY() - 5);
                                        clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                                    }

                                    //RUTA 04
                                    if (clientesArray[((Cliente) o).getIdCliente()].getCenterX() >= 180 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() >= 135 && clientesArray[((Cliente) o).getIdCliente()].getCenterY() <= 155 && clientesArray[((Cliente) o).getIdCliente()].getCenterX() <= 1260) {
                                        access = true;
                                        ((Cliente) o).setEjeX(((Cliente) o).getEjeX() + 5);
                                        clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());

                                        //CONTROLADORES DE POSICIONES DE MESAS

                                        if (clientesArray[((Cliente) o).getIdCliente()].getCenterX() >= 460 && clientesArray[((Cliente) o).getIdCliente()].getCenterX() <= 475) {
                                            ((Cliente) o).setEjeY(((Cliente) o).getEjeY() - 62);
                                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                                        }
                                    }
                                }
                            }
                        };
                        timer.schedule(task, 0, 25);

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(150);
                        ((Cliente) o).setEjeX(463);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(150);
                            ((Cliente) o).setEjeX(463);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 1) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {


                            ((Cliente) o).setEjeY(384);
                            ((Cliente) o).setEjeX(190);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                            //Task


                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(150);
                        ((Cliente) o).setEjeX(650);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(150);
                            ((Cliente) o).setEjeX(463);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 2) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                            ((Cliente) o).setEjeY(384);
                            ((Cliente) o).setEjeX(190);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(150);
                        ((Cliente) o).setEjeX(853);

                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(150);
                            ((Cliente) o).setEjeX(463);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                        clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                        clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                    }
                }

                if (((Cliente) o).getIdMesa() == 3) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {


                            ((Cliente) o).setEjeY(384);
                            ((Cliente) o).setEjeX(190);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(150);
                        ((Cliente) o).setEjeX(1090);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(150);
                            ((Cliente) o).setEjeX(463);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 4) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {


                            ((Cliente) o).setEjeY(384);
                            ((Cliente) o).setEjeX(190);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());

                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(150);
                        ((Cliente) o).setEjeX(1300);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(150);
                            ((Cliente) o).setEjeX(463);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 5) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {


                            ((Cliente) o).setEjeY(420);
                            ((Cliente) o).setEjeX(480);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(400);
                        ((Cliente) o).setEjeX(480);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(480);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 6) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                            ((Cliente) o).setEjeY(384);
                            ((Cliente) o).setEjeX(190);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(400);
                        ((Cliente) o).setEjeX(650);

                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(480);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }


                // -------------------------------------------------------

                if (((Cliente) o).getIdMesa() == 7) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {

                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(853);
                            clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                            clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());

                        }

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(400);
                        ((Cliente) o).setEjeX(853);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(480);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3") && ((Cliente) o).getIdMesa() == 0) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }


                if (((Cliente) o).getIdMesa() == 8) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        Timer timer;
                        timer = new Timer();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {


                                    ((Cliente) o).setEjeY(400);
                                    ((Cliente) o).setEjeX(853);
                                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                                }
                            }
                        };
                        timer.schedule(task, 0, 7);

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(400);
                        ((Cliente) o).setEjeX(1090);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(480);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }

                if (((Cliente) o).getIdMesa() == 9) {

                    if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                        System.out.println("CLIENTE: ==================> " + ((Cliente) o).getIdCliente() + " SALIENDO DEL RESTAURANTE =================> ");
                        /*((Cliente) o).setEjeY(24);
                        ((Cliente) o).setEjeX(190);*/


                        Timer timer;
                        timer = new Timer();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (((Cliente) o).getEstatusAnimacion()[0].equals("0")) {
                                    ((Cliente) o).setEjeY(400);
                                    ((Cliente) o).setEjeX(953);
                                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                                }
                            }
                        };
                        timer.schedule(task, 0, 7);

                    } else if (((Cliente) o).getEstatusAnimacion()[0].equals("1")) {
                        ((Cliente) o).setEjeY(400);
                        ((Cliente) o).setEjeX(1300);
                    } else  //FASE DE ORDENAR - SERVIR - RETIRARSE
                        if (((Cliente) o).getEstatusAnimacion()[0].equals("2")) {
                            ((Cliente) o).setEjeY(400);
                            ((Cliente) o).setEjeX(480);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("3")) {
                            ((Cliente) o).setEjeY(415);
                            ((Cliente) o).setEjeX(54);
                        } else if (((Cliente) o).getEstatusAnimacion()[0].equals("4")) {
                            clientesArray[((Cliente) o).getIdCliente()].setVisible(false);
                        }


                    clientesArray[((Cliente) o).getIdCliente()].setCenterY(((Cliente) o).getEjeY());
                    clientesArray[((Cliente) o).getIdCliente()].setCenterX(((Cliente) o).getEjeX());
                }




            }


        });
    }

    public static Circle[] getClientesArray() {
        return clientesArray;
    }

    public static void setClientesArray(Circle[] clientesArray) {
        Controller.clientesArray = clientesArray;
    }
}
