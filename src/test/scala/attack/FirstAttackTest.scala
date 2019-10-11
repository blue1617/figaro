package attack

import org.scalatest.FlatSpec


/**
  * Created by apreda on 04.03.2019.
  * the scala test has a constant attacker as a model and inferences the posibility of Alice being underage (16)
  */

class FirstAttackTest extends FlatSpec {

  "A First attacker's probability on Alice's age" should "be equal to 1" in {

    val attacker: Attacker = new FirstAttacker()
    // How sure is the attacker that "Alice" is 16?
    val attack: Double = attacker.getAttackProbability(a => a == 16) //this prints 1.0
    assert(attack == 1.0)

    //example from the book with belief propagation
    //    val e1 = Flip(0.5)
    //    val e21: Element[Boolean] = Apply(e1, (b: Boolean) => b)
    //    val e31: Element[Boolean] = Apply(e21, (b: Boolean) => b)
    //    val e22: Element[Boolean] = Apply(e1, (b: Boolean) => b)
    //    val e32: Element[Boolean] = Apply(e31, (b: Boolean) => b)
    //    val e4: Element[Boolean] = Dist(0.5 -> e31, 0.5 -> e32)
    //    println(BeliefPropagation.probability(e4, true))

//    val booleanElement: Element[Boolean] = Apply(ageOfAliceElement, (a: Double) => a == 16)
//    //
//    val attackBelief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a < 16) //TODO: this
//    //    throws a None exception  val beliefElement: Element[Boolean] = Dist(1.0 -> booleanElement)
//    assert(attackBelief == 1.0) //this throws an exception

  }
}
