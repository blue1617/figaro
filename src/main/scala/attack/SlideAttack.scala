package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 28.02.2019.
  */
object SlideAttack {


  def averageAgeConstraint(a: AverageAge): Double = {
    if ((a >= 20.25) && (a < 23.00)) 1000.0 else 0.0
  }

  def slideAttack1(priorKnowledge: FixedSizeArrayElement[(Name, Age)]): Element[Age] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == "Alice"
      } map {
        _ getOrElse -1
      }
      alice <- container get idx
      a = alice map (_._2)
    } yield a getOrElse -1
  }
}
