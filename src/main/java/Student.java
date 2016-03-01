import org.sql20.*;
import java.util.List;
import java.time.LocalDate;

public class Student {

  private int id;
  private String first_name;
  private String last_name;
  private LocalDate enrollment_date;

  public Student (String firstName, String lastName) {
    this.firstName = first_name;
    this.lastName = last_name;
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

  public String getEnrollmentDate() {
    return enrollment_date;
  }

  //SETTER FUNCTIONS//

  public void setFirstName(String newFirstName) {
    first_name = newFirstName;
  }

  public void setLastName(String newLastName) {
    last_name = newLastName;
  }

  public void setEnrollmentDate(){
    enrollment_date = LocalDate.now();
  }

  //CREATE//

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//

  public static List<Student> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students"
      return con.createQuery(sql)
        .executeAndFetch(Student.class);
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Student.class);
    }
  }
}
