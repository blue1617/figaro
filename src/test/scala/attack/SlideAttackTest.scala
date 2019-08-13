package attack

import attack.AverageProgram.{Age, AverageAge, Name}
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
      },
      generator = i => for {name <- Uniform(dict: _*)
                            age <- Normal(42.0, 20.0)} yield (name, age)
    )
    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)
    print("average_age " + average_age.generateRandomness())

    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)
    // The attacker has seen that the average age was a bit above 20,
    // but does not know precisely
    average_age.addConstraint(a => AverageProgram.averageAgeConstraint((a >= 20.25) && (a < 23.00)))

    val ageOfAlice: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")
    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a < 18.0)

    println("slide attack probability " + attack) //todo: this prints out NaN
  }

}
