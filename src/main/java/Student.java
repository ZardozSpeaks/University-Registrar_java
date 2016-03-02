import org.sql2o.*;
import java.util.List;
import java.util.Date;

public class Student {

  private int id;
  private String first_name;
  private String last_name;
  private Date enrollment_date;

  public Student (String firstName, String lastName) {
    this.first_name = firstName;
    this.last_name = lastName;
    this.enrollment_date = new Date();
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getFirstName().equals(newStudent.getFirstName()) &&
      this.getLastName().equals(newStudent.getLastName()) &&
      this.getId() == newStudent.getId();
    }
  }

  //GETTER FUNCTIONS//

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }

  public Date getEnrollmentDate() {
    return enrollment_date;
  }

  //SETTER FUNCTIONS//

  public void setFirstName(String newFirstName) {
    first_name = newFirstName;
  }

  public void setLastName(String newLastName) {
    last_name = newLastName;
  }

  //CREATE//

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (first_name, last_name, enrollment_date) VALUES (:first_name, :last_name, :enrollment_date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .addParameter("enrollment_date", this.enrollment_date)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//

  public static List<Student> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students";
      return con.createQuery(sql)
        .executeAndFetch(Student.class);
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
    }
  }

  //UPDATE//

  public void updateEnrollmentDate() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET enrollment_date = :enrollment_date WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("enrollment_date", enrollment_date)
        .executeUpdate();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET first_name = :first_name, last_name = :last_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(Connection con DB.sql2o.open()) {
      String sql = "DELETE FROM students WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
