import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.time.LocalDate;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Student firstStudent = new Student("Burton", "Westchesterton");
    Student secondStudent = new Student("Burton", "Westchesterton");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesStudentToTheDatabase_true() {
    Student testStudent = new Student("Tran", "Vo");
    testStudent.save();
    assertTrue(Student.all().get(0).equals(testStudent));
  }

  @Test
  public void setEnrollmentDate_savesADate() {
    Student testStudent = new Student("Thompson", "Kalamazoo");
    testStudent.save();
    assertEquals(((Student.all().get(0)).getEnrollmentDate()).toString(), "2016-03-01");
  }

  @Test
  public void find_returnsCorrectStudent_True() {
    Student testStudent1 = new Student("Burton", "Westchesterton");
    Student testStudent2 = new Student("Tran", "Vo");
    Student testStudent3 = new Student("Thompson", "Kalamazoo");
    testStudent1.save();
    testStudent2.save();
    testStudent3.save();
    Student savedStudent = Student.find(testStudent2.getId());
    assertTrue(testStudent2.equals(savedStudent));
  }
}
