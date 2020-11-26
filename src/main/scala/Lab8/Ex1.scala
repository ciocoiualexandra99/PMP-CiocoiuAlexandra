package Lab8

import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.collection._
import com.cra.figaro.library.atomic.continuous._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}

object lab8 {

class Research_Development(hr: HR) {
    val state = CPD(hr.state,
        'top -> Select(0.8 -> 'good, 0.15 -> 'intermittent, 0.05 -> 'bad),
        'intermittent -> Select(0.4 -> 'good, 0.4 -> 'intermittent, 0.2 -> 'bad),
        'down -> Select(0.2 -> 'good, 0.3 -> 'intermittent, 0.5 -> 'bad))
}
class Production(rd: Research_Development) {
    val state = CPD(rd.state,
        'good -> Select(0.8 -> 'well, 0.15 -> 'intermittent, 0.05 -> 'week),
        'intermittent -> Select(0.4 -> 'well, 0.4 -> 'intermittent, 0.2 -> 'week),
        'bad -> Select(0.2 -> 'well, 0.3 -> 'intermittent, 0.5 -> 'week))
}
class Sales(production: Production) {
    val state = CPD(production.state,
        'well -> Select(0.8 -> 'no_number, 0.15 -> 'intermittent, 0.05 -> 'zero),
        'intermittent -> Select(0.4 -> 'no_number, 0.4 -> 'intermittent, 0.2 -> 'zero),
        'week -> Select(0.2 -> 'no_number, 0.3 -> 'intermittent, 0.5 -> 'zero))
}
class HR {
    val state = Select(0.8 -> 'top, 0.1 -> 'intermittent, 0.1 -> 'down)
}
class Finance(sales: Sales) {
    val state = CPD(sales.state,
        'no_number -> Select(0.8 -> 'money_money, 0.15 -> 'intermittent, 0.05 -> 'no_money),
        'intermittent -> Select(0.4 -> 'money_money, 0.4 -> 'intermittent, 0.2 -> 'no_money),
        'zero -> Select(0.2 -> 'money_money, 0.3 -> 'intermittent, 0.5 -> 'no_money))
}

class Firm(rd: Research_Development, production: Production, sales: Sales, hr: HR, finance: Finance){ //very_very_bad, week, good, well
    val state = RichCPD(rd.state, production.state, sales.state, hr.state, finance.state,
    (OneOf('good), OneOf('well), OneOf('no_number), OneOf('top), OneOf('money_money)) -> Constant('well),
    (OneOf('bad), OneOf('week), OneOf('zero), OneOf('down), OneOf('no_money)) -> Constant('very_very_bad),
    (*, *, *, *, OneOf('no_money)) -> Constant('week),
    (*, *, *, OneOf('down, 'intermittent), *) -> Constant('week),
    (*, *, *, *, *) -> Select(0.25 -> 'very_very_bad, 0.25 -> 'week, 0.25 -> 'good, 0.25 -> 'well))
}

val hr = new HR
val rd = new Research_Development(hr)
val production = new Production(rd)
val sales = new Sales(production)
val finance = new Finance(sales)
val firm = new Firm(rd, production, sales, hr, finance)


def step1() {
    val answerWithNoEvidence = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Stare sanatate 'well: " + answerWithNoEvidence)
    println("--------------")
}

def step2(){
    hr.state.observe('top)
    val answerWithNoEvidence = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Hr 'top: " + answerWithNoEvidence)

    production.state.observe('intermittent)
    val answerWithNoEvidence2 = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Productie 'intermittent: " + answerWithNoEvidence2)

    sales.state.observe('no_number)
    val answerWithNoEvidence3 = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Vanzari 'no_number: " + answerWithNoEvidence3)
    println("--------------")

}

def step3(){
    rd.state.observe('good)
    val answerWithNoEvidence = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("RD 'good: " + answerWithNoEvidence)

    production.state.observe('well)
    val answerWithNoEvidence2 = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Productie 'well: " + answerWithNoEvidence2)

    sales.state.observe('intermittent)
    val answerWithNoEvidence3 = VariableElimination.probability(firm.state, (state:Symbol) => state == 'well)
    println("Vanzari 'intermittent: " + answerWithNoEvidence3)

}


    def main(args: Array[String]) {
        step1()
        step2()
        step3
    }
}