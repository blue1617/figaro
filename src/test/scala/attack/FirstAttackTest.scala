package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.FirstAttack.generateFirstAttacker
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.{FlatSpec, Matchers, WordSpec}

/**
  * Created by apreda on 04.03.2019.
  * the scala test has a constant attacker as a model and inferences the posibility of Alice being underage (16)
  */

//todo: add more composition as tests
class FirstAttackTest extends FlatSpec {

  "A First attacker's probability on Alice's age" should "be equal to 1" in {
    val names: Seq[Name] = List("Alice")
    val ages: Seq[Age] = List(16)
    val priorFirstAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFirstAttacker(names, ages))
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorFirstAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorFirstAttacker , "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorFirstAttacker, "Alice")

    // How sure is the attacker that "Alice" is underage?
    val attack: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16) //this prints 1.0
    assert(attack == 1.0)
  }
}
