package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.language.{Element, Select}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 06.03.2019.
  */
object ForthAttack {
  def averageAgeConstraint(a: AverageAge): Double = {
    if (a >= 15 && a <= 20)
      1000.0
    else
      0.0
  }

  def ageAttack1(priorKnowledge: FixedSizeArrayElement[(Name, Age)]): Element[Age] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == "Tom"
      } map {
        _ getOrElse -1
      }
      tom <- container get idx
      age = tom map (_._2)
    } yield age getOrElse -1
  }


  def generateForthAttacker(dict: Seq[Name]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(15, 20)} yield (name, a)
  }
}
