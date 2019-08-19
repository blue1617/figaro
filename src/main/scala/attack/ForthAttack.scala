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

  def generateForthAttacker(dict: Seq[Name]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(15, 50)} yield (name, a)//todo: try Other built-in continuous atomic classes include Normal, Exponent-
    //ial, Gamma, Beta , and Dirichlet
  }
}
