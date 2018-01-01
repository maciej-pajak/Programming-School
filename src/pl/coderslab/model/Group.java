package pl.coderslab.model;

import pl.coderslab.model.standards.DataType;

public class Group extends DataType<Group> {

    private int id;
    private String name;
    
    public Group(String name) {
        this(0, name);
    }
    
    public Group(int id, String name) {
        this.setName(name);
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public Group setName(String name) {
        this.name = name;
        return this;
    }
    
    public int getId() {
        return id;
    }
    
    protected Group setId(int id) {
        this.id = id;
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s]", getId(), getName());
    }
    
}
