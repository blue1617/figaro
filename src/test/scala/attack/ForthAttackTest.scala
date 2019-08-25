package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.ForthAttack.generateForthAttacker
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ForthAttackTest extends FlatSpec {

  "A Forth's attacker's probability on Alice's age" should "be greater than 0.5" in {
    val dict: Seq[Name] = List("Jhon", "Alice")
    //forth attacker
    val priorForthAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateForthAttacker(dict))
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorForthAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Tom should be in the list
    val seenAlice: Element[Boolean]  = AverageProgram.isNameInArrayElement(priorThirdAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a >= 15 && a <= 18))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorThirdAttacker, "Alice")

    // How sure is the attacker that Alice is 15?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1 > 0.5)

    val attack2: Double = Importance.probability(average_age, (a: Double) => a < 18)
    assert(attack2 > 0.99)
    print("attack2 " + attack2)

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack1ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1ImportanceSampling > 0.5)//this prints 0.5103734439833919
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling > 0.99)//this prints :0.9999999999999949
    println("attack2ImportanceSampling " + attack2ImportanceSampling)
    importanceSampling.kill()
  }
}
