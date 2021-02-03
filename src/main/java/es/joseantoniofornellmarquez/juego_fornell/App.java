package es.joseantoniofornellmarquez.juego_fornell;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
//Variables polilineas
Polyline contactoMosca;
Polyline contactoPerro;
Polyline contactoPinchos;
Polyline contactoMosca3;
//Variable para el grupo
Group grupoCuerpo;
Group grupoContactoPerro;
Group grupoContactoMosca;
Group grupoContactoMosca3;
Group grupoContactoPinchos;
boolean visible= false;
//Variables para las colisiones
//Shape colisionMosca;
//int colisionMosca1;
//Velocidad del movimiento del personaje
int movimientoPerro = 0;
//Posicion del fondo
int movimientoFondo = 0;
int movimientoFondo2 = 800;
//Variables del diseño del personaje
Rectangle rectPataDel;
Rectangle rectPataTra;
//Posicion inicial del personaje
int posicionY = 470;
int posicionX = 20;
//Variables para detectar las teclas
boolean derecha = false;
//Movimiento del personaje en el salto
int movimientoY = 0;
//Contador 
int contador = 0;
//Enemigos
int enemigoPosicionX1;
int enemigoPosicionY1;
int enemigoPosicionX2;
int enemigoPosicionY2;
int enemigoPosicionX3;
int enemigoPosicionY3;
//Variables para la imagen de los enemigos
ImageView mosca1;
ImageView pinchos1;
ImageView mosca3;
//Velocidad de enemigo
int velocidadMosca = -2;
int velocidadPinchos = -1;
//Objeto rando para las posiciones de los enemigos
Random random = new Random();
//Variable tamaño textos
final int TEXT_SIZE = 24;

    @Override
    public void start(Stage stage) {
        //Panel donde se va motras todo el juego
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
        //Imagen de los enemigos
        Image moscaArriba = new Image(getClass().getResourceAsStream("/images/fly1.png"));
        Image moscaAbajo = new Image(getClass().getResourceAsStream("/images/fly2.png"));
        Image pinchos = new Image(getClass().getResourceAsStream("/images/pichos.png"));
        mosca1 = new ImageView();
        pinchos1 = new ImageView();
        mosca3 = new ImageView();
        root.getChildren().add(mosca1);
        root.getChildren().add(pinchos1);
        root.getChildren().add(mosca3);
        //Posicion aletoria del enemigo
        enemigoPosicionX1 = random.nextInt(100) + 800;
        enemigoPosicionY1 = random.nextInt(50) + 400;
        enemigoPosicionX2 = random.nextInt(100) + 1500;
        enemigoPosicionY2 = 470;
        enemigoPosicionX3 = random.nextInt(100) + 2200;
        enemigoPosicionY3 = random.nextInt(50) + 400;
        //Llamada al metodo del personaje
        diseñoPersonaje();
        //LLamada al metodo de los textos
        textosJuego();
        //Teclas para el movimiento del personaje
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case LEFT:
                    movimientoPerro = -5;
                    if (posicionX <= 10){
                        movimientoPerro = 0;
                    }
                    if (posicionX/5%2==0){
                        rectPataDel.setRotate(-30);
                        rectPataTra.setRotate(-30);
                    }
                    if (posicionX/5%2==1){
                        rectPataDel.setRotate(30);
                        rectPataTra.setRotate(30);
                    }
                    break;
                case RIGHT:
                    if (posicionX/5%2==0){
                        rectPataDel.setRotate(30);
                        rectPataTra.setRotate(30);
                    }
                    if (posicionX/5%2==1){
                        rectPataDel.setRotate(-30);
                        rectPataTra.setRotate(-30);
                    }
                    movimientoPerro = 5;
                    movimientoFondo -= 5;
                    movimientoFondo2 -= 5;
                    derecha = true;
                    break;
                case SPACE:
                    movimientoY = -10;

                    break;
                case ESCAPE:
                    reinicioJuego();
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            movimientoPerro = 0;
            rectPataDel.setRotate(0);
            rectPataTra.setRotate(0);
            derecha = false;
        });
        //Código para lla animación del juego
        Timeline tiempoAnimacion = new Timeline(
                new KeyFrame(Duration.seconds(0.01), (ActionEvent ae) -> {
                   
                    //Movimiento del fondo
                    if (movimientoFondo <= -ESCENA_TAM_X){
                        movimientoFondo = ESCENA_TAM_X;
                        fondoVisto.setLayoutX(movimientoFondo);
                    }
                    if (movimientoFondo2 <= -ESCENA_TAM_X){
                        movimientoFondo2 = ESCENA_TAM_X;
                        fondoVisto2.setLayoutX(movimientoFondo2);
                    }
                    //Movimiento del salto
                    posicionY += movimientoY;
                    //Si ha llegado a arriba
                    if (posicionY <= 300){
                        movimientoY = +3;
                    }
                    //Si ha llegado al suelo
                    if (posicionY >= 470){
                        movimientoY = 0;
                    }
                    //Movimiento del perro
                    posicionX += movimientoPerro;
                    if (posicionX <= 20){
                        movimientoPerro = 0;
                    }
                    limiteMovimiento();
                    //Movimiento moscas
                    grupoContactoMosca.setLayoutX(enemigoPosicionX1);
                    grupoContactoMosca.setLayoutY(enemigoPosicionY1);
                    grupoContactoPinchos.setLayoutX(enemigoPosicionX2);
                    grupoContactoPinchos.setLayoutY(enemigoPosicionY2);
                    grupoContactoMosca3.setLayoutX(enemigoPosicionX3);
                    grupoContactoMosca3.setLayoutY(enemigoPosicionY3);
                    enemigoPosicionX1 += velocidadMosca;
                    enemigoPosicionX2 += velocidadPinchos;
                    enemigoPosicionX3 += velocidadMosca;
                    if (enemigoPosicionX1/5%2==0){
                        mosca1.setImage(moscaArriba);

                    }
                    if (enemigoPosicionX1/5%2==1){
                        mosca1.setImage(moscaAbajo);

                    }
                    if (enemigoPosicionX1 <= -ESCENA_TAM_X){
                        enemigoPosicionX1 = ESCENA_TAM_X;
                        grupoContactoMosca.setLayoutX(enemigoPosicionX1);
                        enemigoPosicionX1 = random.nextInt(100) + 800;
                        enemigoPosicionY1 = random.nextInt(50) + 400;
                    }
                    if (enemigoPosicionX2/5%2==0){
                        pinchos1.setImage(pinchos);

                    }
                    if (enemigoPosicionX2/5%2==1){
                        pinchos1.setImage(pinchos);
                    }
                    if (enemigoPosicionX2 <= -ESCENA_TAM_X){
                        enemigoPosicionX2 = ESCENA_TAM_X;
                        grupoContactoPinchos.setLayoutX(enemigoPosicionX2);
                        enemigoPosicionX2 = random.nextInt(100) + 1500;
                        enemigoPosicionY2 = 470;
                    }
                    if (enemigoPosicionX3/5%2==0){
                        mosca3.setImage(moscaArriba);
                    }
                    if (enemigoPosicionX3/5%2==1){
                        mosca3.setImage(moscaAbajo);
                    }
                    if (enemigoPosicionX3 <= -ESCENA_TAM_X){
                        enemigoPosicionX3 = ESCENA_TAM_X;
                        grupoContactoMosca3.setLayoutX(enemigoPosicionX3);
                        enemigoPosicionX3 = random.nextInt(100) + 2200;
                        enemigoPosicionY3 = random.nextInt(50) + 400;
                    }
                    fondoVisto.setLayoutX(movimientoFondo);
                    fondoVisto2.setLayoutX(movimientoFondo2);
                    grupoContactoPerro.setLayoutY(posicionY);
                    grupoContactoPerro.setLayoutX(posicionX);
                    System.out.println(posicionY);
                    System.out.println(posicionX);
                    
                })
        );
        tiempoAnimacion.setCycleCount(Timeline.INDEFINITE);
        tiempoAnimacion.play();
    }
    //Metodo para los label del juego
    public void textosJuego(){
        //Textos y información que se mostrara en pantalla
        //Layout principal
        HBox puntuacion = new HBox();
        puntuacion.setMinWidth(ESCENA_TAM_X-60);
        puntuacion.setAlignment(Pos.TOP_RIGHT);
        puntuacion.setSpacing(150);
        root.getChildren().add(puntuacion);
        //Layout para puntuación actual
        HBox puntuacionActual = new HBox();
        puntuacionActual.setSpacing(10);
        puntuacion.getChildren().add(puntuacionActual);
        //Layout para puntuación máxima
        HBox puntuacionMaxima = new HBox();
        puntuacionMaxima.setSpacing(10);
        puntuacion.getChildren().add(puntuacionMaxima);
         //Texto de etiqueta para la puntuación
        Text textTitleScore = new Text("Puntos:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.BLACK);
        //Texto para la puntuación
        Text textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.BLACK);
        //Texto para la etiqueta de la puntuación máxima
        Text textTitleHighScore = new Text("Puntuación Maxima:");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textTitleHighScore.setFill(Color.BLACK);
        //Texto para la puntuación máxima
        Text textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.BLACK);
        //Añadimos los textos a los layouts reservados para ellos
        puntuacionActual.getChildren().add(textTitleScore);
        puntuacionActual.getChildren().add(textScore);
        puntuacionMaxima.getChildren().add(textTitleHighScore);
        puntuacionMaxima.getChildren().add(textHighScore);
        //Layout para las vidas
        HBox vidas = new HBox();
        vidas.setMinWidth(ESCENA_TAM_X);
        vidas.setAlignment(Pos.TOP_LEFT);
        vidas.setSpacing(20);
        root.getChildren().add(vidas);
        //Layout para la cantida de vidas
        HBox cantidaVidas = new HBox();
        cantidaVidas.setSpacing(10);
        vidas.getChildren().add(cantidaVidas);
        //Texto de etiqueta para las vidas
        Text textVidas = new Text("Vidas:");
        textVidas.setFont(Font.font(TEXT_SIZE));
        textVidas.setFill(Color.BLACK);
        //Texto para la cantidad de vidas
        Text textCantidaVidas = new Text("3");
        textCantidaVidas.setFont(Font.font(TEXT_SIZE));
        textCantidaVidas.setFill(Color.BLACK);
        //Añadimos los textos a los layouts reservados para ellos
        vidas.getChildren().add(textVidas);
        vidas.getChildren().add(textCantidaVidas);
    }
    //Metodo para el codigo del diseño del personaje y los enemigos
    public void diseñoPersonaje(){
    //Cuerpo del personaje
        grupoCuerpo = new Group();
        Rectangle rectTorso = new Rectangle(0,0,80,30);
        rectPataDel = new Rectangle(60,20,10,30);
        rectPataTra = new Rectangle(10,20,10,30);
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
        //Polilinea para la zona de contacto en el perro
        contactoPerro = new Polyline();
        contactoPerro.getPoints().addAll(new Double[]{
           -7.0, -9.0,
           7.0, 0.0,
           64.0, -1.0,
           65.0, -20.0,
           80.0, -20.0,
           80.0, -8.0,
           92.0, -7.0,
           98.0, 10.0,
           101.0, 16.0,
           91.0, 25.0,
           80.0, 23.0,
           71.0, 31.0,
           71.0, 50.0,
           60.0, 50.0,
           60.0, 31.0,
           20.0, 31.0,
           20.0, 50.0,
           10.0, 50.0,
           10.0, 30.0,
           1.0, 24.0,
           0.0, 8.0,
        });
        contactoPerro.setFill(Color.RED);
        contactoPerro.setVisible(visible);
        grupoContactoPerro = new Group();
        grupoContactoPerro.getChildren().add(contactoPerro);
        grupoContactoPerro.getChildren().add(grupoCuerpo);
        grupoContactoPerro.setLayoutX(posicionX);
        grupoContactoPerro.setLayoutY(posicionY);
        root.getChildren().add(grupoContactoPerro);
        //Polilinea para la zona de contacto en la mosca1
        contactoMosca = new Polyline();
        contactoMosca.getPoints().addAll(new Double[]{
            12.0, 22.0,
            27.0, 12.0,
            54.0, 14.0,
            66.0, 30.0,
            64.0, 48.0,
            42.0, 56.0,
            17.0, 50.0,
            9.0, 40.0,
            2.0, 34.0,
            7.0, 23.0,
        });
        contactoMosca.setFill(Color.RED);
        contactoMosca.setVisible(visible);
        grupoContactoMosca = new Group();
        grupoContactoMosca.getChildren().add(contactoMosca);
        grupoContactoMosca.getChildren().add(mosca1);
        root.getChildren().add(grupoContactoMosca);
        //Polilinea para la zona de contacto en la mosca3
        contactoMosca3 = new Polyline();
        contactoMosca3.getPoints().addAll(new Double[]{
            12.0, 22.0,
            27.0, 12.0,
            54.0, 14.0,
            66.0, 30.0,
            64.0, 48.0,
            42.0, 56.0,
            17.0, 50.0,
            9.0, 40.0,
            2.0, 34.0,
            7.0, 23.0,
        });
        contactoMosca3.setFill(Color.RED);
        contactoMosca3.setVisible(visible);
        grupoContactoMosca3 = new Group();
        grupoContactoMosca3.getChildren().add(contactoMosca3);
        grupoContactoMosca3.getChildren().add(mosca3);
        root.getChildren().add(grupoContactoMosca3);
        //Polilinea para la zona de contacto en el pinchos
        contactoPinchos = new Polyline();
        contactoPinchos.getPoints().addAll(new Double[]{
            4.0, 15.0,
            22.0, 19.0,
            35.0, 0.0,
            45.0, 18.0,
            65.0, 15.0,
            62.0, 30.0,
            68.0, 48.0,
            62.0, 68.0,
            7.0, 68.0,
            2.0, 50.0,
            6.0, 31.0,
        });
        contactoPinchos.setFill(Color.RED);
        contactoPinchos.setVisible(visible);
        grupoContactoPinchos = new Group();
        grupoContactoPinchos.getChildren().add(contactoPinchos);
        grupoContactoPinchos.getChildren().add(pinchos1);
        root.getChildren().add(grupoContactoPinchos);
    }
    //Metodo para el reinicio del juego
    public void reinicioJuego(){
    
    }
    //Metodo para las colisiones
    public void getColisiones(){
        
        Shape colisionMosca = Shape.intersect(contactoPerro, contactoMosca);
        boolean colisionVacia1 = colisionMosca.getBoundsInLocal().isEmpty();
        Shape colisionMosca3 = Shape.intersect(contactoPerro, contactoMosca3);
        boolean colisionVacia2 = colisionMosca3.getBoundsInLocal().isEmpty();
        Shape colisionPinchos = Shape.intersect(contactoPerro, contactoPinchos);
        boolean colisionVacia3 = colisionPinchos.getBoundsInLocal().isEmpty();
        
    }
    //Metodo para el limite de movimiento del personaje en la pantalla
    public void limiteMovimiento(){
    if (posicionX >= 500 && derecha == true){
        posicionX = 500;
        movimientoPerro = 0;
        contador ++;
        if (contador%2==0){
            rectPataDel.setRotate(30);
            rectPataTra.setRotate(30);
        }
        if (contador%2==1){
            rectPataDel.setRotate(-30);
            rectPataTra.setRotate(-30);
        }
        }else
            contador = 0;
    }
    //Metodo para detectar colisiones
    public void colisiones(){
    
    }
    public static void main(String[] args) {
        launch();
    }

}