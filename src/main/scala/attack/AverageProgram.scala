package attack

import com.cra.figaro.language.Element
import com.cra.figaro.library.collection.FixedSizeArrayElement

/**
  * Created by apreda on 27.02.2019.
  */
object AverageProgram {

  type Name = String
  type Age = Double
  type AverageAge = Double

  def alpha_p(records: FixedSizeArrayElement[(Name, Age)]):
  Element[Age] = {
    val averageAge: Element[Age] = records
      .map { case (n, a) => (a, 1) }
      .reduce { case (x, y) => (x._1 + y._1, x._2 + y._2) }
      .map { case (sum, count) => sum / count }
    averageAge
  }

  def isNameInArrayElement(records: FixedSizeArrayElement[(Name, Age)], name: String):
  Element[Boolean] = {
    records exists { case (s, a) => s == name }
  }

//todo: add Alice in some of attackers, but not all
  def retrieveAge(priorKnowledge: FixedSizeArrayElement[(Name, Age)], name: String): Element[Age] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == name
      } map {
        _ getOrElse -1
      }
      alice <- container get idx
      a = alice map (_._2)
    } yield a getOrElse -1
  }

  /**
    *
    * The attacker has seen that the average age was a bit above 20, but does not know precisely. Rather than
    * specifying hard evidence that average_age is a certain value, 22, for example, one can specify soft
    * evidence that average_age is most likely to be between 20.25 and 23. The age range of 20.25 to 23 is 1000 times more likely than any other age.
    * Constraint can be seen as either a soft evidence, or a way of providing additional connections between
    * elements in a model. A constraint is added when one has some evidence about an element, but is not quite sure
    * about it.The probability of the average
    * age to be in the given range is 1000 more likely than outside it
    *
    * @param constraint given constraint (e.g. a == 16)
    * @return the average age with the intended constraint
    */
  def averageAgeConstraint(constraint: Boolean): Double = {
    if (constraint)
      1
    else
      0.10//todo: look into using a small number but not zero here,check the book, page 54
  }
}
