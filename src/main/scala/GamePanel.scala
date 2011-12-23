import java.awt.{Graphics}
import javax.swing.{JFrame, JPanel}

object Draw {
  def drawSomeThing(g:Graphics) {
    g.drawRect(40,40,40,2)
    g.drawRect(40,44,40,2)
    g.drawRect(40,48,40,2)
    g.drawRect(40,50,40,40)
    
    val fm = g.getFontMetrics()
    val w = fm.charWidth('D')
    val h = fm.getHeight()
    g.drawString("D",60 + w/2,60+h/2)
    g.drawRect(40,92,40,2)
    g.drawRect(40,94,40,2)
    g.drawRect(40,96,40,40)
    g.drawRect(40,138,40,2)
    g.drawRect(40,142,40,2)
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
    Thread.sleep(80)
  }
}