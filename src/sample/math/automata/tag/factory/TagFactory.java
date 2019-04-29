package sample.math.automata.tag;

import java.util.ArrayList;
import java.util.List;

public class TagFactory {
    private final List<Tag> possibleTags;

    public TagFactory(String factoryType){
        possibleTags = new ArrayList<>();

        switch (factoryType){
            case "state":
                //possibleTags.add(new TagImpl())
                break;
            case "states automaton":
                possibleTags.add(new TagImpl("BIB", "Bad Initial Beta"));
                break;
            case "function automaton":
                possibleTags.add(new TagImpl("BIB", "Bad Initial Beta"));
                break;
        }
    }

    public Tag getTag(String acronym){
        Tag ret = null;
        switch (acronym){
            case "BIB":
                ret = new TagImpl("BIB", "Bad Initial Beta");
                return ret;
        }
        return ret;
    }

    public Tag getTagByAcronym(String tagAcronym){
        for (int i = 0; i < possibleTags.size(); i++) {
            Tag tag =  possibleTags.get(i);
            if(tag.getTag()[0].equals(tagAcronym)){
                return tag;
            }
        }
        return null;
    }
}
