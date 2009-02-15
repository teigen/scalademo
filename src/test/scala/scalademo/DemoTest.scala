package scalademo

/* Copyright [yyyy] [name of copyright owner]
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
object DemoTest extends Application with Demo {
  demo("simple"){
    "a simple demo"
  }

  demo("addition 2 + 2"){
    2 + 2
  }

  demo("multiplication 2 * 2"){
    2 * 2
  }

  println("# list all")
  list(_ => true)

  println("# run first")
  run(_.index == 0)

  println("# list all with name containing 2")
  list(_.name contains "2")

  println("# run all containing *")
  run(_.name contains "*")

//repl //for executing demos from the prompt

}