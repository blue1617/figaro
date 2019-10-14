package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 06.03.2019.
  * Uniform distribution - Alice is 16 , five elements and the attacker that observes an average between 16 and 17
  */
class ForthAttacker extends Attacker {

  override val names: Seq[Name] = List("John", "John", "John", "John")
  override val ages: Seq[Age] = List() //not used
  override val populationSize: Int = 5
  override val averageConstraint: Double => Double = a => AverageProgram.averageAgeConstraint((a >= 16) && (a <
    17))

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {
      n <- if (i == 0) Constant("Alice") else Uniform(names: _*)
      a <- if (i == 0) Uniform(10,50) else Uniform(10, 50)}
      yield (n, a)
    // yield  n -> a //NOTE: Syntactic sugar for the line above

    //todo: try Other built-in continuous atomic classes include Normal,
    // Exponent-
    //ial, Gamma, Beta , and Dirichlet, also found in the library.atomic.-
    //continuous package, while discrete elements include discrete Uniform,
    //Geometric, Binomial , and Poisson , to be found in the library.atom-
    //ic.discrete package.
  }
}
