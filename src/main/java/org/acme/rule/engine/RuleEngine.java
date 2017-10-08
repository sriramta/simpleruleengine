package org.acme.rule.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.rule.domain.Rule;
import org.acme.rule.domain.RuleSet;
import org.acme.rule.domain.SubRule;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Created by tasri on 7/10/2017.
 */
public class RuleEngine {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RuleEngine re = new RuleEngine();
        List<String> ruleSetExpressions = RuleLoader.getRuleSet();
        Long startTime = System.currentTimeMillis();
        Map<String, Object> fact = re.createFact();
        System.out.println("starting rule ....");
        ForkJoinPool customThreadPool = new ForkJoinPool(10);
        List goodRules = customThreadPool.submit(() -> ruleSetExpressions.stream().parallel().filter(rs -> MVEL.evalToBoolean(rs, fact)).collect(Collectors.toList())).get();
        //ruleSetExpressions.stream().filter(rs -> MVEL.evalToBoolean(rs, fact)).collect(Collectors.toList());
        Long endTime = System.currentTimeMillis();

        System.out.println("----------------------------->>> " + goodRules.size());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + (endTime - startTime));
    }

    public Map<String, Object> createFact() {

        Map<String, Object> fact = new HashMap<>();
        fact.put("ba1", "Investment Bank");
        fact.put("ba2", "Global");
        fact.put("country", "Singapore");
        fact.put("city", "Singapore");
        fact.put("region", "APAC");

        return fact;
    }
}
