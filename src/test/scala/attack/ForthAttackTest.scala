package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ForthAttackTest extends FlatSpec {

  //todo: look into this probability not being 1 after making sure that Alice is always on the list
  "A Forth's attacker's probability on Alice being underage" should "be greater than 0.4" in {
    Universe.createNew()
    val ageOfAliceElement: Element[Age] = new ForthAttack().getAttackElement

    // How sure is the attacker that Alice is 17?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 17)
    assert(attack1 > 0.4)
    print("attack1 " + attack1)

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2 > 0.4)
    print("attack2 " + attack2)
  }
}
