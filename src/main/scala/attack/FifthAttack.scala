package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 13.08.2019.
  */
class FifthAttack extends Attacker {
//todo: add more examples, at least 5, maybe 10;
  val names: Seq[Name] = List("John", "Alice")
  val ages: Seq[Age] = List.tabulate(36)(_ + 15) //age from 15 to 51

 override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}
