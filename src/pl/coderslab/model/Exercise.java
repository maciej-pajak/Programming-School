package pl.coderslab.model;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DataType;

public class Exercise extends DataType<Exercise> {
    
    private int id;
    private String title;
    private String description;

    public Exercise(String title, String description) {
        this(0, title, description);
    }

    public Exercise(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
    }
    
    public int getId() {
        return id;
    }

    protected Exercise setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Column implements ColumnsEnumInterface {
        ID("id"), TITLE("title"), DESCRIPTION("description");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.id).append(", ").append(this.title).append(", ").append(this.description)
                .append("]");
        return sb.toString();
    }

}
