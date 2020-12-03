package Lab9
/*
Score
Learn
Pass     L0 -> S0 -> P0    | L0 -> L1 ..

*/

import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.collection._
import com.cra.figaro.library.atomic.continuous._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}

object lab9 {
    def main(args: Array[String]) {

        val length = 10
        val pass: Array[Element[String]] = Array.fill(length)(Constant(""))
        val learn: Array[Element[Int]] = Array.fill(length)(Constant(0))
        val score: Array[Element[Int]] = Array.fill(length)(Constant(0))

        learn(0) = Constant(10)
        score(0) = Constant(10)
        pass(0)= Constant("pass")
        
        for { minute <- 1 until length } {

            learn(minute) = CPD(learn(minute - 1),
                (10) -> Constant(9),
                (9) -> Constant(8),
                (8) -> Constant(7),
                (7) -> Constant(6),
                (6) -> Constant(5),
                (5) -> Constant(4),
                (4) -> Constant(3),
                (3) -> Constant(2),
                (2) -> Constant(1),
                (1) -> Constant(0),
                (0) -> Constant(0))
            
            score(minute) = CPD(learn(minute),
                 (10) -> Constant(10),
                (9) -> Constant(9),
                (8) -> Constant(8),
                (7) -> Constant(7),
                (6) -> Constant(6),
                (5) -> Constant(5),
                (4) -> Constant(4),
                (3) -> Constant(3),
                (2) -> Constant(2))

            pass(minute) = Apply(score(minute), (i: Int) => if (i > 4) "pass" else "fail")
        }


        pass(1).observe("pass")
        pass(2).observe("pass")
        pass(3).observe("pass")

        println("Probability to pass test 10: " + VariableElimination.probability(pass(9), "pass"))

    }
}