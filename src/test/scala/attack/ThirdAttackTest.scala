package attack

import com.cra.figaro.language.Universe
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class ThirdAttackTest extends FlatSpec {

  "A Third's attacker's probability on Alice's age" should "be greater than 0.5,but smaller than 0.6" in {
    Universe.createNew()
    val attacker: Attacker = new ThirdAttack()

    // How sure is the attacker that Alice is 16?
    val attack1: Double = attacker.getAttackProbability((a: Double) => a == 16)
    assert(attack1 > 0.5)

    val attack2: Double = attacker.getAttackProbability((a: Double) => a < 18)
    assert(attack2 > 0.99)
  }
}
