import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.rule.domain.Rule;
import org.acme.rule.domain.RuleSet;
import org.acme.rule.domain.SubRule;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tasri on 7/10/2017.
 */
public class TestRuleCreator {

    String[] properties = {"ba1", "ba2", "country", "city", "region"};
    String[] ba1Values = {"Investment Bank", "Corp Bank", "Private Bank", "Trading", "Africa"};
    String[] ba2Values = {"Group CEO", "Group CIO", "Analyst", "Workers"};
    String[] countryVals = {"India", "China", "Colombia", "Sri Lanka", "Singapore", "Thailand", "Vietnam", "Indonesia", "Malaysia", "United Kingdom", "United States", "France", "Neatherland", "Germany", "Spain", "Russia", "Norway", "Latvia", "Lithuania", "Estonia", "Finland", "Mongolia", "Pakistan", "Ukraine", "South Korea", "Japan", "Laos"};
    String[] cityVals = {"London", "Slough", "New York", "New Jersey", "Singapore", "Shanghai", "Mumbai", "Chennai", "Delhi", "Denver"};
    String[] regionVals = {"AMER", "APAC", "EMEA"};
    String[] operators = {"AND", "OR"};

    public static void main(String[] args) throws JsonProcessingException {
        TestRuleCreator rc = new TestRuleCreator();
        List<RuleSet> rss = new ArrayList<>();
        for(int i = 0; i <100000; i++) {
            rss.add(rc.createRuleSet());

        }

        ObjectMapper om = new ObjectMapper();
        //String s = om.writerWithDefaultPrettyPrinter().writeValueAsString(rss);
        File f = new File("C:/Srinath/Work/Java/coursera/Temp/Scala - Design/simpleruleengine/src/main/resources/test-rule");

        try {
            if(!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            om.writerWithDefaultPrettyPrinter().writeValue(f, rss);

            //Files.write(Paths.get("C:/Srinath/Work/Java/coursera/Temp/Scala - Design/simpleruleengine/src/main/resources/test-rule"), s.getBytes(), StandardOpenOption.APPEND );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public RuleSet createRuleSet() {
        RuleSet rset = new RuleSet();
        rset.setCondition("AND");
        Rule r = null;
        List<Rule> rules = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            rules.add(createRule());
        }

        rset.setRules(rules);
        return rset;
    }

    public Rule createRule() {
        int fl  = ThreadLocalRandom.current().nextInt(0, properties.length );
        String field = properties[fl];
        Rule rule = new Rule();
        rule.setCondition("OR");
        List<SubRule> srules = new ArrayList<>();
        for(int i = 0 ; i<5; i++ ){
            srules.add(createSubRule(field));
        }

        rule.setRules(srules);

        return rule;
    }

    private SubRule createSubRule (String field) {
        SubRule subR = new SubRule();
        subR.setId(field);
        subR.setField(field);
        subR.setOperator("equal");

        if(field.equals("ba1")) {
            int rn = ThreadLocalRandom.current().nextInt(0, ba1Values.length );
            subR.setValueText(ba1Values[rn]);
        } else if (field.equals("ba2")) {
            int rn = ThreadLocalRandom.current().nextInt(0, ba2Values.length );
            subR.setValueText(ba2Values[rn]);
        } else if (field.equals("country")) {
            int rn = ThreadLocalRandom.current().nextInt(0, countryVals.length );
            subR.setValueText(countryVals[rn]);
        } else if (field.equals("region")) {
            int rn = ThreadLocalRandom.current().nextInt(0, regionVals.length );
            subR.setValueText(regionVals[rn]);
        } else if(field.equals("city")) {
            int rn = ThreadLocalRandom.current().nextInt(0, cityVals.length );
            subR.setValueText(cityVals[rn]);
        }

        return subR;
    }
}
