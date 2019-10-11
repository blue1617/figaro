package attack

import com.cra.figaro.language.Universe
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  * There is only one person in the list - Alice - with the age of 16/22, the attacker knows that Alice is in the
  * list, he finds out the average 16/22 and he is still not 100% sure if Alice is underage or not, why?
  */
class ThirdAttackTest extends FlatSpec {

  "A Third's attacker's probability on Alice's age" should "be greater than 0.99" in {
    Universe.createNew()
    val attacker: Attacker = new ThirdAttacker()

    // How sure is the attacker that Alice is 16?
    val attack1: Double = attacker.getAttackProbability((a: Double) => a == 16)
    assert(attack1 > 0.49)

    val attack2: Double = attacker.getAttackProbability((a: Double) => a < 18)
    assert(attack2 > 0.99)//* todo: find out why this fails, it returns 0.91, it must have something to do with the
    // constraint added on the average age element in the model
  }
}
