package org.acme.rule.engine;

import org.apache.commons.jexl3.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tasri on 8/10/2017.
 */
public class RuleEngineUsingJexl {

    public static void main(String[] args) {
        JexlEngine jexl = new JexlBuilder().create();
        String jexlExp = "(ba2=='Investment Bank')&&(ba1=='Global')";
        List<String> ruleSetExpressions = RuleLoader.getRuleSet();

        JexlContext jc = createFact();
        jc.set("ba2", "Investment Bank" );
        jc.set("ba1", "Global");
        System.out.println("---> starting rule <--- ");
        Long startTime = System.currentTimeMillis();

        List goodRules = ruleSetExpressions.stream().parallel().filter(e -> {
            JexlExpression exp = jexl.createExpression( e );
            Object o = exp.evaluate(jc);
            //System.out.println("-----> " + o);
            return (boolean) o;
        }).collect(Collectors.toList());
        Long endTime = System.currentTimeMillis();

        System.out.println("----------------------------->>> " + goodRules.size());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + (endTime - startTime));
    }

    private static JexlContext createFact() {

        JexlContext fact = new MapContext();
        fact.set("ba1", "Investment Bank");
        fact.set("ba2", "Global");
        fact.set("country", "Singapore");
        fact.set("city", "Singapore");
        fact.set("region", "APAC");

        return fact;
    }
}
