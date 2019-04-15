package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.FirstAttack.{ageAttack1, averageAgeConstraint, generateFirstAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.{FlatSpec, Matchers, WordSpec}

/**
  * Created by apreda on 04.03.2019.
  *
  *   //scala test if I call on this on line 99 is 0, under 42 I should get a probability of 1
  // should include model, inference, constant attacker, age of Alice, everything
  // that is in main , add more composition as tests
  //a third attacker model

  */
class FirstAttackTest extends FlatSpec {

  "A First attacker's probability on Tom's age" should "be equal to 1" in {
    val dict: Seq[Name] = List("Tom")
    val ages: Seq[Age] = List(16)
    //first attacker
    val priorFirstAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFirstAttacker(dict, ages))
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorFirstAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom: Element[Boolean]  = priorFirstAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorFirstAttacker)

    // How sure is the attacker that Tom is underage?
    val attack: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16)//this prints 1.0
    assert(attack == 1.0)
  }
}
