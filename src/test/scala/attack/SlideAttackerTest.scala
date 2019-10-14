package attack

import attack.AverageProgram.Age
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Element, Universe}
import org.scalatest.FlatSpec

/**
 * Created by apreda on 06.03.2019.
 */
class SlideAttackerTest extends FlatSpec {

  "The attacker from Andrzej's slide " should "return a probability higher than 0.99" in {
    Universe.createNew() // in addition to creating a fresh universe, also sets the default universe to this
    //    new universe. This provides a convenient way to start working with a new universe, put
    //    your new elements in this universe, and have your algorithm run on this universe. (page 247 in the book)


    val ageOfAliceElement: Element[Age] = new SlideAttacker(2).getAttackElement
    //    ageOfAliceElement.setCondition((a: Double) => a < 18.0) todo: adding this condition lifts the inference to 9.99
    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18.0)

    println("slide attack probability " + attack) //this prints out NaN with Normal(42.0, 20.0) since the constraint
    //     says that the range is between 20 and 23 and the Normal distribution says that the average of the age is 42
    //     and the standard deviation is sqrt(20), around 4, so the range is between 38 and 46

    assert(attack < 0.1) // this prints 0.024948317749016163
  }

}
