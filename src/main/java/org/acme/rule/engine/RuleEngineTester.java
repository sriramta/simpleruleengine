package org.acme.rule.engine;

import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tasri on 7/10/2017.
 */
public class RuleEngineTester {

    public static void main(String[] args) {

        Map<String, Object> fact = new HashMap<>();
        fact.put("ba1", "Investment Bank");
        fact.put("ba2", "Global");
        fact.put("country", "Singapore");
        fact.put("city", "Singapore");

        //boolean result = MVEL.evalToBoolean("(ba2=='Investment Bank'&&country=='Singapore')", fact);
        boolean result = MVEL.evalToBoolean("ba2=='Investment Bank'&&ba2=='Global'", fact);
        System.out.println("--------->> " + result);
    }
}
