package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SecondAttack.{ageAttack1, averageAgeConstraint, generateSecondAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement, VariableSizeArray}

object FirstAttack {


  def averageAgeConstraint(a: AverageAge): Double = {
    if (a == 16)
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


  def generateFirstAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}


