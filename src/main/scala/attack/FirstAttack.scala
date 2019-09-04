package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.discrete.Uniform

object FirstAttack {

  def generateFirstAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}


