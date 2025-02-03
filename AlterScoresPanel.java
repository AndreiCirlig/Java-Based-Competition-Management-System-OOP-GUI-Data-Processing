import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterScoresPanel extends JPanel {
    private final JComboBox<Competitor> competitorDropdown;
    private final JTextField[] scoreFields;
    private final JLabel overallScoreLabel;
    private final CompetitorList competitorList;

    public AlterScoresPanel(CompetitorList competitorList, ViewCompetitorsPanel viewCompetitorsPanel) {
        this.competitorList = competitorList;
        setLayout(new BorderLayout());

        // Dropdown for selecting a competitor
        competitorDropdown = new JComboBox<>();
        populateCompetitorDropdown();
        add(competitorDropdown, BorderLayout.NORTH);

        // Panel for score input fields
        JPanel scoresPanel = new JPanel();
        scoreFields = new JTextField[4]; // Assuming 4 scores per competitor
        for (int i = 0; i < scoreFields.length; i++) {
            scoreFields[i] = new JTextField(3); // Width of 3 columns
            scoresPanel.add(scoreFields[i]);
        }
        add(scoresPanel, BorderLayout.CENTER);

        // Button to update scores
        JButton updateButton = new JButton("Update Scores");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCompetitorScores();
                viewCompetitorsPanel.refreshTable();
            }
        });
        add(updateButton, BorderLayout.EAST);

        // Label to display overall score
        overallScoreLabel = new JLabel("Overall Score: ");
        add(overallScoreLabel, BorderLayout.SOUTH);

        // Listener for competitor selection
        competitorDropdown.addActionListener(e -> fillCurrentScores());
    }

    private void populateCompetitorDropdown() {
        for (Competitor competitor : competitorList.getCompetitors()) {
            competitorDropdown.addItem(competitor);
        }
    }

    private void fillCurrentScores() {
        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        if (selectedCompetitor != null) {
            int[] scores = selectedCompetitor.getScoreArray();
            for (int i = 0; i < scores.length; i++) {
                scoreFields[i].setText(String.valueOf(scores[i]));
            }
        }
    }

    private void updateCompetitorScores() {
        Competitor selectedCompetitor = (Competitor) competitorDropdown.getSelectedItem();
        if (selectedCompetitor != null) {
            int[] newScores = new int[scoreFields.length];
            for (int i = 0; i < newScores.length; i++) {
                try {
                    int score = Integer.parseInt(scoreFields[i].getText());
                    if (score < 0 || score > 5) {
                        JOptionPane.showMessageDialog(this, "Please input a number between 0 and 5 for all scores.");
                        return;
                    }
                    newScores[i] = score;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid score format");
                    return;
                }
            }
            selectedCompetitor.setScores(newScores);
            double overallScore = selectedCompetitor.getOverallScore();
            overallScoreLabel.setText("Overall Score: " + overallScore);
        }
    }
    public void refreshCompetitorDropdown() {
        competitorDropdown.removeAllItems(); // Clear existing items
        for (Competitor competitor : competitorList.getCompetitors()) {
            competitorDropdown.addItem(competitor);
        }
    }
}
