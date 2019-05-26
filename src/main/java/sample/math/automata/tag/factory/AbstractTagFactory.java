package sample.math.automata.tag.factory;

import java.util.HashMap;
import java.util.Map;

public class AbstractTagFactory {
    public TagFactory getTagFactory(String type){
        switch (type){
            case "function_automaton":
                return new TagFactory(getFunctionAutomatonPossibleTags());
            case "states_automaton":
                return new TagFactory(getStatesAutomatonPossibleTags());
            case "arc":
                return new TagFactory(getArcPossibleTags());
            default:
                    Map<String, String> possibleTags = new HashMap<>();
                    return new TagFactory(possibleTags);
        }
    }

    private Map<String, String> getFunctionAutomatonPossibleTags(){
        Map<String, String> possibleTags = new HashMap<>();
        possibleTags.put("BIB", "Bad Initial Beta");
        possibleTags.put("BIN", "Bad Initial Name");
        return possibleTags;
    }

    private Map<String, String> getStatesAutomatonPossibleTags(){
        Map<String, String> possibleTags = new HashMap<>();
        possibleTags.put("BIB", "Bad Initial: Beta");
        possibleTags.put("BIN", "Bad Initial: Name");
        return possibleTags;
    }

    private Map<String, String> getArcPossibleTags(){
        Map<String, String> possibleTags = new HashMap<>();
        possibleTags.put("RO", "Rewritten Output");
        possibleTags.put("ROS", "Rewritten Output State");
        possibleTags.put("NI", "Not initialized");
        return possibleTags;
    }
}
