import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GetRegistration extends JPanel {
    private JTable table;
    private GroupedTableModel tableModel;

    public GetRegistration() {
        setLayout(new BorderLayout());

        // Exemplo de dados
        List<Object[]> rawData = new ArrayList<>();
        rawData.add(new Object[]{"CS101", "Computer Science", "CS101-1", "Data Structures"});
        rawData.add(new Object[]{"CS101", "Computer Science", "CS101-2", "Algorithms"});
        rawData.add(new Object[]{"MATH101", "Mathematics", "MATH101-1", "Calculus"});
        rawData.add(new Object[]{"MATH101", "Mathematics", "MATH101-2", "Linear Algebra"});

        // Agrupar dados
        List<Object[]> groupedData = GroupedTableModel.groupData(rawData, 0);

        // Configuração da tabela
        String[] columnNames = {"Course Code", "Course Name", "Subject Code", "Subject Name"};
        tableModel = new GroupedTableModel(columnNames, groupedData);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Consulta de Disciplinas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GetRegistration());
        frame.setVisible(true);
    }
}