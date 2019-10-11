package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element

class BetaAttacker extends Attacker {

  //binomial distribution with parameters n and p is the discrete probability distribution of the number of successes
  // in a sequence of n independent experiments, each asking a yes–no question, and each with its own boolean-valued
  // outcome: success/yes/true/one (with probability p)
  override val names: Seq[Name] = List("John", "Alice", "Tom")
  override val ages: Seq[Age] = List()
  override val populationSize: Int = 3
  override val averageConstraint: Double => Double = ???

  override def generateAttacker(i: Int): Element[(Name, Age)] = ???
//  {
//    val ageProbability = Beta(1, 1)
//
//    val element: Element[(Name, Age)] = Select(ageProbability -> (names(0), ages(0)),
//      ageProbability -> (names(0), ages(1)))
//    element
//  }

  def main(args: Array[String]): Unit = {
    print("Alice underage: " + getAttackProbability(a => a < 18))

  }
}
