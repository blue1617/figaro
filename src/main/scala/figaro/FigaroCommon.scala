package figaro

import attack.AverageProgram.{Age, AverageAge}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.Element

object FigaroCommon {

  /**
    * Importance.sampleJointPosterior(ageOfAliceElement) returns a stream
    * e.g. Stream(List(-1.0), ?)
    *
    * @param element the element to get the sample value from using the Importance algorithm
    * @return
    */
  def getSampleValue (element: Element[AverageAge]): List[Age] = {
    Importance.sampleJointPosterior(element).map { case h :: _ => h;
    case _ =>
      ???
    }
      .map { case n: Double => n; case _ => ??? }
      .take(1)
      .toList
  }
}
