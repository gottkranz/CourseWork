package sample.math.automata.tag;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.math.automata.tag.factory.AbstractTagFactory;
import sample.math.automata.tag.factory.TagFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaggableImpl implements Taggable{
    private StringProperty tags;
    private TagFactory tagFactory;

    private Map<String, String> tagMap;

    public TaggableImpl(String type){
        tags = new SimpleStringProperty("");
        tagFactory = new AbstractTagFactory().getTagFactory(type);
        tagMap = new HashMap<>();
    }

    @Override
    public String getTags() {
        return tags.getValue();
    }

    @Override
    public Map<String, String>  getCloneTagMap() {
        return new HashMap<>(tagMap);
    }

    @Override
    public void clearTags() {
        tags.setValue("");
        tagMap.clear();
    }

    @Override
    public void addTag(String acronym) {
        String[] tagArr = tagFactory.getTag(acronym);
        if(tagArr != null){
            if(tagMap.put(tagArr[0], tagArr[1]) == null){
                String old = tags.getValue();
                tags.setValue(old + "\n" + tagArr[0]);
            }else{
                System.err.println("Trying to add an existing tag!");
            }
        }else{
            System.err.println("Trying to add bad tag!");
        }
    }
}
