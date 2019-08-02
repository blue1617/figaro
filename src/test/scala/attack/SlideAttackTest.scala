package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SlideAttack.{averageAgeConstraint, slideAttack1}
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
    val dict: Seq[Name] = List("John", "Alice", "Joe", "Bob", "Alice")
    val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
      numItems = Binomial(300, 0.3) map {
        _ + 1
      },
      generator = i => for {n <- Uniform(dict: _*)
                            a <- Normal(42.0, 20.0)} yield (n, a)
    )
    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)

    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)
    // The attacker has seen that the average age was a bit above 20,
    // but does not know precisely
    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfAlice: Element[Age] = slideAttack1(prior)
    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a >= 42.0)

    println("slide attack probability " + attack) //todo: this prints out NaN
  }

}
