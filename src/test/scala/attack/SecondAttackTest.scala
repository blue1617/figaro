package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SecondAttack.generateSecondAttacker
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class SecondAttackTest extends FlatSpec {

  "A Second's attacker's probability on Alice's age" should "be greater than 0.5,but smaller than 0.6" in {
    val dict: Seq[Name] = List("John", "Alice")
    val ages: Seq[Age] = List(17, 16)
    val priorSecondAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateSecondAttacker(dict, ages))
    val priorSecondAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorSecondAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorSecondAttacker)
    // The attacker knows that Alice isin the list
    val seenAlice: Element[Boolean]  = AverageProgram.isNameInArrayElement(priorSecondAttacker , "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16|| a == 17))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorSecondAttacker, "Alice")

    // How sure is the attacker that Alice is 16?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16)//this prints  0.5024885526577609
    assert(attack1 > 0.40 && attack1 < 0.60)

    val attack2: Double = Importance.probability(average_age, (a: Double) => a < 18)//this prints 0.50271465915945
    assert(attack2 > 0.99)//todo: this requires 1 to be returned, I have to look into it
  }
}
