package es.joseantoniofornellmarquez.juego_fornell;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX App
 */
public class App extends Application {
//Constantes del juego
//Constantes del tamaño de la ventana
final int ESCENA_TAM_X = 800;
final int ESCENA_TAM_Y = 600;
//Variables del juego
//Variable root
Pane root;
//Variable para el grupo
Group grupoCuerpo;
//Velocidad del movimiento del personaje
int posicionPerro = 0;
int movimientoFondo = 0;
int movimientoFondo2 = 800;
//Posicion inicial del personaje
int posicionY = 470;
int posicionX = 20;
//Movimiento del personaje en salto
int movimientoY = 0;

    @Override
    public void start(Stage stage) {
        root = new Pane();
        Scene scene = new Scene(root, ESCENA_TAM_X, ESCENA_TAM_Y);
        scene.setFill(Color.BLACK);
        stage.setTitle("Casper Y Mia");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //Fondo del juego
        Image fondo = new Image(getClass().getResourceAsStream("/images/fondo.png"));
        ImageView fondoVisto = new ImageView(fondo);
        ImageView fondoVisto2 = new ImageView(fondo);
        fondoVisto.setLayoutX(0);
        fondoVisto.setLayoutY(0);
        fondoVisto2.setLayoutX(ESCENA_TAM_X);
        fondoVisto2.setLayoutY(0);
        root.getChildren().add(fondoVisto);
        root.getChildren().add(fondoVisto2);
        //Cuerpo del personaje
        grupoCuerpo = new Group();
        Rectangle rectTorso = new Rectangle(0,0,80,30);
        Rectangle rectPataDel = new Rectangle(60,20,10,30);
        Rectangle rectPataTra = new Rectangle(10,20,10,30);
        Rectangle cola = new Rectangle(-5,-10,5,20);
        Circle cabeza = new Circle(80,5,17);
        Rectangle orejas = new Rectangle (65,-20,15,20);
        Rectangle boca = new Rectangle (80,0,15,25);
        Circle ojo = new Circle(88,5,5);
        //Propiedades de las partes del cuerpo
        cola.setRotate(-30);
        cola.setArcHeight(20);
        cola.setArcWidth(20);
        rectTorso.setArcHeight(20);
        rectTorso.setArcWidth(20);
        rectPataDel.setArcHeight(20);
        rectPataDel.setArcWidth(20);
        rectPataTra.setArcHeight(20);
        rectPataTra.setArcWidth(20);
        orejas.setArcHeight(20);
        orejas.setArcWidth(50);
        orejas.setRotate(-20);
        boca.setArcHeight(10);
        boca.setArcWidth(15);
        boca.setRotate(-50);
        //Color del personaje
        rectTorso.setFill(Color.rgb(147,81,22));
        rectPataDel.setFill(Color.rgb(147,81,22));
        rectPataTra.setFill(Color.rgb(147,81,22));
        cola.setFill(Color.rgb(147,81,22));
        cabeza.setFill(Color.rgb(147,81,22));
        orejas.setFill(Color.rgb(147,81,22));
        boca.setFill(Color.rgb(147,81,22));
        ojo.setFill(Color.BLACK);
        //Agrupar partes del personaje
        grupoCuerpo.getChildren().add(rectTorso);
        grupoCuerpo.getChildren().add(rectPataDel);
        grupoCuerpo.getChildren().add(rectPataTra);
        grupoCuerpo.getChildren().add(cola);
        grupoCuerpo.getChildren().add(cabeza);
        grupoCuerpo.getChildren().add(orejas);
        grupoCuerpo.getChildren().add(boca);
        grupoCuerpo.getChildren().add(ojo);
        //Colocación del personaje en la pantalla
        grupoCuerpo.setLayoutX(posicionX);
        grupoCuerpo.setLayoutY(posicionY);
        //Agrupar el cuerpo del personaje
        root.getChildren().add(grupoCuerpo);
        //Teclas para el movimiento del personaje
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case LEFT:
                    posicionPerro -= 5;
                    movimientoFondo += 4;
                    movimientoFondo2 += 4;
                    break;
                case RIGHT:
                    posicionPerro += 5;
                    movimientoFondo -= 4;
                    movimientoFondo2 -= 4;
                    break;
                case SPACE:
                    movimientoY = -5;
                    break;
            }
        });
        //Código para lla animación del juego
        Timeline tiempoAnimacion = new Timeline(
                new KeyFrame(Duration.seconds(0.01), (ActionEvent ae) -> {
                   
                    grupoCuerpo.setLayoutX(posicionPerro);
                    fondoVisto.setLayoutX(movimientoFondo);
                    fondoVisto2.setLayoutX(movimientoFondo2);
                    if (movimientoFondo <= -ESCENA_TAM_X){
                        movimientoFondo = ESCENA_TAM_X;
                        fondoVisto.setLayoutX(movimientoFondo);
                    }
                    if (movimientoFondo2 <= -ESCENA_TAM_X){
                        movimientoFondo2 = ESCENA_TAM_X;
                        fondoVisto2.setLayoutX(movimientoFondo2);
                    }
                    posicionY += movimientoY;
                    //Si ha llegado a arriba
                    if (posicionY <= 350){
                        movimientoY = +5;
                    }
                    //Si ha llegado al suelo
                    if (posicionY >= 470){
                        movimientoY = 0;
                    }
                    grupoCuerpo.setLayoutY(posicionY);
                    System.out.println(posicionY);
                })
        );
        tiempoAnimacion.setCycleCount(Timeline.INDEFINITE);
        tiempoAnimacion.play();
    }

    public static void main(String[] args) {
        launch();
    }

}