import java.awt.event.{MouseEvent, MouseMotionListener}
import java.awt.geom.Rectangle2D
import java.awt.{Graphics2D, Graphics}
import javax.swing.{JFrame, JPanel}

object Draw {

  case class DrawEnvironment(g:Graphics, x0:Int, y0:Int,  w:Int, h:Int)

  abstract class Block {
    def draw(i:Int,  de:DrawEnvironment):Int
    def h(de:DrawEnvironment):Int
  }

  def delay = 50

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

  def drawSomeThing(g:Graphics, i:Int, mousePos:(Int, Int)) {
    val g2d:Graphics2D = g.asInstanceOf[Graphics2D]
    
    val font = g.getFont
    val font2 = font.deriveFont(20.toFloat)
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
    
    val letterList = List(
      ('A', 100),
      ('B', 10),
      ('C', 50),
      ('D', 10),
      ('E', 10),
      ('F', 10),
      ('G', 10),
      ('H', 80),
      ('I', 10),
      ('J', 200),
      ('K', 10),
      ('L', 10),
      ('M', 90),
      ('N', 10),
      ('O', 10),
      ('P', 10),
      ('Q', 10),
      ('R', 70),
      ('S', 10),
      ('T', 10),
      ('U', 20),
      ('V', 10),
      ('W', 10),
      ('X', 30),
      ('Y', 10),
      ('Z', 10)
    )

    // ugly as hell
    def generateBlockList(ll:List[(Char, Int)], nrOfFullChars:Int):List[Block] = {
      val nl = 1.to(ll.length)
      val zl = nl.zip(ll)
      val sl = zl.sortBy(-_._2._2)

      def repl(leftNr:Int, lll:List[(Char, Int)]):List[(Int, Block)] = {
        lll match {
          case x :: xs => {
            if (leftNr > 0) {
              (x._2, LetterBlock(x._1)) :: repl(leftNr - 1, xs)
            } else {
              (x._2, SmallBlock()) :: repl(leftNr - 1, xs)
            }
          }
          case _ => Nil
        }
        
      }
      
      val ml = sl.map(t => (t._2._1, t._1)).toList
      
      val ul = repl(nrOfFullChars, ml)
      val uls = ul.sortBy(_._1)
      uls.map(_._2)

    }

    // even uglyer doesn't need to be a method its only a replace
    def modLetterList(index: Int, ll:List[(Char, Int)]):List[(Char, Int)]  = {
      def local(i: Int, l:List[(Char, Int)]):List[(Char, Int)] = {
        l match {
          case x :: xs => {
            if(i != 0) {
              x :: local(i - 1, xs)
            } else {
              x.copy(_2 = x._2 + 100) :: local(i - 1, xs)
            }                      
          }
          case _ => Nil
        }
      }

      local(index, ll)
    }

    val selctedLetter = (mousePos._2 / 400.0 * 26 + 1).toInt;

    val aSecondDrawList = generateBlockList(modLetterList(selctedLetter,letterList), 2)

    g.drawString("Total Height: " + BlockListLenght(aSecondDrawList, de), 20, 20)
    g.drawString("Mouse x: " + mousePos._1 + ", y: " + mousePos._2, 20, 40)
    g.drawString("Selected Letter: " + selctedLetter, 20, 60)

    drawBlockList(aSecondDrawList, 0, de)
  }
}

class GamePanel extends JPanel with MouseMotionListener{
  var i = 1
  
  var mousePos = (0,0)

  def mouseDragged(p1: MouseEvent) {
    mousePos = (p1.getX, p1.getY+1000)
  }

  def mouseMoved(p1: MouseEvent) {
    mousePos = (p1.getX, p1.getY)
  }

  override def paintComponent(g: Graphics) {
    super.paintComponent(g)
    Draw.drawSomeThing(g,i, mousePos)
    i = if(i > 26 || i < 0) {
      1
    }   else {
      i + 1
    }
  }
}

object GamePanelMain extends App {
  val f = new JFrame()
  val g = new GamePanel()
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  f.setSize(400,400)
  f.add(g)
  f.addMouseMotionListener(g)
  f.setVisible(true)
  
  while(true){
    f.repaint()
    Thread.sleep(Draw.delay)
  }
}