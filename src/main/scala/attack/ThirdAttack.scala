package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Element, Select}

/**
  * Created by apreda on 27.02.2019.
  */
class ThirdAttack extends Attacker {


  override val names: Seq[Name] = List("Alice")
  override val ages: Seq[Age] = List(16, 22)

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    val element: Element[(Name, Age)] = Select(0.5 -> (names(0), ages(0)),
      0.5 -> (names(0), ages(1)))
    element
  }
}
