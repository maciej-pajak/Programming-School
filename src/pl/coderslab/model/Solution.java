package pl.coderslab.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DataType;

public class Solution extends DataType<Solution> {
    
    
    private int id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private int userId;
    private int exerciseId;
    
    public Solution(int userId, int exerciseId) {
        this.id = 0;
        setUserId(userId);
        setExerciseId(exerciseId);
    }
    
    public Solution(String created, String updated, String description, int userId, int exerciseId) {
        this(0, created, updated, description, userId, exerciseId);
    }
    
    public Solution(int id, String created, String updated, String description, int userId, int exerciseId) {
        this.id = id;
        setCreated(created);
        setUpdated(updated);
        setDescription(description);
        setUserId(userId);
        setExerciseId(exerciseId);
    }
    
    public enum Column implements ColumnsEnumInterface {
        ID("id"), CREATED("created"), UPDATED("updated"), DESCRIPTION("description"), USERID("user_id"), EXERCISE_ID("exercise_id");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    private static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        }
    }
    
    private static LocalDateTime stringToLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
            return LocalDateTime.parse(dateTimeStr, formatter);
        }
    }
    
    public String getCreated() {
        return localDateTimeToString(created);
    }

    private Solution setCreated(String created) {
        this.created = stringToLocalDateTime(created);
        return this;
    }
    
    public Solution setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getUpdated() {
        return localDateTimeToString(updated);
    }

    public Solution setUpdated(String updated) {
        this.updated = stringToLocalDateTime(updated);
        return this;
    }
    
    public Solution setUpdated(LocalDateTime updated) {
        this.updated = updated;
        return this;
    }
       
    public String getDescription() {
        return description;
    }

    public Solution setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Solution setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public Solution setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
        return this;
    }

    public int getId() {
        return id;
    }
    
    protected Solution setId(int id) {
        this.id = id;
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s, %s, %s]", getId(), getUserId(), getExerciseId(), getCreated(), getUpdated(), getDescription());
    }

}
