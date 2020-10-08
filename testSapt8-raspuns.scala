import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
object hpf{
  def main(args: Array[String]){
    // cele trei componente ale jocului
    val h = "hartie"
    val p = "piatra"
    val f = "foarfece"
    // relatia de comparatie
    def maiMare(s1: String, s2: String) : Boolean =
      (s1 == h && s2 == p) ||
      (s1 == p && s2 == f) ||
      (s1 == f && s2 == h) 
    
    // jucatorii
    val jucator1 = Uniform(h, p, f) 
    val jucator2 = Uniform(h, p, f)
    // joc cu o singura runda
    val jocORunda = Apply(jucator1, jucator2, (v1:String, v2:String) =>
      if (v1 == v2) "egal"
      else if (maiMare(v1, v2)) "jucator1"
      else "jucator2")
    // afisare probabilitate
    println("Joc intr-o runda: " + VariableElimination.probability(jocORunda, "egal"))
    
    // strategia primului jucator pentru runda a doua
    def str1(v: String) : Element[String] = {
      (v) match {
        case "piatra" => Select(0.4 -> h, 0.4 -> p, 0.2 -> f) 
	case "hartie" => Select(0.4 -> h, 0.3 -> p, 0.3 -> f)  
	case "foarfece" => Select(0.2 -> h, 0.3 -> p, 0.5 -> f)
      }
    }
    // strategia celui de-al doiles jucator pentru runda a doua
    def str2(v: String) : Element[String] = {
      (v) match {
        case "piatra" => Select(0.3 -> h, 0.4 -> p, 0.3 -> f) 
	case "hartie" => Select(0.2 -> h, 0.6 -> p, 0.2 -> f)  
	case "foarfece" => Select(0.3 -> h, 0.3 -> p, 0.4 -> f)
      }
    }
    // joc cu doua runde
    val joc2Runde = Chain(jucator1, jucator2, (v1:String, v2:String) =>
      if (v1 == v2) Apply(str1(v1), str2(v2), (v12:String, v22:String) =>
        if (v12 == v22) "egal"
        else if (maiMare(v12, v22)) "jucator1"
        else "jucator2")
      else if (maiMare(v1, v2)) Constant("jucator1")
      else Constant("jucator2")) 
    // afisare probabilitate
    println("Joc in doua runde: " + VariableElimination.probability(joc2Runde, "egal"))
  }
}
