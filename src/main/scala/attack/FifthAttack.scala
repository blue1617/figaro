package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 13.08.2019.
  */
class FifthAttack extends Attacker {

  val names: Seq[Name] = List("John", "Alice")
  val ages: Seq[Age] = List.tabulate(36)(_ + 15) //age from 15 to 51

 override def generateAttacker(): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }

  def main(args: Array[String]): Unit = {
    getAttackProbability(a => a < 18)
  }
}
