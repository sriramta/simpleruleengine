package org.acme.rule.engine;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.acme.rule.domain.Rule;
import org.acme.rule.domain.RuleSet;
import org.acme.rule.domain.SubRule;
import org.apache.commons.jexl3.JexlExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tasri on 8/10/2017.
 */
public class JEvalRuleEngine {

    public static void main(String[] args) throws EvaluationException {
        //final String expression = "(#{ba1}=='Corp Bank'||#{ba1}=='Africa'||#{ba1}=='Investment Bank'||#{ba1}=='Corp Bank'||#{ba1}=='Investment Bank')&&(#{city}=='Delhi'||#{city}=='Slough'||#{city}=='Denver'||#{city}=='Delhi'||#{city}=='Mumbai')&&(#{region}=='EMEA'||#{region}=='EMEA'||#{region}=='EMEA'||#{region}=='AMER'||#{region}=='EMEA')&&(#{ba2}=='Workers'||#{ba2}=='Group CEO'||#{ba2}=='Analyst'||#{ba2}=='Analyst'||#{ba2}=='Analyst')&&(#{region}=='APAC'||#{region}=='EMEA'||#{region}=='EMEA'||#{region}=='EMEA'||#{region}=='APAC')";

        List<RuleSet> ruleSets = RuleLoader.getRawRules();
        List<String> ruleSetExpressions = new ArrayList<>();
        ruleSets.stream().forEach(r -> {
            ruleSetExpressions.add(getRuleExpression(r));
        });

        long startTime = System.currentTimeMillis();

       List goodRules = ruleSetExpressions.stream().parallel().filter(e -> {
            try {
                final Evaluator evaluator = new Evaluator();
                createFact(evaluator);
                return evaluator.getBooleanResult(e);
            } catch (EvaluationException e1) {
                e1.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());

        //System.out.println(evaluator.getBooleanResult(expression));
        long endTime = System.currentTimeMillis();

        //System.out.println("--------->> " + goodRules.size());
        System.out.println("Execution time :" + (endTime - startTime) + " ms");
    }

    public static void createFact(Evaluator fact) {
        fact.putVariable("ba1", "'Investment Bank'");
        fact.putVariable("ba2", "'Global'");
        fact.putVariable("country", "'Singapore'");
        fact.putVariable("city", "'Singapore'");
        fact.putVariable("region", "'APAC'");
    }


    private static String getRuleExpression(RuleSet rs) {
        RuleEngine re = new RuleEngine();
        List<Rule> rules = rs.getRules();
        StringBuilder expression  = new StringBuilder();
        String ruleExpression = rules.stream().map(r -> {
            StringBuilder tempExpression = new StringBuilder();


            if(r.getRules() != null) {
                return getSubExpression(r.getRules(), r.getCondition());
            } else {
                String field = r.getField();
                String value = r.getValueText();
                String operator = translateOperator(r.getOperator());
                return tempExpression.append("#{"+field+"}").append(operator).append("'").append(value).append("'").toString();
            }
        }).collect(Collectors.joining(translateOperator(rs.getCondition()) + "" ));
        return ruleExpression;
    }

    private static String getSubExpression(List<SubRule> rules, String condition) {
        String ruleExpression = rules.stream().map(r -> {
            StringBuilder tempExpression = new StringBuilder();
            String field = r.getField();
            String value = r.getValueText();
            String operator = translateOperator(r.getOperator());

            return tempExpression.append("#{"+field+"}").append(operator).append("'").append(value).append("'").toString();
        }).collect(Collectors.joining(translateOperator(condition) + "" ));

        return "(" + ruleExpression + ")";
    }

    private static String translateOperator(String operator){
        if(operator.equals("equal")) {
            return "==";
        } else if (operator.equals("AND") ) {
            return "&&";
        } else if (operator.equals("OR")) {
            return "||";
        }

        return null;
    }


}
