package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.ThirdAttack.{ageAttack1, averageAgeConstraint, generateThirdAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ThirdAttackTest extends FlatSpec {

  "A Third's attacker's probability on Tom's age" should "be greater than 0.5,but smaller than 0.6" in {
    //third attacker
    val priorThirdAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateThirdAttacker())
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorThirdAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom = priorThirdAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorThirdAttacker)

    // How sure is the attacker that Tom is 16?
    val attack1: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16) //this prints 0.51105305466237
    assert(attack1 > 0.50 && attack1 < 0.60)

    val attack2: Double = Importance.probability(average_age, (a: Double) => a == 16) //this prints 0.49880430450377383
    assert(attack2 > 0.50 && attack2 < 0.60)
  }
}
