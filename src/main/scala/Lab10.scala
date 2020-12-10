import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.collection._
import com.cra.figaro.library.atomic.continuous._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}

//capital0
//profit0
//investment0

//c1 <- c0, p1, i1
//p1 <- i1 procent
//i1 <- c0 procent fix

object lab10 {
  val length = 10
  val capital: Array[Element[Double]] = Array.fill(length)(Constant(0))
  val profit: Array[Element[Double]] = Array.fill(length)(Constant(0))
  val investitie: Array[Element[Double]] = Array.fill(length)(Constant(0))
  capital(0) = Constant(100)
  profit(0) = Constant(0)
  investitie(0) = Constant(0)


  for { minute <- 1 until length } {
    investitie(minute) = Apply(capital(minute - 1), (i: Double) => if(i>=100) (i*2)/10 else 0)
    profit(minute) = Apply(investitie(minute), (i: Double) => if (i>0) i*2 else -5)
    capital(minute) =  Apply(capital(minute -1), profit(minute), investitie(minute),
               (c: Double, p: Double, i: Double) => c + p - i)
  }

    def main(args: Array[String]) {
    val alg = VariableElimination(capital(length - 1))
    alg.start()
    println("Capital final: " + alg.mean(capital(length - 1)))

    
    }
}
