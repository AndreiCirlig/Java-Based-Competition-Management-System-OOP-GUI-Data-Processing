import javax.swing.*;
import java.awt.*;

public class CompetitorManagerGUI extends JFrame {

    public CompetitorManagerGUI(CompetitorList competitorList) {
        setTitle("Competitor Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ViewCompetitorsPanel viewCompetitorsPanel = new ViewCompetitorsPanel(competitorList);
        AlterScoresPanel alterScoresPanel = new AlterScoresPanel(competitorList, viewCompetitorsPanel);
        EditDetailsPanel editDetailsPanel = new EditDetailsPanel(competitorList, viewCompetitorsPanel, alterScoresPanel);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Alter Scores", alterScoresPanel);
        tabbedPane.addTab("View Competitors", viewCompetitorsPanel);
        tabbedPane.addTab("Edit Competitor Details", editDetailsPanel);
        add(tabbedPane, BorderLayout.CENTER);

    }


}
