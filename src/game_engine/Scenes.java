package game_engine;

import gui.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import levels.Map;
import levels.Tile;
import sounds.Sound;
import sounds.SoundManager;
import utilities.MapViewerScene;
import utilities.ZombieBoardRenderer;

import java.util.Random;

/**
 * @author Atle Olson
 *         A place to stage Scenes and switch them out
 */
public class Scenes implements EventHandler<ActionEvent>
{
  private final boolean DEBUG = false;
  private SoundManager soundManager;
  private Main main;
  private Stage primaryStage;

  private int winW = 1280;
  private int winH = 800;

  public Button returnButton = new Button();
  public Button returnButtonDeath = new Button();
  private Button goTo3dGame = new Button();
  public Button tryAgainButton = new Button();
  public Button goTo3dGameNextLevel = new Button();
  private Button goTo2dGame = new Button();
  private Button goToMapViewer = new Button();
  private Button goToMainMenu = new Button();
  private Button goToEndScreen = new Button();
  private Button goToGameOver = new Button();
  private Button goToWin = new Button();
  private Button goToSettings = new Button();

  Slider playerHearing = new Slider(0, 50, 1);
  Slider playerWalkingSpeed = new Slider(0, 2, 0.16);
  Slider playerSprintSpeed = new Slider(0, 2, 0.25);
  Slider playerStamina = new Slider(0, 10, 5);
  Slider playerRegen = new Slider(0, 2, 0.2);
  Slider zombieSmell = new Slider(0, 30, 15);
  Slider maxZombies = new Slider(0, 30, 20);
  Slider rotateSensitivity = new Slider(0, 20, 5);

  private ZombieHouse3d threeDGameObject = new ZombieHouse3d(0, this);
  private ZombieBoardRenderer twoDGameObject;
  private MapViewerScene mapObject = new MapViewerScene();
  private int difficulty = 0;
  public BorderPane startRoot, threeDGameRoot, twoDGameRoot, settingsRoot, gameOverRoot, loadRoot, winRoot;
  public Scene mainMenu, threeDGame, twoDGame, gameOver, loading, win, settings, nextLevel, mapScene;

  Map map;
  Tile[][] gameBoard;

  /**
   * @param primaryStage primary stage
   * @param main         Constructor for a scenes object
   */
  public Scenes(Stage primaryStage, Main main)
  {

    this.main = main;
    this.primaryStage = primaryStage;

    returnButton.setText("Back to main menu.");
    returnButton.setOnAction(this);

    returnButtonDeath.setText("Back to main menu.");
    returnButtonDeath.setOnAction(this);

    goTo3dGame.setText("Start Game!");
    goTo3dGame.setOnAction(this);

    tryAgainButton.setText("Try Again?");
    tryAgainButton.setOnAction(this);

    goTo3dGameNextLevel.setText("Next Level!!");
    goTo3dGameNextLevel.setOnAction(this);

    goTo2dGame.setText("Play 2d ZombieHouse!");
    goTo2dGame.setOnAction(this);

    goToMapViewer.setText("Map Viewer");
    goToMapViewer.setOnAction(this);

    goToGameOver.setText("Game Over!");
    goToGameOver.setOnAction(this);

    goToWin.setText("Win Screen");
    goToWin.setOnAction(this);

    goToSettings.setText("Settings");
    goToSettings.setOnAction(this);

    playerHearing.setShowTickMarks(true);
    playerHearing.setShowTickLabels(true);

    playerWalkingSpeed.setShowTickMarks(true);
    playerWalkingSpeed.setShowTickLabels(true);

    playerSprintSpeed.setShowTickMarks(true);
    playerSprintSpeed.setShowTickLabels(true);

    playerStamina.setShowTickMarks(true);
    playerStamina.setShowTickLabels(true);

    playerRegen.setShowTickMarks(true);
    playerRegen.setShowTickLabels(true);

    zombieSmell.setShowTickMarks(true);
    zombieSmell.setShowTickLabels(true);

    maxZombies.setShowTickMarks(true);
    maxZombies.setSnapToTicks(true);
    maxZombies.setShowTickLabels(true);
    maxZombies.setMajorTickUnit(5);

    rotateSensitivity.setShowTickMarks(true);
    rotateSensitivity.setSnapToTicks(true);
    rotateSensitivity.setShowTickLabels(true);
    rotateSensitivity.setMajorTickUnit(4);

    playerHearing.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Hearing = playerHearing.getValue();
      }
    });
    playerWalkingSpeed.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Walking_Speed = playerWalkingSpeed.getValue();
      }
    });
    playerSprintSpeed.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Sprint_Speed = playerSprintSpeed.getValue();
      }
    });
    playerStamina.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Stamina = playerStamina.getValue();
      }
    });
    playerRegen.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Regen = playerRegen.getValue();
      }
    });
    zombieSmell.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Zombie_Smell = zombieSmell.getValue();
      }
    });
    maxZombies.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Max_Zombies = maxZombies.getValue();
      }
    });
    rotateSensitivity.valueProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
      {
        Attributes.Player_Rotate_sensitivity = rotateSensitivity.getValue();
      }
    });

    //Main menu Scene
    Random random = new Random();
    startRoot = new BorderPane();
    if(random.nextInt(2) == 1) startRoot.setStyle("-fx-background-image: url(/Images/background.jpg);-fx-background-size: stretch;-fx-background-repeat: " + "no-repeat;");
    else startRoot.setStyle("-fx-background-image: url(/Images/background2.jpg);-fx-background-size: stretch;-fx-background-repeat: " + "no-repeat;");
    startRoot.setPrefSize(winW, winH);
    VBox buttonVBox = new VBox();
    if(!DEBUG) buttonVBox.getChildren().addAll(goTo3dGame, goToSettings);
    else buttonVBox.getChildren().addAll(goTo3dGame, goTo2dGame, goToMapViewer, goToGameOver, goToWin, goToSettings);
    buttonVBox.setSpacing(5);//vertical spacing in between buttons
    buttonVBox.setPadding(new Insets(5, 5, 5, 5));
    startRoot.setCenter(buttonVBox);

    //3D Game Scene
    threeDGameRoot = new BorderPane();
    threeDGameRoot.setStyle("-fx-background-size: stretch;");

    //2D Game Scene
    twoDGameRoot = new BorderPane();
    twoDGameRoot.setPrefSize(winW, winH);

    //Game over Scene
    gameOverRoot = new BorderPane();
    gameOverRoot.setStyle("-fx-background-image: url(/Images/dead_bg.jpg);-fx-background-size: stretch;-fx-background-repeat: " + "no-repeat;");
    gameOverRoot.setPrefSize(winW, winH);
    HBox hBoxGameOver = new HBox();
//    hBoxGameOver.getChildren().addAll(returnButtonDeath, tryAgainButton);
    gameOverRoot.setTop(hBoxGameOver);

    //loading Scene
    loadRoot = new BorderPane();
    loadRoot.setPrefSize(winW, winH);
    loadRoot.setCenter(new Label("Loading screen!"));

    //Win Scene
    winRoot = new BorderPane();
    winRoot.setPrefSize(winW, winH);
    winRoot.setCenter(new Label("You Won! " + this.threeDGameObject.difficulty));
    HBox hBoxWin = new HBox();
    hBoxWin.getChildren().addAll(returnButton, goTo3dGameNextLevel);
    gameOverRoot.setTop(hBoxWin);

    //Settings Scene
    settingsRoot = new BorderPane();
    settingsRoot.setPrefSize(winW, winH);
    settingsRoot.setCenter(new Label("Settings!"));
    VBox sliders = new VBox();
    sliders.getChildren().addAll(new Label("Player Hearing:"), playerHearing, new Label("Player Walking Speed"), playerWalkingSpeed, new Label("Player Sprint Speed"), playerSprintSpeed, new Label("Player Stamina"), playerStamina, new Label("Player Regen"), playerRegen, new Label("Zombie Smell"), zombieSmell, new Label("Max Zombies"), maxZombies, new Label("Rotate Sensitivity"), rotateSensitivity);
    sliders.setSpacing(5);
    sliders.setPadding(new Insets(10, 20, 10, 20));
    settingsRoot.setLeft(sliders);

    mainMenu = new Scene(startRoot);
    threeDGame = new Scene(threeDGameRoot);
    twoDGame = new Scene(twoDGameRoot);
    gameOver = new Scene(gameOverRoot);
    loading = new Scene(loadRoot);
    win = new Scene(winRoot);
    settings = new Scene(settingsRoot);
  }

  /**
   * Plays a sound when you click buttons
   */
  private void playButtonSound()
  {
    soundManager.playSoundClip(Sound.button);
  }

  public void updateWinScreen()
  {
    String s = "";
    int numLevels = this.threeDGameObject.difficulty + 1;
    if (numLevels > 1) s = "s";
    if (numLevels == 5)
    {
      this.winRoot.setCenter(new Label("You have escaped all 5 Houses, good job!"));
    }
    else
    {
      this.winRoot.setCenter(new Label("You have escaped  " + numLevels + " house" + s));
    }
  }

  public Main getMain()
  {
    return main;
  }

  public SoundManager getSoundManager()
  {
    return soundManager;
  }

  /**
   * @param soundManager Setter for the sound manager
   */
  public void setSoundManager(SoundManager soundManager)
  {
    this.soundManager = soundManager;
  }

  /**
   * Creates a new 2d game board
   */
  private void create2DGameBoard()
  {
    twoDGameObject = new ZombieBoardRenderer(main, this);
  }

  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();

    if (source == goTo3dGame) //START 3D Game
    {
      playButtonSound();
      map = new Map(difficulty);
      try
      {
        gameBoard = map.getGameBoard();
        main.assignStage(threeDGameObject.zombieHouse3d(primaryStage, gameBoard));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == tryAgainButton) //TRY AGAIN
    {
      playButtonSound();
      soundManager.playTrack(0);
      System.out.println("TRY AGAIN");
      try
      {
        main.assignStage(threeDGameObject.resetScene());
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goTo3dGameNextLevel) //NEXT LEVEL
    {
      playButtonSound();
      soundManager.playTrack(0);
      difficulty++;
      map = new Map(difficulty);
      try
      {
        gameBoard = map.getGameBoard();
        main.assignStage(threeDGameObject.zombieHouse3d(primaryStage, gameBoard));
        //ZombieHouse3d.parent.getChildren().add(returnButton);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == returnButton || source == returnButtonDeath) //BACK TO MAIN MENU
    {
      playButtonSound();
      soundManager.playTrack(0);
      if (source == returnButtonDeath){
        System.out.println("DISPOSE");
        threeDGameObject.dispose();
      }
      try
      {
        main.assignStage(mainMenu);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goTo2dGame)
    {
      playButtonSound();
      create2DGameBoard();
      try
      {
        main.assignStage(twoDGameObject.zombieHouse2d(primaryStage));
        ZombieBoardRenderer.root.getChildren().add(returnButton);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goToMapViewer)
    {
      playButtonSound();
      try
      {
        main.assignStage(mapObject.mapViewerScene(primaryStage));
        MapViewerScene.root.getChildren().add(returnButton);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goToGameOver)
    {
      playButtonSound();
      try
      {
        gameOverRoot.setTop(returnButton);
        main.assignStage(gameOver);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goToWin)
    {
      playButtonSound();
      try
      {
        winRoot.setTop(returnButton);
        main.assignStage(win);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (source == goToSettings)
    {
      playButtonSound();
      try
      {
        settingsRoot.setTop(returnButton);
        main.assignStage(settings);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
