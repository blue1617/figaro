package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 13.08.2019.
  */
class FifthAttacker extends Attacker {

  override val names: Seq[Name] = List("John", "Alice")//todo: add more examples, at least 5, maybe 10;
  override val ages: Seq[Age] = List.tabulate(36)(_ + 15) //age from 15 to 51
  override val populationSize: Int = 10
  override val averageConstraint: Double => Double =  a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a <
    23.00))

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {
      n <- if (i == 0) Constant("Alice") else Uniform(names: _*)
      a <- if (i == 0) Normal(40.2, 10) else Uniform(ages: _*)} // What if change to `age`
      yield (n, a)
    // yield  n -> a //NOTE: Syntactic sugar for the line above
  }
}
