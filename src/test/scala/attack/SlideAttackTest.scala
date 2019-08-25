package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.factored.beliefpropagation.{BeliefPropagation, MPEBeliefPropagation, OneTimeProbabilisticBeliefPropagation}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 06.03.2019.
  */
class SlideAttackTest extends FlatSpec {

  "The attacker from Andrzej's slide " should "not return a NaN as it does now" in {
    val dict: Seq[Name] = List("John", "Alice", "Joe", "Bob", "Tom")
    val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
      numItems = Binomial(3, 0.3) map {
        _ + 1
      },//todo: we curently have a prior on the Alice's age and add a likelihood of average
      generator = i => for {name <- Uniform(dict: _*)
                            age <- Normal(20.0, 20.0)} yield (name, age)
      //todo: change the inference algorithm (variable elimination)
      //add another continuous distribution Normal(42.0, 500.0),Normal(20.0, 20.0) 20 is the mean and the
//        variance, which is the square of the standard deviation is 20 (around 4)
      //my scenarios plot the prior distribution and posterior distribution to show that the attacker has learned a
      // lot from the attack
    )
    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)

    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)
    // The attacker has seen that the average age was a bit above 20,
    // but does not know precisely
    average_age.addConstraint(a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a < 23.00)))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")
    // How sure is the attacker that Alice is underage?
    ageOfAliceElement.setCondition((a: Double) => a < 18.0)
    val attackVariableElimination: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18.0)

    println("slide attack probability " + attackVariableElimination) //this prints out NaN with Normal(42.0, 20.0) since the constraint
//     says that the range is between 20 and 23

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling > 0.99)//this prints :0.9999999999999949
    println("attack2ImportanceSampling " + attack2ImportanceSampling)
    importanceSampling.kill()
  }

}
