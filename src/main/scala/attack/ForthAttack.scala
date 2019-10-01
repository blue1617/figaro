package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 06.03.2019.
  */
class ForthAttack extends Attacker {

  val names: Seq[Name] = List("John", "Alice")
  val ages: Seq[Age] = List()//not used

  override def generateAttacker(): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(15, 50)} yield (name, a) //todo: try Other built-in continuous atomic classes include Normal, Exponent-
    //ial, Gamma, Beta , and Dirichlet
  }
}
