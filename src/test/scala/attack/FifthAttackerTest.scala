package attack

import com.cra.figaro.language.Universe
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class FifthAttackerTest extends FlatSpec {

  "A Fifth's attacker's probability on Alice's age" should "be less than 0.5" in {

    Universe.createNew() // in addition to creating a fresh universe, also sets the default universe to this
    //    new universe. This provides a convenient way to start working with a new universe, put
    //    your new elements in this universe, and have your algorithm run on this universe. (page 247 in the book)

    val attacker: Attacker = new FifthAttacker()


    // How sure is the attacker that Alice is 15?
    val attack1: Double = attacker.getAttackProbability((a: Double) => a == 18)
    assert(attack1 < 0.5) //Alice's age has a normal distribution  Normal(18, 1)
    println("Alice is age 18 " + attack1)

    val attack2: Double = attacker.getAttackProbability((a: Double) => a < 18)
    assert(attack2 < 0.6)
    println("Alice is underage " + attack2)//this prints more than 0.5


    //    val attack1Belief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a == 15)
    //    assert(attack1Belief < 0.5)//we have a uniform distribution on age ranging from 15 to 50
    //    println("attack1Belief " + attack1Belief)
  }
}