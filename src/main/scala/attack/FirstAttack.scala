package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.discrete.Uniform

class FirstAttack extends Attacker {

  val names: Seq[Name] = List("Alice")
  val ages: Seq[Age] = List(16)

  override def generateAttacker(): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}


