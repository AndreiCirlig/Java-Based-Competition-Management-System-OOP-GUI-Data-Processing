import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Load competitors from file
        CompetitionSystem.loadCompetitors();
        CompetitorList competitorList = new CompetitorList();
        List<Competitor> competitorsFromJson = CompetitionSystem.loadCompetitorsFromJson();
        // Adding Amateur Competitors
        competitorList.addCompetitor(new AmateurCompetitor(1, new Name("John", "Doe", ""), 25, "Male", "USA", "Novice", new int[] {2, 5, 4, 2}));
        competitorList.addCompetitor(new AmateurCompetitor(2, new Name("Alice", "Smith", ""), 22, "Female", "UK", "Intermediate", new int[] {3, 4, 4, 3}));
        competitorList.addCompetitor(new AmateurCompetitor(3, new Name("Bob", "Brown", ""), 30, "Male", "UAE", "Advanced", new int[] {4, 4, 5, 4}));

        // Adding Professional Competitors
        competitorList.addCompetitor(new ProfessionalCompetitor(4, new Name("Clara", "Johnson", ""), 28, "Female", "USA", "Expert", new int[] {5, 4, 5, 5}));
        competitorList.addCompetitor(new ProfessionalCompetitor(5, new Name("Dave", "Wilson", ""), 35, "Male", "UAE", "Elite", new int[] {5, 5, 5, 5}));

        Manager manager = new Manager(competitorList);

        if (competitorsFromJson != null) {
            for (Competitor competitor : competitorsFromJson) {
                competitorList.addCompetitor(competitor);
            }
        }

        // Generate and print the report
        String outputPath = "final_report.txt"; // Path for the output file
        manager.generateFinalReport(outputPath);
        manager.displayCompetitorDetails();


        SwingUtilities.invokeLater(() -> {
            CompetitorManagerGUI frame = new CompetitorManagerGUI(competitorList);
            frame.setVisible(true);
        });

    }







    }
