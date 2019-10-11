package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 27.02.2019.
  */
class SecondAttack extends Attacker {
  val names: Seq[Name] = List("John", "Alice")
  val ages: Seq[Age] = List(16, 17)
  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}
