package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ForthAttackerTest extends FlatSpec {

  "A Forth's attacker's probability on Alice being underage" should "be greater than 0.4" in {
    Universe.createNew()
    val ageOfAliceElement: Element[Age] = new ForthAttacker().getAttackElement

    // How sure is the attacker that Alice is 17?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 17)
    println("How sure is the attacker that Alice is 17 " + attack1)
    assert(attack1 < 0.6)


    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    println("How sure is the attacker that Alice is underage " + attack2)
    assert(attack2 > 0.4)
  }
}
