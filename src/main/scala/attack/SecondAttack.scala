package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 27.02.2019.
  * Constant distribution, two elements
  */
class SecondAttack extends Attacker {
  val names: Seq[Name] = List("John")
  val ages: Seq[Age] = List(17)
  override val populationSize: Int = 2
  override val averageConstraint: Double => Double = a => AverageProgram.averageAgeConstraint(a == 16 || a==17)

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {
      n <- if (i == 0) Constant("Alice") else Uniform(names: _*)
      a <- if (i == 0) Constant(16.toDouble) else Uniform(ages: _*)}
      yield (n, a)
    // yield  n -> a //NOTE: Syntactic sugar for the line above
  }
}
