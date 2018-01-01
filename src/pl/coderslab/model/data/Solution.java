package pl.coderslab.model.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.coderslab.model.dao.ColumnsEnumInterface;

public class Solution implements DataTypeInterface<Solution> {
    
    
    private int id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private int userId;
    private int exerciseId;
    
    public Solution(int user_id, int exercise_id) {
        this.id = 0;
        setUserId(user_id);
        setExerciseId(exercise_id);
    }
    
    public Solution(String created, String updated, String description, int user_id, int exercise_id) {
        this(0, created, updated, description, user_id, exercise_id);
    }
    
    public Solution(int id, String created, String updated, String description, int user_id, int exercise_id) {
        this.id = id;
        setCreated(created);
        setUpdated(updated);
        setDescription(description);
        setUserId(user_id);
        setExerciseId(exercise_id);
    }
    
    public static enum Column implements ColumnsEnumInterface {
        ID("id"), CREATED("created"), UPDATED("updated"), DESCRIPTION("description"), USERID("user_id"), EXERCISE_ID("exercise_id");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    private static String LocalDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        }
    }
    
    private static LocalDateTime StringToLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
            return LocalDateTime.parse(dateTimeStr, formatter);
        }
    }
    
    public String getCreated() {
        return LocalDateTimeToString(created);
    }

    private Solution setCreated(String created) {
        this.created = StringToLocalDateTime(created);
        return this;
    }
    
    public Solution setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getUpdated() {
        return LocalDateTimeToString(updated);
    }

    public Solution setUpdated(String updated) {
        this.updated = StringToLocalDateTime(updated);
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

    public Solution setUserId(int user_id) {
        this.userId = user_id;
        return this;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public Solution setExerciseId(int exercise_id) {
        this.exerciseId = exercise_id;
        return this;
    }

    public int getId() {
        return id;
    }
    
    public Solution setId(int id) {
        this.id = id;
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s, %s, %s]", getId(), getUserId(), getExerciseId(), getCreated(), getUpdated(), getDescription());
    }

}
