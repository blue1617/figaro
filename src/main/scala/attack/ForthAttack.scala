package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 06.03.2019.
  */
class ForthAttack extends Attacker {

  val names: Seq[Name] = List("John", "John","John","John")
  val ages: Seq[Age] = List() //not used
  override def generateAttacker(i: Int): Element[(Name, Age)] = {

    if (i == 0)
      for {name <- Uniform(names: _*)
           a <- Uniform(17, 19)} yield ("Alice", a)
    else for {name <- Uniform(names: _*)
              a <- Uniform(17, 19)} yield (name, a)
    //todo: try Other built-in continuous atomic classes include Normal,
    // Exponent-
    //ial, Gamma, Beta , and Dirichlet
    //Other built-in continuous atomic classes include Normal, Exponent-
    //ial, Gamma, Beta , and Dirichlet , also found in the library.atomic.-
    //continuous package, while discrete elements include discrete Uniform,
    //Geometric, Binomial , and Poisson , to be found in the library.atom-
    //ic.discrete package.
  }
}
