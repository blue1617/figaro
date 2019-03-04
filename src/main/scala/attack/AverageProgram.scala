package attack

import com.cra.figaro.language.{Constant, Element}
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

  def getElement(ageOption: Element[Option[Age]]): Element[Age] = {
    ageOption.value match {
      case None => Constant(-1)
      case null => Constant(-1)
      case _ => Constant(ageOption.value.get)
    }
  }
}
