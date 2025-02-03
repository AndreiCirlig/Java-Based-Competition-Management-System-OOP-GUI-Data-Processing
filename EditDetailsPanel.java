import javax.swing.*;
import java.awt.*;



public class EditDetailsPanel extends JPanel {
    private final CompetitorList competitorList;
    private final JComboBox<Competitor> competitorDropdown;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextArea detailsArea;
    private final JTextField countryInputField;
    private final JRadioButton maleRadioButton;
    private final JRadioButton femaleRadioButton;



    public EditDetailsPanel(CompetitorList competitorList, ViewCompetitorsPanel viewCompetitorsPanel, AlterScoresPanel alterScoresPanel) {
        this.competitorList = competitorList;
        setLayout(new BorderLayout());

        // Initialize components
        competitorDropdown = new JComboBox<>();
        populateCompetitorDropdown();
        nameField = new JTextField(20);
        ageField = new JTextField(5);
        countryInputField = new JTextField(10); // For country input



        // Initialize gender radio buttons
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        // Initialize buttons
        JButton viewFullDetailsButton = new JButton("View Full Details");
        JButton viewShortDetailsButton = new JButton("View Short Details");
        JButton editDetailsButton = new JButton("Edit Details");
        JButton removeButton = new JButton("Remove Competitor");


        // Create panels for layout
        JPanel upperPanel = new JPanel(new FlowLayout());
        upperPanel.add(competitorDropdown);
        upperPanel.add(new JLabel("Name:"));
        upperPanel.add(nameField);
        upperPanel.add(new JLabel("Age:"));
        upperPanel.add(ageField);
        upperPanel.add(new JLabel("Country:"));
        upperPanel.add(countryInputField);
        // Gender panel
        JPanel genderPanel = new JPanel(new FlowLayout());
        genderPanel.add(new JLabel("Gender:"));
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);


        JPanel lowerPanel = new JPanel(new FlowLayout());
        lowerPanel.add(viewFullDetailsButton);
        lowerPanel.add(viewShortDetailsButton);
        lowerPanel.add(editDetailsButton);
        lowerPanel.add(removeButton);

        // Main control panel with GridLayout
        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        controlPanel.add(upperPanel);
        controlPanel.add(genderPanel); // Add the gender panel separately
        controlPanel.add(lowerPanel);

        // Add panels to main layout
        add(controlPanel, BorderLayout.NORTH);
        detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // Add action listeners

        viewFullDetailsButton.addActionListener(e -> viewFullDetails());
        viewShortDetailsButton.addActionListener(e -> viewShortDetails());
        editDetailsButton.addActionListener(e -> {
            editCompetitorDetails();
            if (alterScoresPanel != null) {
                alterScoresPanel.refreshCompetitorDropdown(); // Refresh AlterScoresPanel's dropdown
            }
            if (viewCompetitorsPanel != null) {
                viewCompetitorsPanel.refreshTable(); // Refresh ViewCompetitorsPanel's table
            }
            viewFullDetails();
            refreshCompetitorDropdown();
            resetInputFields(); // Reset fields after editing



        });
        removeButton.addActionListener(e -> {
            removeCompetitor();
            if (alterScoresPanel != null) {
                alterScoresPanel.refreshCompetitorDropdown(); // Refresh AlterScoresPanel's dropdown
            }
            if (viewCompetitorsPanel != null) {
                viewCompetitorsPanel.refreshTable(); // Refresh ViewCompetitorsPanel's table
            }
            viewFullDetails();
            refreshCompetitorDropdown();
            resetInputFields(); // Reset fields after editing


        });


    }




    // Method to refresh the competitor dropdown
    private void refreshCompetitorDropdown() {
        competitorDropdown.removeAllItems(); // Remove all current items
        populateCompetitorDropdown(); // Re-populate with updated competitor list
    }

    private void populateCompetitorDropdown() {
        for (Competitor competitor : competitorList.getCompetitors()) {
            competitorDropdown.addItem(competitor);
        }
    }




    private void viewFullDetails() {
        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        if (selectedCompetitor != null) {
            detailsArea.setText(selectedCompetitor.getFullDetails());
        }
    }

    private void viewShortDetails() {
        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        if (selectedCompetitor != null) {
            detailsArea.setText(selectedCompetitor.getShortDetails());
        }
    }
    private void editCompetitorDetails() {

        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        String[] nameParts = nameField.getText().split(" ");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String middleName = nameParts.length > 2 ? nameParts[1] : "";
        String lastName = nameParts.length > 2 ? nameParts[2] : (nameParts.length > 1 ? nameParts[1] : "");

        if (selectedCompetitor != null) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                return;
            }
            selectedCompetitor.setName(new Name(firstName, middleName, lastName));
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                selectedCompetitor.setAge(age);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Only the name has been changed. ");
                selectedCompetitor.setAge(Integer.parseInt(String.valueOf(selectedCompetitor.getAge())));

            }
            if (maleRadioButton.isSelected()) {
                selectedCompetitor.setGender("Male");
            } else if (femaleRadioButton.isSelected()) {
                selectedCompetitor.setGender("Female");
            } else {

                selectedCompetitor.setGender((String) selectedCompetitor.getGender());
            }
            // Validate and set country
            String country = countryInputField.getText().trim();
            if (!country.matches("[a-zA-Z ]*")) {
                JOptionPane.showMessageDialog(this, "Country should only contain letters.");
                return;
            }
            if (country.isEmpty()) {
                country = (String) selectedCompetitor.getCountry();
            }
            selectedCompetitor.setCountry(country);
        }
    }

    private void resetInputFields() {
        nameField.setText("");
        ageField.setText("");
        countryInputField.setText("");
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);

    }

    private void removeCompetitor() {
        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        if (selectedCompetitor != null) {
            competitorList.removeCompetitor(selectedCompetitor);
            competitorDropdown.removeItem(selectedCompetitor);

        }
    }


    }
