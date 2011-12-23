import org.newdawn.slick._
import state.{StateBasedGame, BasicGameState}

class MainMenuState(stateID:Int) extends BasicGameState {
  
  var background:Image = _
  var startGameOption:Image = _
  var exitOption:Image = _

  var fx:Sound = _
  
  var startGameScale = 1f
  var exitScale = 1f

  var menuX = 50
  var menuY = 50

  val scaleStep = 0.0001f
  
  def init(container: GameContainer, game: StateBasedGame) {
    background = new Image("menu.jpeg")

    val menuOptions = new Image("menuoptions.png")

    startGameOption = menuOptions.getSubImage(0, 0, 377, 71)

    exitOption = menuOptions.getSubImage(0, 71, 377, 71)
  }

  def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
    background.draw(0, 0)

    startGameOption.draw(menuX, menuY, startGameScale)

    exitOption.draw(menuX, menuY + 80, exitScale)
  }

  def update(gc: GameContainer, sb: StateBasedGame, delta: Int) {
    val input = gc.getInput
    
    val mouseX = input.getMouseX
    val mouseY = input.getMouseY
    
    var insideStartGame = false
    var insideExit = false
    
    if( (mouseX >= menuX && mouseX >= menuX + startGameOption.getWidth) &&
        (mouseY >= menuY && mouseY >= menuY + startGameOption.getHeight)) {
      insideStartGame = true
    } else if( (mouseX >= menuX && mouseX >= menuX + exitOption.getWidth) &&
               (mouseY >= menuY + 80 && mouseY >= menuY + 80 + exitOption.getHeight)) {
      insideExit = true
    } 
    
    if(insideStartGame) {
      if(startGameScale < 1.05f)
        startGameScale += scaleStep * delta
      
      if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
        sb.enterState(SlickBlocksGame.gamePlayState)
      }
    } else {
      if(startGameScale > 1.0f)
        startGameScale -= scaleStep * delta
      
      if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        gc.exit()      
    }
    
    if(insideExit) {
      if(exitScale < 1.05f)
        exitScale += scaleStep * delta
    } else {
      if(exitScale > 1.0f)
        exitScale -= scaleStep * delta
    }                                  
  }

  def getID = stateID
}


class GameplayState(stateID:Int) extends BasicGameState {

  def init(container: GameContainer, game: StateBasedGame) {}

  def render(container: GameContainer, game: StateBasedGame, g: Graphics) {}

  def update(container: GameContainer, game: StateBasedGame, delta: Int) {}

  def getID = stateID
}

class SlickBlocksGame extends StateBasedGame("SlickBlocks") {
  import SlickBlocksGame._
  addState(new MainMenuState(mainMenuState))
  addState(new GameplayState(gamePlayState))
  enterState(mainMenuState)

  override def initStatesList(gameContainer: GameContainer) {
    getState(mainMenuState).init(gameContainer, this)
    getState(gamePlayState).init(gameContainer, this)
  }
}

object SlickBlocksGame {
  val mainMenuState = 0
  val gamePlayState = 1
}

/*object BlockGameMain extends App {

  def unsafeAddDir(dir: String) = try {
      val field = classOf[ClassLoader].getDeclaredField("usr_paths")
      field.setAccessible(true)
      val paths = field.get(null).asInstanceOf[Array[String]]
      if(!(paths contains dir)) {
        field.set(null, paths :+ dir)
        System.setProperty("java.library.path",
  			 System.getProperty("java.library.path") +
  			 java.io.File.pathSeparator +
  			 dir)
      }
    } catch {
      case _: IllegalAccessException =>
        error("Insufficient permissions; can't modify private variables.")
      case _: NoSuchFieldException =>
        error("JVM implementation incompatible with path hack")
    }

  println("lib path: " + System.getProperty("java.library.path"))
  unsafeAddDir("./lib")
  unsafeAddDir("/Users/fabian/Downloads/jinput_lib")
  println("lib path: " + System.getProperty("java.library.path"))
  val app = new AppGameContainer(new SlickBlocksGame())
  app.setDisplayMode(800, 600, false)
  app.start
}     */