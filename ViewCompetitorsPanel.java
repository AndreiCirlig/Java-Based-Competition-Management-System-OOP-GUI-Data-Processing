import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewCompetitorsPanel extends JPanel {
    private final CompetitorList competitorList;
    private final JTable competitorTable;
    private final DefaultTableModel tableModel;
    private JComboBox<String> countryFilter;
    private final JToggleButton maleFilter;
    private final JToggleButton femaleFilter;
    private final JTextField searchField;

    private final JComboBox<String> typeFilter;

    public ViewCompetitorsPanel(CompetitorList competitorList) {
        this.competitorList = competitorList;
        setLayout(new BorderLayout());

        // Table Model
        String[] columnNames = {"Competitor Number", "Name", "Age", "Country", "Gender", "Type", "Level", "Overall Score"};
        tableModel = new DefaultTableModel(columnNames, 0);
        competitorTable = new JTable(tableModel);

        // Populate table
        populateTable(competitorList.getCompetitors());
        // Setup search bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(10); // Width of 10 columns
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Competitor Number:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add search panel to the main panel
        add(searchPanel, BorderLayout.SOUTH);

        // Setup search button action listener
        searchButton.addActionListener(e -> searchCompetitor());


        // Sorting Buttons
        JButton sortHighToLowButton = new JButton("Sort High to Low");
        JButton sortLowToHighButton = new JButton("Sort Low to High");
        sortHighToLowButton.addActionListener(e -> sortCompetitors(true));
        sortLowToHighButton.addActionListener(e -> sortCompetitors(false));

        // Filtering Options
        countryFilter = new JComboBox<>();
        maleFilter = new JToggleButton("Male");
        femaleFilter = new JToggleButton("Female");

        maleFilter.addActionListener(e -> toggleGenderFilter(femaleFilter));
        femaleFilter.addActionListener(e -> toggleGenderFilter(maleFilter));

        // Country Filtering Options
        String[] countries = {"All Countries", "US", "UK", "UAE"};
        countryFilter = new JComboBox<>(countries);
        countryFilter.addActionListener(e -> filterCompetitors());

        // Setup type filter dropdown
        typeFilter = new JComboBox<>(new String[] {"All", "Amateur", "Professional"});
        typeFilter.addActionListener(e -> filterCompetitors());


        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.add(sortHighToLowButton);
        controlPanel.add(sortLowToHighButton);
        controlPanel.add(countryFilter);
        controlPanel.add(maleFilter);
        controlPanel.add(femaleFilter);
        controlPanel.add(new JLabel("Competitor Type:"));
        controlPanel.add(typeFilter);

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(competitorTable), BorderLayout.CENTER);
    }

    private void populateTable(List<Competitor> competitors) {
        DefaultTableModel model = (DefaultTableModel) competitorTable.getModel();
        model.setRowCount(0);
        for (Competitor competitor : competitors) {
            String type;
            String level;

            if (competitor instanceof AmateurCompetitor) {
                type = "Amateur";
                level = ((AmateurCompetitor) competitor).getAmateurLevel();
            } else if (competitor instanceof ProfessionalCompetitor) {
                type = "Professional";
                level = ((ProfessionalCompetitor) competitor).getProfessionalLevel();
            } else {
                type = "Unknown";
                level = "N/A";
            }

            model.addRow(new Object[]{
                    competitor.getCompetitorNumber(),
                    competitor.getName().getFullName(),
                    competitor.getAge(),
                    competitor.getGender(),
                    competitor.getCountry(),
                    type,
                    level,
                    competitor.getOverallScore()

            });
        }
    }

    private void sortCompetitors(boolean highToLow) {
        Comparator<Competitor> comparator = Comparator.comparingDouble(Competitor::getOverallScore);
        if (highToLow) {
            comparator = comparator.reversed();
        }
        List<Competitor> sortedList = getFilteredCompetitorStream()
                .sorted(comparator)
                .collect(Collectors.toList());
        populateTable(sortedList);
    }

    private void toggleGenderFilter(JToggleButton otherButton) {
        if (otherButton.isSelected()) {
            otherButton.setSelected(false);
        }
        filterCompetitors();
    }

    private void filterCompetitors() {
        List<Competitor> filteredList = getFilteredCompetitorStream()
                .collect(Collectors.toList());
        populateTable(filteredList);
    }

    private Stream<Competitor> getFilteredCompetitorStream() {
        String selectedCountry = (String) countryFilter.getSelectedItem();
        String selectedType = (String) typeFilter.getSelectedItem(); // Assumes a typeFilter dropdown exists
        boolean maleSelected = maleFilter.isSelected();
        boolean femaleSelected = femaleFilter.isSelected();

        return competitorList.getCompetitors().stream()
                .filter(c -> {
                    if ((!"All Countries".equals(selectedCountry) && !c.getCountry().equals(selectedCountry)) ||
                            (maleSelected && !c.getGender().equals("Male")) ||
                            (femaleSelected && !c.getGender().equals("Female"))) return false;
                    assert selectedType != null;
                    return selectedType.equals("All") ||
                            (selectedType.equals("Amateur") && c instanceof AmateurCompetitor) ||
                            (selectedType.equals("Professional") && c instanceof ProfessionalCompetitor);
                });
    }

    private void searchCompetitor() {
        if (searchField.getText().isEmpty()) {
            // If search field is empty, repopulate the table with all competitors
            refreshTable();
            return;
        }

        try {
            int competitorNumber = Integer.parseInt(searchField.getText());
            Competitor foundCompetitor = competitorList.findCompetitorByNumber(competitorNumber);

            if (foundCompetitor != null) {
                displayCompetitorDetails(foundCompetitor);
            } else {
                JOptionPane.showMessageDialog(this, "Competitor Number invalid");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
        }
    }

    private void displayCompetitorDetails(Competitor competitor) {
        // Clear existing table data
        tableModel.setRowCount(0);

        String type;
        String level;

        if (competitor instanceof AmateurCompetitor) {
            type = "Amateur";
            level = ((AmateurCompetitor) competitor).getAmateurLevel();
        } else if (competitor instanceof ProfessionalCompetitor) {
            type = "Professional";
            level = ((ProfessionalCompetitor) competitor).getProfessionalLevel();
        } else {
            type = "Unknown";
            level = "N/A";
        }

        tableModel.addRow(new Object[]{
                competitor.getCompetitorNumber(),
                competitor.getName().getFullName(),
                competitor.getAge(),
                competitor.getGender(),
                competitor.getCountry(),
                type,
                level,
                competitor.getOverallScore()

        });
    }




    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) competitorTable.getModel();
        model.setRowCount(0);
        for (Competitor competitor : competitorList.getCompetitors()) {
            String type;
            String level;

            if (competitor instanceof AmateurCompetitor) {
                type = "Amateur";
                level = ((AmateurCompetitor) competitor).getAmateurLevel();
            } else if (competitor instanceof ProfessionalCompetitor) {
                type = "Professional";
                level = ((ProfessionalCompetitor) competitor).getProfessionalLevel();
            } else {
                type = "Unknown";
                level = "N/A";
            }

            model.addRow(new Object[]{
                    competitor.getCompetitorNumber(),
                    competitor.getName().getFullName(),
                    competitor.getAge(),
                    competitor.getGender(),
                    competitor.getCountry(),
                    type,
                    level,
                    competitor.getOverallScore()

            });
        }
    }


}
