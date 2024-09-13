//package project3;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown = new Text();
    private Timeline timeline;
    private final int TIMER = 15;
    private int count = 0;

    //private Timeline background_Time;
    private Text score;
    private Text main_word = new Text();
    private FlowPane fPane = new FlowPane();
    private Region bottomRegion = new Region();
    private Region bottomRegionL = new Region();
    private HBox top_box = new HBox();
    private HBox bottom_box = new HBox();
    private final ColorsEnum[] string_colors = ColorsEnum.values();//string color array
    private Random random_num = new Random();//object of generation of random numbers
    
    private int current_Score = 0;
   
    private Image correct = new Image("correct.png");
    private ImageView imageView = new ImageView();
    private Image wrong = new Image("wrong.png");
    private ArrayList<Text> wordArray = new ArrayList<Text>();
    //private String[] flash_Col = {"PINK", "BEIGE", "BURLYWOOD", "CYAN", "GOLD", "LAVENDER" };

    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();
        

    }
    
    public void endOfGame()
    {
            borderPane.setTop(null);
            borderPane.setCenter(null);
            borderPane.setBottom(null);

            fPane.getChildren().clear();
            wordArray.clear();

            VBox vbox = new VBox();// new vertical box 
            Text user_Score = new Text("Your score: " + current_Score);
            user_Score.setFont(new Font(40));
            Button play_again_btn = new Button("Play again");
            vbox.getChildren().addAll(user_Score,play_again_btn);
            vbox.setAlignment(Pos.CENTER);      //put the vertical box in the center possition 
            vbox.setSpacing(20);
            borderPane.setCenter(vbox);

            play_again_btn.setOnAction(event -> {
                borderPane.setCenter(null);     //reset the panel
                score.setText("Score: " + current_Score);
                borderPane.setBottom(bottom_box);
                borderPane.setTop(top_box);
                borderPane.setCenter(fPane);
                current_Score = 0;
                
                imageView.setImage(null);

                startPlay();
            });
    }


    public void checkChoice(Text choice)            //take player mouse click choice 
    {
        if(main_word.getFill().equals(Color.web(Color.valueOf(choice.getText()) +"")))      //main_word contain main words 
        {
            //System.out.println(main_word.getFill());
           // System.out.println(Color.web(Color.valueOf(choice.getText())+""));
            current_Score++;
            score.setText("Score: " + current_Score);
            imageView.setImage(correct);
        }
        else
        {
            imageView.setImage(wrong);
        }
        fPane.getChildren().clear();
        wordArray.clear();
        //Do NOT add any code after this comment
        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        main_word.setText(string_colors[random_num.nextInt(string_colors.length)] + ""); //set the main text colors color[random int from[i+color.lenght]] is just a words from the colors enum files ex.blue!=color=string
        main_word.setFont(new Font("Marker Felt", 60));     
        main_word.setFill(Color.valueOf(string_colors[random_num.nextInt(string_colors.length)] + ""));   //set the filling of the main text 
        main_word.setTextAlignment(TextAlignment.CENTER);
        borderPane.setTop(top_box);
    }
    
    public void colorNameOptions()
    {
        // Create new text objects with the color array names
        // Add a listener to each text object
        for(int i = 0; i < string_colors.length; i++)   //populate the wordarray and set a listener to each text 
        {
            Text text = new Text(string_colors[i] + "");
            text.setFont(new Font("Marker Felt", 40));
            wordArray.add(text);
            //System.out.println(text);
            text.setOnMouseClicked(
                event -> {checkChoice(text);}
                );
        }
        /*for(Text i:wordArray)
        {System.out.println("this should be the text you can click"+i);
        }*/

        // shuffle the arrayList and add the whole list to the flow pane
        Collections.shuffle(wordArray);
        for (Text t: wordArray) {
            fPane.getChildren().add(t);         ///this should be the words that display 
        }

        /*for(Text i:wordArray)
        {System.out.println("this is after ramdomize the choice using shuffle"+i);
        }*/
        // shuffle the arrayList so that you can get different color fills
        Collections.shuffle(wordArray);
        for(int i = 0; i < string_colors.length; i++)
        {
            wordArray.get(i).setFill(Color.valueOf(string_colors[i] + ""));
        }
        
        /*for(Text i:wordArray)
        {System.out.println("this is the color are each words"+i);
        }*/
    }

    public void initializeGame()
    {
        // Setting up flow pane for the center region
        fPane.setHgap(10);
        fPane.setOrientation(Orientation.HORIZONTAL);
        fPane.setAlignment(Pos.CENTER);
        fPane.setPrefHeight((borderPane.getHeight()*55)/100);
       // System.out.println((borderPane.getHeight()*55)/100);//center box 55%
        fPane.setPadding(new Insets(0,40,0,40));            //left,right,top,bottom

        top_box.getChildren().add(main_word);
        top_box.setAlignment(Pos.CENTER);
        top_box.setPrefHeight((borderPane.getHeight()*35)/100);
        //System.out.println((borderPane.getHeight()*35)/100);// top height 35%
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        // Setting up the bottom for the timer and the score
        score = new Text("Score: " + current_Score);
        score.setFont(new Font(20));

        txtCountDown.setFont(new Font(20));
        Text time = new Text("Timer: ");
        time.setFont(new Font(20));
        bottom_box.getChildren().addAll(score, bottomRegionL, imageView, bottomRegion, time, txtCountDown);
        bottom_box.setHgrow(bottomRegion, Priority.ALWAYS);
        bottom_box.setHgrow(bottomRegionL, Priority.ALWAYS);
        bottom_box.setPadding(new Insets(0,5,0,5));
        bottom_box.setPrefHeight((borderPane.getHeight()*10)/100);


        borderPane.setTop(top_box);
        borderPane.setMargin(main_word, new Insets(10));
        borderPane.setCenter(fPane);
        borderPane.setBottom(bottom_box);
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}