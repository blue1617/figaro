package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * There are Binomial(5, 0.3) people in the database. Alice's age is Normal (18, 2) and the other people in the are
  * Normal (42, 2).
  * The constraint on the age is between 20.25 and 23.
  * Created by apreda on 28.02.2019.
  */
class SlideAttacker extends Attacker {

  val names: Seq[Name] = List("John", "Joe", "Bob", "Tom")
  val ages: Seq[Age] = List() //not used
  override val populationSize: Int = 5
  override val averageConstraint: Double => Double = a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a <
    23.00))
  var distributionStandardDeviation: Double = 2

  //todo: plot variations of the attackers by changing the standard deviation on the OX AND on OY HAVE THE
  // PROBABILITY of the attack
  val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
    numItems = Binomial(populationSize, 0.3) map {
      _ + 1
    }, //todo: we currently have a prior on the Alice's age, so let's add a likelihood of average
    generator = i => for {
      n <- if (i == 0) Constant("Alice") else Uniform(names: _*)
      a <- if (i == 0) Normal(18, Math.pow(distributionStandardDeviation, 2)) else Normal(42.0, Math.pow
      (distributionStandardDeviation, 2))}
      yield (n, a)
    // Normal(42.0, 500.0),Normal(20.0, 20.0) 20 is the mean and the variance, which is the square of the standard
    // deviation, which is around 4
    //todo: change the inference algorithm to variable elimination
    //todo: add another continuous distribution
    //my scenarios plot the prior distribution and posterior distribution to show that the attacker has learned a
    // lot from the attack
  )

  override def getPrior: FixedSizeArrayElement[(Name, Age)] = {
    prior
  }

  /**
    * No implementation is needed, since getPrior does not call this function, as it does in the other attackers
    *
    * @param i
    * @return
    */
  override def generateAttacker(i: Int): Element[(Name, Age)] = ???
}
