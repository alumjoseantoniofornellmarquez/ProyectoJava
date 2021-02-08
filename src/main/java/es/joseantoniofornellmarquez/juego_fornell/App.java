package es.joseantoniofornellmarquez.juego_fornell;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import javafx.animation.Animation;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
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
//Variables polilineas y rectangulo
Polyline contactoMosca;
Polyline contactoPerro;
Polyline contactoPinchos;
Polyline contactoMosca3;
Rectangle contactoHueso;
//Variable para el grupo
Group grupoCuerpo;
Group grupoContactoPerro;
Group grupoContactoMosca;
Group grupoContactoMosca3;
Group grupoContactoPinchos;
Group grupoContactoHueso;
boolean visible= false;
//Variables para las colisiones
boolean colisionVacia1;
boolean colisionVacia2;
boolean colisionVacia3;
boolean colisionVacia4;
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
//Hueso posición
int huesoPosicionY;
int huesoPosicionX;
//Incremento para la caida del hueso
int velocidadCaidaHueso = -3;
//Variables para la imagen de los enemigos y hueso
ImageView mosca1;
ImageView pinchos1;
ImageView mosca3;
ImageView femur;
//Velocidad de enemigo
int velocidadMosca = -2;
int velocidadPinchos = -1;
//Objeto rando para las posiciones de los enemigos
Random random = new Random();
//Variable tamaño textos
final int TEXT_SIZE = 24;
//Puntuación
int score = 0;
//Texto para la puntuación
Text textScore;
//TextoVidas
Text textCantidaVidas;
int vidas = 10;
//Texto maximo puntos
Text textHighScore;
int maxscore = 0;
//Texto inicio
Text textInicio;
HBox inicio;
//Texto muerto
Text textMuerto;
Text textMuerto2;
VBox hasMuerto;
//Variable para la animación
Timeline tiempoAnimacion;
//Variable para ver si estoy vivo o muerto
boolean noMuerto = true;
//Variable incremento vida
boolean incrementoVida = false;
//Variables audio
AudioClip audioClip1;//Sonido del juego
AudioClip audioClip2;//Sonido salto
AudioClip audioClip3;//Sonido golpeo con enemigos
AudioClip audioClip4;//Sonido al obtener un hueso
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
        //Imagen de los enemigos y hueso
        Image moscaArriba = new Image(getClass().getResourceAsStream("/images/fly1.png"));
        Image moscaAbajo = new Image(getClass().getResourceAsStream("/images/fly2.png"));
        Image pinchos = new Image(getClass().getResourceAsStream("/images/pichos.png"));
        Image hueso = new Image(getClass().getResourceAsStream("/images/femur.png"));
        mosca1 = new ImageView();
        pinchos1 = new ImageView();
        mosca3 = new ImageView();
        femur = new ImageView();
        root.getChildren().add(mosca1);
        root.getChildren().add(pinchos1);
        root.getChildren().add(mosca3);
        root.getChildren().add(femur);
        //Posicion aletoria del enemigo
        enemigoPosicionX1 = random.nextInt(200) + 800;
        enemigoPosicionY1 = random.nextInt(50) + 400;
        enemigoPosicionX2 = random.nextInt(200) + 1500;
        enemigoPosicionY2 = 470;
        enemigoPosicionX3 = random.nextInt(200) + 2200;
        enemigoPosicionY3 = random.nextInt(50) + 400;
        //Posicion aletoria del hueso
        huesoPosicionX = random.nextInt(200) + 4000;
        huesoPosicionY = random.nextInt(50) + 400;
        //Llamada al metodo del audio
        audioJuego();
        audioSalto();
        audioMuerto();
        audioHueso();
        //Llamada al metodo del personaje
        diseñoPersonaje();
        //LLamada al metodo de los textos
        textosJuego();
        //Metodo para el reinicio del juego
        reinicioJuego();
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
                //Salto
                case SPACE:
                    movimientoY = -10;
                    audioClip2.play();
                    break;
                //Pause
                case P:
                    if (tiempoAnimacion.getStatus()== Animation.Status.RUNNING){
                        tiempoAnimacion.pause();
                        audioClip1.stop();
                    }else {
                        tiempoAnimacion.play();
                        audioClip1.play();
                    }
                    break;
                case ESCAPE: 
                //Reinicio del juego cuando mueres
                if (noMuerto == false && tiempoAnimacion.getStatus()== Animation.Status.STOPPED){
                    reinicioJuego();
                    tiempoAnimacion.stop();
                    root.getChildren().add(inicio);
                    hasMuerto.getChildren().remove(textMuerto);
                    hasMuerto.getChildren().remove(textMuerto2);
                    audioClip1.play();
                }
                    break;
                case ENTER:
                //Código que lleva el inicio del juego
                if (noMuerto == true && tiempoAnimacion.getStatus()== Animation.Status.STOPPED){
                    reinicioJuego();
                    tiempoAnimacion.play();
                    root.getChildren().remove(inicio);
                }
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            movimientoPerro = 0;
            rectPataDel.setRotate(0);
            rectPataTra.setRotate(0);
            derecha = false;
        });
        //Código para lla animación del juego
        tiempoAnimacion = new Timeline(
                new KeyFrame(Duration.seconds(0.01), (ActionEvent ae) -> {
                    //Movimiento del fondo
                    if (movimientoFondo <= -ESCENA_TAM_X){
                        movimientoFondo = movimientoFondo2 + ESCENA_TAM_X;
                        fondoVisto.setLayoutX(movimientoFondo);
                    }
                    if (movimientoFondo2 <= -ESCENA_TAM_X){
                        movimientoFondo2 = movimientoFondo + ESCENA_TAM_X;
                        fondoVisto2.setLayoutX(movimientoFondo2);
                    }
                    //Movimiento del salto
                    posicionY += movimientoY;
                    //Si ha llegado a arriba
                    if (posicionY <= 250){
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
                        enemigoPosicionX1 = random.nextInt(200) + 800;
                        enemigoPosicionY1 = random.nextInt(50) + 400;
                    }
                    //Pinchos
                    if (enemigoPosicionX2 <= -ESCENA_TAM_X){
                        enemigoPosicionX2 = ESCENA_TAM_X;
                        grupoContactoPinchos.setLayoutX(enemigoPosicionX2);
                        enemigoPosicionX2 = random.nextInt(200) + 1500;
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
                        enemigoPosicionX3 = random.nextInt(200) + 2200;
                        enemigoPosicionY3 = random.nextInt(50) + 400;
                    }
                    //Movimiento de hueso
                    huesoPosicionX += velocidadCaidaHueso;
                    if (huesoPosicionX <= -ESCENA_TAM_X){
                        huesoPosicionX = ESCENA_TAM_X;
                        grupoContactoHueso.setLayoutX(huesoPosicionX);
                        huesoPosicionX = random.nextInt(200) + 1000;
                        huesoPosicionY = random.nextInt(50) + 400;
                    }
                    //Muestra de imagenes en sus posiciones
                    femur.setImage(hueso);
                    pinchos1.setImage(pinchos);
                    fondoVisto.setLayoutX(movimientoFondo);
                    fondoVisto2.setLayoutX(movimientoFondo2);
                    grupoContactoPerro.setLayoutY(posicionY);
                    grupoContactoPerro.setLayoutX(posicionX);
                    grupoContactoHueso.setLayoutY(huesoPosicionY);
                    grupoContactoHueso.setLayoutX(huesoPosicionX);
                    //System.out.println(posicionY);
                    //System.out.println(posicionX);
                    //System.out.println(velocidadCaidaHueso);
                    //Metodo que controla las colisiones y la puntuación
                    getColisiones();
                    //Condiciones para ganar vidas y aumentar la velocidad
                    if (score %1000 == 0 && score > 0 && incrementoVida == false){
                        vidas++;
                        textCantidaVidas.setText(String.valueOf(vidas));
                        System.out.println(score);
                        incrementoVida = true;
                    }
                })
        );
        tiempoAnimacion.setCycleCount(Timeline.INDEFINITE);
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
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.BLACK);
        //Texto para la etiqueta de la puntuación máxima
        Text textTitleHighScore = new Text("Puntuación Maxima:");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textTitleHighScore.setFill(Color.BLACK);
        //Texto para la puntuación máxima
        textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.BLACK);
        //Añadimos los textos a los layouts reservados para ellos
        puntuacionActual.getChildren().add(textTitleScore);
        puntuacionActual.getChildren().add(textScore);
        puntuacionMaxima.getChildren().add(textTitleHighScore);
        puntuacionMaxima.getChildren().add(textHighScore);
        //Layout para las vidas
        HBox cajaVidas = new HBox();
        cajaVidas.setMinWidth(ESCENA_TAM_X);
        cajaVidas.setAlignment(Pos.TOP_LEFT);
        cajaVidas.setSpacing(20);
        root.getChildren().add(cajaVidas);
        //Layout para la cantida de vidas
        HBox cantidaVidas = new HBox();
        cantidaVidas.setSpacing(10);
        cajaVidas.getChildren().add(cantidaVidas);
        //Texto de etiqueta para las vidas
        Text textVidas = new Text("Vidas:");
        textVidas.setFont(Font.font(TEXT_SIZE));
        textVidas.setFill(Color.BLACK);
        //Texto para la cantidad de vidas
        textCantidaVidas = new Text("3");
        textCantidaVidas.setFont(Font.font(TEXT_SIZE));
        textCantidaVidas.setFill(Color.BLACK);
        //Añadimos los textos a los layouts reservados para ellos
        cajaVidas.getChildren().add(textVidas);
        cajaVidas.getChildren().add(textCantidaVidas);
        //Label inicial
        inicio = new HBox();
        inicio.setMinWidth(ESCENA_TAM_X);
        inicio.setMinHeight(ESCENA_TAM_Y);
        inicio.setAlignment(Pos.CENTER);
        inicio.setSpacing(150);
        root.getChildren().add(inicio);
        textInicio = new Text("¡Pulsa ENTER para iniciar!");
        textInicio.setFont(Font.font(50));
        textInicio.setFill(Color.BLACK);
        inicio.getChildren().add(textInicio);
        //Label muerto
        hasMuerto = new VBox();
        hasMuerto.setMinWidth(ESCENA_TAM_X);
        hasMuerto.setMinHeight(ESCENA_TAM_Y);
        hasMuerto.setAlignment(Pos.CENTER);
        hasMuerto.setSpacing(150);
        root.getChildren().add(hasMuerto);
        textMuerto = new Text("¡Has Muerto!");
        textMuerto2 = new Text("Pulsa ESC para reiniciar");
        textMuerto.setFont(Font.font(50));
        textMuerto.setFill(Color.BLACK);
        textMuerto2.setFont(Font.font(50));
        textMuerto2.setFill(Color.BLACK);
        
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
        //Rectangulo para la zona de contacto del hueso
        contactoHueso = new Rectangle(15, 35);
        contactoHueso.setRotate(30);
        contactoHueso.setFill(Color.RED);
        contactoHueso.setVisible(false);
        grupoContactoHueso = new Group();
        grupoContactoHueso.getChildren().add(contactoHueso);
        grupoContactoHueso.getChildren().add(femur);
        root.getChildren().add(grupoContactoHueso);
    }
    //Metodo para el reinicio del juego
    public void reinicioJuego(){
        //Velocidad del movimiento del personaje
        movimientoPerro = 0;
        //Posicion del fondo
        movimientoFondo = 0;
        movimientoFondo2 = 800;
        //Posicion inicial del personaje
        posicionY = 470;
        posicionX = 20;
        //vidas
        vidas = 10;
        //Puntuación
        score = 0;
        //Enemigos
        //Posicion aletoria del enemigo
        enemigoPosicionX1 = random.nextInt(200) + 800;
        enemigoPosicionY1 = random.nextInt(50) + 400;
        enemigoPosicionX2 = random.nextInt(200) + 1500;
        enemigoPosicionY2 = 470;
        enemigoPosicionX3 = random.nextInt(200) + 2200;
        enemigoPosicionY3 = random.nextInt(50) + 400;
        //Posicion aletoria del hueso
        huesoPosicionX = random.nextInt(200) + 4000;
        huesoPosicionY = random.nextInt(50) + 400;
        //Texto para la puntuación máxima
        textScore.setText(String.valueOf(score));
        textCantidaVidas.setText(String.valueOf(vidas));
        //Variable noMuerto
        noMuerto = true;
    }
    //Metodo para las colisiones
    public void getColisiones(){
        //Detección de colisiones 
        Shape colisionMosca = Shape.intersect(contactoPerro, contactoMosca);
        colisionVacia1 = colisionMosca.getBoundsInLocal().isEmpty();
        Shape colisionMosca3 = Shape.intersect(contactoPerro, contactoMosca3);
        colisionVacia2 = colisionMosca3.getBoundsInLocal().isEmpty();
        Shape colisionPinchos = Shape.intersect(contactoPerro, contactoPinchos);
        colisionVacia3 = colisionPinchos.getBoundsInLocal().isEmpty();
        Shape colisionHueso = Shape.intersect(contactoPerro, contactoHueso);
        colisionVacia4 = colisionHueso.getBoundsInLocal().isEmpty();
        //Control de puntuación y vidas
        if ((colisionVacia1 == false || colisionVacia2 == false || colisionVacia3 == false) && (vidas > 0)){
            vidas -= 1;
            textCantidaVidas.setText(String.valueOf(vidas));
            enemigoPosicionX1 = random.nextInt(200) + 800;
            enemigoPosicionY1 = random.nextInt(50) + 400;
            enemigoPosicionX2 = random.nextInt(200) + 1500;
            enemigoPosicionY2 = 470;
            enemigoPosicionX3 = random.nextInt(200) + 2200;
            enemigoPosicionY3 = random.nextInt(50) + 400;
            audioClip3.play();
        }
        if (colisionVacia4 == false  && (vidas > 0)){
            score += 200;
            textScore.setText(String.valueOf(score));
            huesoPosicionX += velocidadCaidaHueso;
            System.out.println(colisionVacia4);
            huesoPosicionX = random.nextInt(100) + 1000;
            huesoPosicionY = random.nextInt(50) + 400;
            incrementoVida = false;
            audioClip4.play();
        }
        if (vidas == 0){
            if (score > maxscore){
                maxscore = score;
                textHighScore.setText(String.valueOf(maxscore));
            }
           noMuerto = false;
           tiempoAnimacion.stop();
           hasMuerto.getChildren().add(textMuerto);
           hasMuerto.getChildren().add(textMuerto2);
           audioClip1.stop();
        }
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
    //Metodo para el audio
    public void audioJuego(){
        URL urlAudio = getClass().getResource("/sonidos/SonidoPrincipal.mp3");
        if(urlAudio != null) {
            try {
                audioClip1 = new AudioClip(urlAudio.toURI().toString());
                audioClip1.play();
        } catch (URISyntaxException ex) {
        System.out.println("Error en el formato de ruta de archivo de audio");
        }            
        } else {
        System.out.println("No se ha encontrado el archivo de audio");
        }
    }
    public void audioSalto(){
        URL urlAudio2 = getClass().getResource("/sonidos/Salto.wav");
        if(urlAudio2 != null) {
            try {
                audioClip2 = new AudioClip(urlAudio2.toURI().toString());
        } catch (URISyntaxException ex) {
        System.out.println("Error en el formato de ruta de archivo de audio");
        }            
        } else {
        System.out.println("No se ha encontrado el archivo de audio");
        }
    }
    public void audioMuerto(){
        URL urlAudio3 = getClass().getResource("/sonidos/Muerto.wav");
        if(urlAudio3 != null) {
            try {
                audioClip3 = new AudioClip(urlAudio3.toURI().toString());
        } catch (URISyntaxException ex) {
        System.out.println("Error en el formato de ruta de archivo de audio");
        }            
        } else {
        System.out.println("No se ha encontrado el archivo de audio");
        }
    }
    public void audioHueso(){
        URL urlAudio4 = getClass().getResource("/sonidos/Hueso.wav");
        if(urlAudio4 != null) {
            try {
                audioClip4 = new AudioClip(urlAudio4.toURI().toString());
        } catch (URISyntaxException ex) {
        System.out.println("Error en el formato de ruta de archivo de audio");
        }            
        } else {
        System.out.println("No se ha encontrado el archivo de audio");
        }
    }
    public static void main(String[] args) {
        launch();
    }

}