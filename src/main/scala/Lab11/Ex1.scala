import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.collection._
import com.cra.figaro.library.atomic.continuous._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}


object lab11 {
    def main(args: Array[String]) {
        val p_Presedinte = Flip(1.0/40000000.0) //1/40000000
        val p_Stangaci_Presedinti = Flip(0.5)
        val p_Stangaci_Populatie = Flip(0.1)
        val p_Havard_Presedinti = Flip(0.15)
        val p_Havard_Populatie = Flip(0.0005)

        //val p_Presedinte_Stangagi = (p_Stangaci_Presedinti * p_Presedinte) / ((p_Stangaci_Presedinti * p_Presedinte) + (p_Stangaci_Populatie*(1-p_Presedinte)))
        //val p_Presedinte_Havard = (p_Havard_Presedinti * p_Presedinte) / ((p_Havard_Presedinti*p_Presedinte) + (p_Havard_Populatie*(1-p_Presedinte)))


        val p_Stangaci = If(p_Presedinte,Flip(0.5),Flip(0.1))

        p_Stangaci.observe(true)
        val result = VariableElimination.probability(p_Presedinte,true)
        println("Posibilitate Presedinte Stangaci: " + result)

        val p_Havard = If(p_Presedinte,Flip(0.15),Flip(0.0005))

        p_Stangaci.unobserve()
        p_Havard.observe(true)
        val result2 = VariableElimination.probability(p_Presedinte,true)
        println("Posibilitate Presedinte Harvard: " + result2)

        p_Stangaci.observe(true)
        p_Havard.observe(true)
        val result3 = VariableElimination.probability(p_Presedinte,true)
        println("Posibilitate Presedinte Stangaci + Harvard: " + result3)


        //val result4 = Importance.probability(p_Presedinte,true)
        //println("Posibilitate Presedinte Stangaci + Harvard: " + result4)

        val alg = Importance(1000, p_Presedinte)
        alg.start()
        println("Stanga + Harvard cu importance: " + alg.probability(p_Presedinte,true))




        
    }
}