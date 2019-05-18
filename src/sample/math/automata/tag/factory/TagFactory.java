package sample.math.automata.tag.factory;

import java.util.List;
import java.util.Map;

public class TagFactory {
    private final Map<String, String> possibleTags;

    public TagFactory(Map<String, String> possibleTags){
        this.possibleTags = possibleTags;
    }

    public String[] getTag(String acronym){
        if(possibleTags.get(acronym) != null){
            String[] ret = {acronym, possibleTags.get(acronym)};
            return ret;
        }else{
            return null;
        }
    }
}
