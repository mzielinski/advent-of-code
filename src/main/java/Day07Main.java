import utils.ReadFile;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.MIN_VALUE;

public class Day07Main {

    static class Rule {

        private final String name;
        private final int quantity;
        private final List<Rule> rules = new ArrayList<>();

        Rule(String name) {
            this(name, MIN_VALUE);
        }

        Rule(String name, int quantity) {
            this.name = name.replaceAll("bags", "").replaceAll("bag", "").trim();
            this.quantity = quantity;
        }

        void addRule(Rule rule) {
            rules.add(rule);
        }

        @Override
        public String toString() {
            return name +
                    (quantity != MIN_VALUE ? ", quantity: " + quantity : "") +
                    (!rules.isEmpty() ? ", rules: " + rules : "");
        }
    }

    static class Day07Input implements ReadFile<Rule> {

        @Override
        public Rule getRecordFromLine(String line) {
            String[] split = line.split("contain");
            Rule rule = new Rule(split[0]);
            for (String bag : split[1].split(",")) {
                rule.addRule(parseLine(bag));
            }
            return rule;
        }

        private Rule parseLine(String line) {
            int quantity = 0;
            StringBuilder name = new StringBuilder();
            try (Scanner fi = new Scanner(line)) {
                fi.useDelimiter("[^\\p{Alnum}]");
                while (fi.hasNext()) {
                    if (fi.hasNextInt()) {
                        quantity = fi.nextInt();
                    } else {
                        name.append(" ").append(fi.next());
                    }
                }
            }
            return new Rule(name.toString(), quantity);
        }
    }

    private List<Rule> readFile(String filename) {
        ReadFile<Rule> file = new Day07Input();
        return file.readFile(filename);
    }


    private Map<String, List<Rule>> readFile(List<Rule> roots) {
        return roots.stream().collect(Collectors.toMap(rule -> rule.name, rule -> rule.rules));
    }

    private int bagColors(Map<String, List<Rule>> all, List<Rule> rules) {
        int quantity = 0;
        for (Rule rule : rules) {
            if (!"shiny gold".equals(rule.name)) {
                quantity += recursion(all, "shiny gold", rule.rules, rule.name);
            }
        }
        return quantity;
    }

    private int recursion(Map<String, List<Rule>> all, String bag, List<Rule> rules, String path) {
        int result = 0;
        for (Rule r : rules) {
            if (bag.equals(r.name)) {
                return ++result;
            } else if (all.containsKey(r.name) &&
                    recursion(all, bag, all.get(r.name), path + " → " + r.name) > 0) {
                return ++result;
            }
        }
        return result;
    }

    private int individualBags(Map<String, List<Rule>> all, List<Rule> rules) {
        int result = 0;
        for (Rule r : rules) {
            if (all.containsKey(r.name)) {
                List<Rule> rule = all.get(r.name);
                if (rule.size() > 1 || rule.get(0).quantity != 0) {
                    result += r.quantity * individualBags(all, rule);
                }
                result += r.quantity;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Day07Main main = new Day07Main();
        String[] files = new String[]{"day07/01.luggage", "day07/02.luggage", "day07/03.luggage"};
        Arrays.stream(files).forEach(file -> {
            System.out.println("### File " + file);
            List<Rule> rules = main.readFile(file);
            Map<String, List<Rule>> map = main.readFile(rules);
            System.out.println("# Result Part1: " + main.bagColors(map, rules));
            System.out.println("# Result Part2: " + main.individualBags(map, map.get("shiny gold")));
            System.out.println();
        });
    }
}
