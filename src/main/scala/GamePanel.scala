import java.awt.geom.Rectangle2D
import java.awt.{Graphics2D, Graphics}
import javax.swing.{JFrame, JPanel}

object Draw {

  case class DrawEnvironment(g:Graphics, x0:Int, y0:Int,  w:Int, h:Int)

  abstract class Block {
    def draw(i:Int,  de:DrawEnvironment):Int
    def h(de:DrawEnvironment):Int
  }

  case class SmallBlock() extends Block {
    def draw(i:Int,  de:DrawEnvironment) = {
      de.g.drawRect(de.x0, de.y0+i, de.w, 2)
      i + h(de)
    }
    def h(de:DrawEnvironment) = 3
  }
  case class LetterBlock(c:Char) extends Block {
    def draw(i:Int,  de:DrawEnvironment) = {
      de.g.drawRect(de.x0, de.y0+i+1, de.w, de.h)
      de.g.drawString(c.toString, de.x0, de.y0+i+de.h)
      i + h(de)
    }
    def h(de:DrawEnvironment) = de.h+3
  }
  
  def BlockListLenght(bl:List[Block], de:DrawEnvironment):Int = {
    bl.foldLeft(0)((sum, b) => sum + b.h(de))
  }
  
  def drawBlockList(l: List[Block], i: Int,  de:DrawEnvironment) {
    l match {
      case x :: xs => x.draw(i,de); drawBlockList(xs, i + x.h(de), de)
      case Nil =>
    }
  }

  def drawSomeThing(g:Graphics) {
    val g2d:Graphics2D = g.asInstanceOf[Graphics2D]

    val font = g.getFont
    val font2 = font.deriveFont(25.2.toFloat)
    g.setFont(font2)

    val fm = g.getFontMetrics()

    val bb: Rectangle2D = fm.getStringBounds("D",0,1,g)
    val h: Double = (bb.getHeight * 0.8)
    val w: Double = bb.getWidth

    val de = DrawEnvironment(g,200,50,w.toInt,h.toInt)

    val aDrawList = List(
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      LetterBlock('D'),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      LetterBlock('H'),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      LetterBlock('N'),
      LetterBlock('O'),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      LetterBlock('S'),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock(),
      SmallBlock()
    )
    
    g.drawString("Total Height: " + BlockListLenght(aDrawList, de), 20, 20)


    drawBlockList(aDrawList, 0, de)
  }
}

class GamePanel extends JPanel{
  override def paintComponent(g: Graphics) {
    super.paintComponent(g)
    Draw.drawSomeThing(g)
  }
}

object GamePanelMain extends App {
  val f = new JFrame()
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  f.setSize(400,400)
  f.add(new GamePanel())
  f.setVisible(true)
  
  while(true){
    f.repaint()
    Thread.sleep(800)
  }
}