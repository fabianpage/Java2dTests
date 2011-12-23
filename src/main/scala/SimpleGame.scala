import org.lwjgl.util.Rectangle
import org.newdawn.slick._


class SimpleGame extends BasicGame("Slick2DPath2Gloryyyy - SimpleGame") {
  var land:Image = _
  var plane:Image = _
  
  var x:Float = 100
  var y:Float = 100
  var scale:Float = 1
  
	override def init(gc:GameContainer): Unit = {
    land = new Image("land.jpg")
    plane = new Image("plane.png")
  }
	override def update(gc:GameContainer, delta:Int): Unit = {
    val input = gc.getInput

    if(input.isKeyDown(Input.KEY_A))
      plane.rotate(-0.2f * delta)

    if(input.isKeyDown(Input.KEY_D))
      plane.rotate(0.2f * delta)
    
    if(input.isKeyDown(Input.KEY_W)){
      val hip = 0.4f * delta
      val rot = plane.getRotation
      
      x += hip * math.sin(rot / 360 * 2 * math.Pi).toFloat
      y -= hip * math.cos(rot / 360 * 2 * math.Pi).toFloat
    }
    
    if(input.isKeyDown(Input.KEY_2)) {
      scale += {if(scale >= 5) 0 else 0.02f}
      plane.setCenterOfRotation(plane.getWidth / 2f * scale, plane.getHeight / 2f * scale)
    }

    if(input.isKeyDown(Input.KEY_1)) {
      scale -= {if(scale <= 1) 0 else 0.02f}
      plane.setCenterOfRotation(plane.getWidth / 2f * scale, plane.getHeight / 2f * scale)
    }
    

  }
	override def render(gc:GameContainer, g:Graphics): Unit = {
    land.draw(0,0)
    plane.draw(x,y,scale)
  }
  

  
}

/*object SimpleGameMain extends App {
	val app = new AppGameContainer(new SimpleGame())
	app.setDisplayMode(800, 600, false)
	app.start
}*/

/*class Huge[@specialized A, @specialized B, @specialized C](
  val a: A, val b: B, val c: C
) {} // 730 files, 2.9 MB


class Gigantic[@specialized A, @specialized B, @specialized C, @specialized D](
  val a: A, val b: B, val c: C, val d: D
) {} // 6562 files, 26 MB*/