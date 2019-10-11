package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Constant distribution, one element
  */
class FirstAttacker extends Attacker {

  val names: Seq[Name] = List("Alice")
  val ages: Seq[Age] = List(16)
  override val populationSize: Int = 1
  override val averageConstraint: Double => Double = a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a <
    23.00))

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}


