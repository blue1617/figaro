package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SecondAttack.{ageAttack1, averageAgeConstraint, generateSecondAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class SecondAttackTest extends FlatSpec {

  "A Second's attacker's probability on Tom's age" should "be greater than 0.5,but smaller than 0.6" in {
    val dict: Seq[Name] = List("John", "Tom")
    val ages: Seq[Age] = List(16, 17)
    //second attacker
    val priorSecondAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateSecondAttacker(dict, ages))
    val priorSecondAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorSecondAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorSecondAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom = priorSecondAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorSecondAttacker)

    // How sure is the attacker that Tom is 16?
    val attack1: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16)//this prints  0.5024885526577609
    assert(attack1 > 0.40 && attack1 < 0.60)

    val attack2: Double = Importance.probability(average_age, (a: Double) => a == 16)//this prints 0.50271465915945
    assert(attack2 > 0.40 && attack2 < 0.60)
  }
}
