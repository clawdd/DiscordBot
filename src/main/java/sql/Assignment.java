package sql;

import java.util.Date;

public class Assignment {

    private int id;
    private String className;
    private String assigmentType;
    private Date date;
    public Assignment(int id, String className, String assignmentType, Date date) {

        this.id = id;
        this.className = className;
        this.assigmentType = assignmentType;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getAssigmentType() {
        return assigmentType;
    }

    public Date getDate() {
        return date;
    }
}
