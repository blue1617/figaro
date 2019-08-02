package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element, Select}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 27.02.2019.
  */
object ThirdAttack {

  def generateThirdAttacker(): Element[(Name, Age)] = {
    val element: Element[(Name, Age)] = Select(0.5 -> ("Tom", 17.toDouble),
      0.5 -> ("Tom", 16.toDouble))
    element
  }
}
