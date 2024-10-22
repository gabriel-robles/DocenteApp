import fateczl.csvdb.CsvContext;
import fateczl.csvdb.CsvContextImpl;
import fateczl.csvdb.CsvMapper;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.model.CourseMapper;
import fateczl.util.Queue;

public class Main {
  public static void main(String[] args) {
    CsvMapper<Course> courseMapper = new CourseMapper();
    CsvContext<Course> courseContext = new CsvContextImpl<>(
        "cursos.csv", courseMapper, Course.class);

    System.out.println("Prodocente");
    while (true) {
      System.out.println("1.a Cadastrar curso");
      System.out.println("2. Listar cursos");
      System.out.println("3. Atualizar curso");
      System.out.println("4. Excluir curso");
      System.out.println("5. Sair");

      var option = System.console().readLine();

      switch (option) {
        case "1":
          var courseCode = System.console().readLine("Código do Curso: ");
          var courseName = System.console().readLine("Nome do Curso: ");
          var courseKnowledgeArea = System.console().readLine("Área de Conhecimento: ");
          var course = Course.create(courseCode, courseName, courseKnowledgeArea);
          try {
            courseContext.insert(course);
          } catch (Exception e) {
            System.out.println("Erro ao cadastrar curso");
          }
          break;
        case "2":
          try {
            var courses = courseContext.readAll();
            while (!courses.isEmpty()) {
              System.out.println(courses.dequeue().toString());
            }
          } catch (Exception e) {
            System.out.println("Erro ao listar cursos");
          }
          break;
        case "3":
          var courseId = Integer.parseInt(System.console().readLine("ID do Curso: "));
          Queue<Course> courses = new Queue<>();

          try {
            courses = courseContext.readAll();
          } catch (Exception e) {
            System.out.println("Erro ao listar cursos");
          }

          Course courseToUpdate = null;

          while (!courses.isEmpty()) {
            var cours = courses.dequeue();
            if (cours.getId() == courseId) {
              courseToUpdate = cours;
              break;
            }
          }

          if (courseToUpdate == null) {
            System.out.println("Curso não encontrado");
            break;
          }

          var courseCodeToUpdate = System.console().readLine("Código do Curso: ");
          var courseNameToUpdate = System.console().readLine("Nome do Curso: ");
          var courseKnowledgeAreaToUpdate = System.console().readLine("Área de Conhecimento: ");

          var updatedCourse = Course.update(
              courseToUpdate,
              courseCodeToUpdate,
              courseNameToUpdate,
              courseKnowledgeAreaToUpdate);

          try {
            courseContext.update(updatedCourse);
          } catch (Exception e) {
            System.out.println("Erro ao atualizar curso");
          }
          break;
        case "4":
          var courseIdToDelete = Integer.parseInt(System.console().readLine("ID do Curso: "));

          Queue<Course> courses1 = new Queue<>();

          try {
            courses1 = courseContext.readAll();
          } catch (Exception e) {
            System.out.println("Erro ao listar cursos");
          }

          Course courseToDelete = null;

          while (!courses1.isEmpty()) {
            var cours = courses1.dequeue();
            if (cours.getId() == courseIdToDelete) {
              courseToDelete = cours;
              break;
            }
          }

          if (courseToDelete == null) {
            System.out.println("Curso não encontrado");
            break;
          }

          try {
            courseContext.delete(courseToDelete);
          } catch (Exception e) {
            System.out.println("Erro ao excluir curso");
          }

          break;
        case "5":
          System.exit(0);
          return;
        default:
          System.out.println("Opção inválida");
          break;
      }
    }
  }
}
