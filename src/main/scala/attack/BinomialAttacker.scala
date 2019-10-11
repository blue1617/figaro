package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}

class BinomialAttacker extends Attacker {

  //binomial distribution with parameters n and p is the discrete probability distribution of the number of successes
  // in a sequence of n independent experiments, each asking a yes–no question, and each with its own boolean-valued
  // outcome: success/yes/true/one (with probability p)
  override val names: Seq[Name] = List("John", "Alice", "Tom")
  override val ages: Seq[Age] = List()
  override val populationSize: Int = 3
  override val averageConstraint: Double => Double = ???

  override def generateAttacker(i: Int): Element[(Name, Age)] = {
    for {name <- Uniform(names: _*)
         a <- Binomial(35, 0.5)} yield (name, a)
  }
}
