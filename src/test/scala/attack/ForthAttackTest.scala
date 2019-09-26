package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ForthAttackTest extends FlatSpec {

  "A Forth's attacker's probability on Alice's age" should "be greater than 0.5" in {
    Universe.createNew()
    val ageOfAliceElement: Element[Age] = ForthAttack.getAttackElement

    // How sure is the attacker that Alice is 15?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1 > 0.5)

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2 > 0.99)
    print("attack2 " + attack2)

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack1ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1ImportanceSampling > 0.5) //this prints 0.5103734439833919
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling > 0.99) //this prints :0.9999999999999949
    println("attack2ImportanceSampling " + attack2ImportanceSampling)
    importanceSampling.kill()
  }
}
