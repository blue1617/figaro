package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 28.02.2019.
  */
class SlideAttack extends Attacker {

  val names: Seq[Name] = List("John", "Alice", "Joe", "Bob", "Tom")
  val ages: Seq[Age] = List()//not used

  val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
    numItems = Binomial(3, 0.3) map {
      _ + 1
    },//todo: we currently have a prior on the Alice's age, so let's add a likelihood of average
    generator = i => for {name <- Uniform(names: _*)
                          age <- Normal(20.0, 20.0)} yield (name, age)
    //todo: change the inference algorithm to variable elimination
    //todo: add another continuous distribution Normal(42.0, 500.0),Normal(20.0, 20.0) 20 is the mean and the
    //        variance, which is the square of the standard deviation is 20 (around 4)
    //my scenarios plot the prior distribution and posterior distribution to show that the attacker has learned a
    // lot from the attack
  )
  // This is what we know about average age before any observation

  override def getAttackElement: Element[Age] = {
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)

    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)
    // The attacker has seen that the average age was a bit above 20,
    // but does not know precisely
    average_age.addConstraint(a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a < 23.00)))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")
    ageOfAliceElement
  }

  def generateAttacker(): Element[(Name, Age)] = {
    ???
  }
}
