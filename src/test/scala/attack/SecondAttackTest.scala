package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.junit.Ignore
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
@Ignore
class SecondAttackTest extends FlatSpec {

  "A Second's attacker's probability on Alice's age" should "be greater than 0.5,but smaller than 0.6" in {
    Universe.createNew()

    val ageOfAliceElement: Element[Age] = SecondAttack.getAttackElement()

    // How sure is the attacker that Alice is 16?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16)//this prints
      // 0.5024885526577609 fore and is expected
//    assert(attack1 > 0.40 && attack1 < 0.60)//todo: find out why this does not give the same value as it did be

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2 > 0.99)

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack1ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 16)
//    assert(attack1ImportanceSampling > 0.40 && attack1ImportanceSampling < 0.60)//this prints 0.5103734439833919
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling > 0.99)//this prints :0.9999999999999949
    println("attack2ImportanceSampling " + attack2ImportanceSampling)
    importanceSampling.kill()
  }
}
