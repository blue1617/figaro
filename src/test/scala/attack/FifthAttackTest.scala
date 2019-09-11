package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
  * Created by apreda on 04.03.2019.
  */
class FifthAttackTest extends FlatSpec {

  "A Fifth's attacker's probability on Alice's age" should "be less than 0.5" in {

    Universe.createNew()// in addition to creating a fresh universe, also sets the default universe to this
//    new universe. This provides a convenient way to start working with a new universe, put
//    your new elements in this universe, and have your algorithm run on this universe. (page 247 in the book)

    val ageOfAliceElement: Element[Age] = FifthAttack.getAttackElement()
    // How sure is the attacker that Alice is 15?
    val attack1: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 15)
    assert(attack1 < 0.5) //we have a uniform distribution on age ranging from 15 to 50
    println("attack1 " + attack1)

    //    val attack1Belief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a == 15)
    //    assert(attack1Belief < 0.5)//we have a uniform distribution on age ranging from 15 to 50
    //    println("attack1Belief " + attack1Belief)

    val attack1Prime: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 50)
    assert(attack1Prime < 0.3)
    println("attack1Prime " + attack1Prime)

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18) //this prints 0.06813750641354209
    assert(attack2 < 0.5)

    //importance sampling
    val importanceSampling = Importance(ageOfAliceElement)
    importanceSampling.start()
    Thread.sleep(1000)
    val attack1ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a == 50)
    assert(attack1ImportanceSampling < 0.3)
    //this prints 0.5103734439833919
    val attack2ImportanceSampling: Double = importanceSampling.probability(ageOfAliceElement, (a: Double) => a < 18)
    assert(attack2ImportanceSampling < 0.5) //this prints :0.06966138410671595
    importanceSampling.kill()
  }
}