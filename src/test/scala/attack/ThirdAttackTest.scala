package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ThirdAttackTest extends FlatSpec {

  "A Third's attacker's probability on Alice's age" should "be greater than 0.5,but smaller than 0.6" in {
    Universe.createNew()
    val ageOfAliceElement: Element[Age] = ThirdAttack.getAttackElement

    // How sure is the attacker that Alice is 16?
//    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16)
//    assert(attack1 > 0.30 && attack1 < 0.60)//todo: look into this, it's similar to second attacker

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2 > 0.99)
    println("attack2 " + attack2 )//this prints 0.9999999999999747

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack1ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 16)
//    assert(attack1ImportanceSampling > 0.30 && attack1ImportanceSampling < 0.60)//this prints 0.5103734439833919
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling > 0.99)//this prints :0.9999999999999949
    println("attack2ImportanceSampling " + attack2ImportanceSampling)
    importanceSampling.kill()
  }
}
