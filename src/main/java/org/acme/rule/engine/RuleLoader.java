package org.acme.rule.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.rule.domain.Rule;
import org.acme.rule.domain.RuleSet;
import org.acme.rule.domain.SubRule;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tasri on 8/10/2017.
 */
public class RuleLoader {
    private static List<RuleSet> ruleSets ;
    private static List<String> ruleSetExpressions = new ArrayList<>();
    static {
        ruleSets = getRules();
        ruleSets.stream().forEach(r -> {
            ruleSetExpressions.add(getRuleExpression(r));
        });
    }

    public static List<String> getRuleSet() {
        return ruleSetExpressions;
    }

    public static List<RuleSet> getRawRules() {
        return ruleSets;
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
                return tempExpression.append(field).append(operator).append("'").append(value).append("'").toString();
            }

            //return tempExpression.append(field).append(" ").append(translateOperator(operator)).append(" ").append(value).append(" ").toString();
        }).collect(Collectors.joining(translateOperator(rs.getCondition()) + "" ));

        //System.out.println("expression -----> " + ruleExpression);
        return ruleExpression;
    }

    private static String getSubExpression(List<SubRule> rules, String condition) {
        String ruleExpression = rules.stream().map(r -> {
            StringBuilder tempExpression = new StringBuilder();
            String field = r.getField();
            String value = r.getValueText();
            String operator = translateOperator(r.getOperator());

            return tempExpression.append(field).append(operator).append("'").append(value).append("'").toString();
            //return tempExpression.append(field).append(" ").append(translateOperator(operator)).append(" ").append(value).append(" ").toString();
        }).collect(Collectors.joining(translateOperator(condition) + "" ));

        return "(" + ruleExpression + ")";
    }

    private static String translateOperator(String operator){
        //System.out.println("operator ------> " + operator);
        if(operator.equals("equal")) {
            return "==";
        } else if (operator.equals("AND") ) {
            return "&&";
        } else if (operator.equals("OR")) {
            return "||";
        }

        return null;
    }

    private static List<RuleSet> getRules() {
        InputStream is = RuleLoader.class.getResourceAsStream("/test-rule");
        //System.out.println("is ----> " + is);
        ObjectMapper om = new ObjectMapper();
        try {
            TypeReference<List<RuleSet>> typeRef
                    = new TypeReference<List<RuleSet>>() {};
            return om.readValue(is, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
