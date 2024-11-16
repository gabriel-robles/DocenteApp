package fateczl.docenteapp.views.registrations;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import fateczl.csvdb.CsvContextFactory;
import fateczl.csvdb.CsvMapperFactory;
import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.model.Registration;
import fateczl.docenteapp.views.dtos.RegistrationDto;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;




public class GetRegistration extends JPanel{
	private final transient RegistrationController registrationController;
	  private JButton createButton;
	  private JButton searchButton;
	  private JComboBox<String> filterByComboBox;
	  private JTextField searchTextField;
	  private JPanel cardPanel;
	  private JPanel menuPanel;
	  private CardLayout cardLayout;
	  private JSplitPane splitPane;
	  private JScrollPane listPanel;
	  private EditRegistration editPanel;


	  public GetRegistration() throws IOException{
	    var registrationMapper = CsvMapperFactory.create(Registration.class);
	    var registrationContext = CsvContextFactory.create("Inscricao", registrationMapper, Registration.class);
	    this.registrationController = new RegistrationController(registrationContext);

	    splitPane = new JSplitPane();
	    splitPane.setPreferredSize(new Dimension(700, 425));

	    cardLayout = new CardLayout();
	    cardPanel = new JPanel(cardLayout);

	    setPreferredSize(new Dimension(700, 425));
	    add(cardPanel);

	    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    splitPane.setDividerLocation(75);
	    splitPane.setDividerSize(0);
	    menuPanel = createMenuPanel();
	    splitPane.setTopComponent(menuPanel);
	    listPanel = createListPanel();
	    splitPane.setBottomComponent(listPanel);

	    cardPanel.add(splitPane, "Menu Panel");
	    cardPanel.add(new CreateRegistration(registrationController, this), "Create Panel");
	    editPanel = new EditRegistration(registrationController, this);
	    cardPanel.add(editPanel, "Edit Panel");

	    createButton.addActionListener(e -> {
	      cardLayout.show(cardPanel, "Create Panel");
	    });
	  }

	  public void refreshListPanel() {
	    splitPane.setBottomComponent(createListPanel());
	    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    splitPane.setDividerLocation(75);
	    splitPane.setDividerSize(0);
	    revalidate();
	    repaint();
	  }

	  private JScrollPane createListPanel() {
	    var registrations = this.registrationController.getAll();

	    Object[][] data = new Object[registrations.size()][5];

	    int i = 0;

	    while (!registrations.isEmpty()) {
	      var registration = registrations.dequeue();
	      data[i][0] = registration.getId();
	      data[i][1] = registration.getCPFRegistration();
	      data[i][2] = registration.getCodigoDisciplinaRegistration();
	      data[i][3] = registration.getCodigoProcessoRegistration();
	      data[i][4] = "Ações";
	      i++;
	    }

	    var columnNames = new String[] { "Id", "CPF", "Cód. da Disciplina", "Cód. do Processo", "Ações" };

	    var tableModel = new CustomTableModel(data, columnNames, new int[] { 4 });
	    var columnModel = new FixedTableColumnModel(0);

	    var table = new JTable(tableModel, columnModel);
	    table.createDefaultColumnsFromModel();
	    table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer());

	    var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox());

	    buttonPanelEditor.getEditButton().addActionListener(e -> {
	      var row = table.getSelectedRow();
	      var id = table.getValueAt(row, 0);
	      var nCPFRegistration = table.getValueAt(row, 1);
	      var codigoDisciplinaRegistration = table.getValueAt(row, 2);
	      var codigoProcessoRegistration = table.getValueAt(row, 3);
		  
	      var registrationDto = new RegistrationDto();
		  registrationDto.setCPFRegistration((String) nCPFRegistration);
		  registrationDto.setCodigoDisciplinaRegistration((String) codigoDisciplinaRegistration);
		  registrationDto.setCodigoProcessoRegistration((String) codigoProcessoRegistration);

	      editPanel.setRegistrationId((Integer) id);
	      editPanel.setRegistrationDto(registrationDto);
	      editPanel.loadData();

	      cardLayout.show(cardPanel, "Edit Panel");
	    });

	    buttonPanelEditor.getDeleteButton().addActionListener(e -> {
	      var row = table.getSelectedRow();
	      var id = table.getValueAt(row, 0);
	      registrationController.delete((Integer) id);

	      refreshListPanel();
	    });

	    table.getColumn("Ações").setCellEditor(buttonPanelEditor);

	    table.getColumnModel().getColumn(0).setMinWidth(0);
	    table.getColumnModel().getColumn(0).setMaxWidth(0);
	    table.getColumnModel().getColumn(0).setWidth(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(0);
	    table.getColumnModel().getColumn(4).setPreferredWidth(160);

	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    var scrollPane = new JScrollPane(table);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	    return scrollPane;
	  }

	  private JPanel createMenuPanel() {
	    menuPanel = new JPanel();
	    menuPanel.setLayout(null);

	    var filterByLabel = new JLabel("Filtrar por");
	    filterByLabel.setBounds(10, 5, 200, 25);

	    filterByComboBox = new JComboBox<>(new String[] { "CPF", "Cód. da Disciplina", "Cód. do Processo" });
	    filterByComboBox.setBounds(10, 26, 160, 23);

	    searchTextField = new JTextField();
	    searchTextField.setBounds(172, 26, 200, 25);

	    searchButton = new JButton("buscar");
	    searchButton.setBounds(374, 26, 100, 23);

	    createButton = new JButton("cadastrar");
	    createButton.setBounds(550, 26, 100, 23);

	    menuPanel.add(filterByLabel);
	    menuPanel.add(filterByComboBox);
	    menuPanel.add(searchTextField);
	    menuPanel.add(searchButton);
	    menuPanel.add(createButton);

	    return menuPanel;
	  }
}
