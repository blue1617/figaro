package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.FifthAttack.generateFifthAttacker
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class FifthAttackTest extends FlatSpec {

  "A Fifth's attacker's probability on Alice's age" should "be less than 0.5" in {
    val dictNames: Seq[Name] = List("John", "Alice")
    val dictAges: Seq[Age] = List.tabulate(36)(_ + 15)
    val priorFixedSizeArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
        generateFifthAttacker(dictNames, dictAges))//
    val prior: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorFixedSizeArray))

    // This is what we know about aver+age age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)
    // The attacker knows that Tom should be in the list
    val seenAlice: Element[Boolean]  = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a >= 19))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")

    // How sure is the attacker that Alice is 15?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1 < 0.5)//we have a uniform distribution on age ranging from 15 to 50
    println("attack1 " + attack1)

    val attack1Prime: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 50)
    println("attack1Prime " + attack1Prime)

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)//this prints 0.06
    assert(attack2 < 0.5)
    println("attack2 " + attack2)

  }
}