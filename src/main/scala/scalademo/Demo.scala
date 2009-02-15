package scalademo

import scala.collection.mutable.ArrayBuffer

/* Copyright [2009] [Jon-Anders Teigen]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
trait Demo {
  private[this] case class Demoable(description: String, run: () => Any)
  case class Demonstration(name: String, index: Int)

  private[this] val demonstrations = new ArrayBuffer[Demoable]

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
    dashes()
  }

  private [this] def dashes(){
    println("-" * (width + 3 + demonstrations.length.toString.length))
  }

  private[this] def display(demonstration: Demoable) {
    println("[" + demonstrations.indexOf(demonstration) + " " + demonstration.description + "]")
  }

  def demo(description: String)(f: => Any) {
    demonstrations += Demoable(description, () => f)
  }

  def run(criteria: Demonstration => Boolean) {
    findMatching(criteria).foreach(demonstrate)
  }

  def list(criteria: Demonstration => Boolean) {
    findMatching(criteria).foreach(display)
    dashes()
  }

  def startsWith(names: List[String]) =
    (demonstration: Demonstration) => names.exists(name => demonstration.name.toUpperCase.startsWith(name.toUpperCase))

  def repl {
    readLine("> ") split ("""\s""") toList match {
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
