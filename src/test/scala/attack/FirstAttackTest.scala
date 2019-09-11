package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import org.scalatest.FlatSpec


/**
  * Created by apreda on 04.03.2019.
  * the scala test has a constant attacker as a model and inferences the posibility of Alice being underage (16)
  */

//todo: add more composition as tests
class FirstAttackTest extends FlatSpec {

  "A First attacker's probability on Alice's age" should "be equal to 1" in {
    Universe.createNew()
    val ageOfAliceElement: Element[Age] = FirstAttack.getAttackElement()

    // How sure is the attacker that "Alice" is underage?
    val attack: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16) //this prints 1.0
    assert(attack == 1.0)

    //    val booleanElement: Element[Boolean] = Apply(ageOfAliceElement, (a: Double) => a == 16)
    //    val beliefElement: Element[Boolean] = Dist(1.0 -> booleanElement)
    //    //
    //    val attackBelief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a < 16)//TODO: this
    //     throws a None exception
    //    assert(attackBelief == 1.0) //this throws an exception


    //example from the book with belief propagation
    //    val e1 = Flip(0.5)
    //    val e21: Element[Boolean] = Apply(e1, (b: Boolean) => b)
    //    val e31: Element[Boolean] = Apply(e21, (b: Boolean) => b)
    //    val e22: Element[Boolean] = Apply(e1, (b: Boolean) => b)
    //    val e32: Element[Boolean] = Apply(e31, (b: Boolean) => b)
    //    val e4: Element[Boolean] = Dist(0.5 -> e31, 0.5 -> e32)
    //    println(BeliefPropagation.probability(e4, true))

    //importance sampling
    //    val importanceSampling = Importance(ageOfAliceElement)
    //    importanceSampling.start()
    //    Thread.sleep(1000)
    //    val attackImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 16)
    //    assert(attackImportanceSampling == 1)
    //    importanceSampling.kill()
  }
}
