package scalademo

import scala.collection.mutable.ArrayBuffer
/*

 */
trait Demo {
  private[this] case class Demoable(description: String, run: () => Any)
  case class Demonstration(name: String, index: Int)

  private[this] val demonstrations = new ArrayBuffer[Demoable]

  private[this] def pretty(description: String) = "[" + description + "]"

  private[this] def width = (0 /: demonstrations)((biggest, demoable) => biggest max demoable.description.length)

  private[this] def findMatching(criteria: Demonstration => Boolean) =
    demonstrations
            .toList
            .zipWithIndex
            .filter(t => criteria(Demonstration(t._1.description, t._2)))
            .map(_._1)

  private[this] def demonstrate(demonstration: Demoable) {
    display(demonstration)
    println(demonstration.run())
    println("-" * (width + 3 + demonstrations.length.toString.length))
  }

  private[this] def display(demonstration: Demoable) {
    println(demonstrations.indexOf(demonstration) + " " + pretty(demonstration.description))
  }

  def demo(description: String)(f: => Any) {
    demonstrations += Demoable(description, () => f)
  }

  def run(criteria: Demonstration => Boolean) {
    findMatching(criteria).foreach(demonstrate)
  }

  def list(criteria: Demonstration => Boolean) {
    findMatching(criteria).foreach(display)
  }

  def startsWith(names: List[String]) =
    (demonstration: Demonstration) => names.exists(name => demonstration.name.toUpperCase.startsWith(name.toUpperCase))

  def repl {
    print("> ")
    readLine() split ("""\s""") toList match {
      case "quit" :: _ => println("good bye")
      case "list" :: Nil => list(_ => true); repl
      case "run" :: Nil => run(_ => true); repl
      case "list" :: names => list(startsWith(names)); repl
      case "run" :: names => run(startsWith(names)); repl
      case "run:i" :: indexes => run(d => indexes.exists(d.index == _.toInt)); repl
      case unknown => println("unknown " + unknown); repl
    }
  }
}
