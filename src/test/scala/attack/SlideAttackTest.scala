package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 06.03.2019.
  */
class SlideAttackTest extends FlatSpec {

  "The attacker from Andrzej's slide " should "return a probability higher than 0.99" in {
    Universe.createNew()

    val ageOfAliceElement: Element[Age] = SlideAttack.getAttackElement
    // How sure is the attacker that Alice is underage?
    ageOfAliceElement.setCondition((a: Double) => a < 18.0)
    // How sure is the attacker that Alice is underage?
    val attackVariableElimination: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18.0)

    println("slide attack probability " + attackVariableElimination) //this prints out NaN with Normal(42.0, 20.0) since the constraint
//     says that the range is between 20 and 23
    assert(attackVariableElimination > 0.99)

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
