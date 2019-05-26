package sample.math.automata.tag;

import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Map;

public interface Taggable {
    String getTags();
    Map<String, String> getCloneTagMap();

    void clearTags();
    void addTag(String acronym);
}
