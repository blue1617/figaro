package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Element, Select}

/**
  * Created by apreda on 27.02.2019.
  * Select distribution, one element
  */
class ThirdAttacker extends Attacker {


  override val names: Seq[Name] = List("Alice")
  override val ages: Seq[Age] = List(16, 22)
  override val populationSize: Int = 1
  override val averageConstraint: Double => Double = a => AverageProgram.averageAgeConstraint(a == 16)

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    val element: Element[(Name, Age)] = Select(0.5 -> (names(0), ages(0)),
      0.5 -> (names(0), ages(1)))
    element
  }
}
