import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Manager {
    private CompetitorList competitorList;

    public Manager(CompetitorList competitorList) {
        this.competitorList = competitorList;
    }

    // Method to generate the full report
    public void generateFinalReport(String outputPath) {
        StringBuilder report = new StringBuilder();

        // Competitors' Details
        report.append("Competitors' Details:\n");
        for (Competitor competitor : competitorList.getCompetitors()) {
            double overallScore = competitor.getOverallScore(); // Retrieve overall score
            report.append(competitor.getFullDetails())
                    .append(" - Overall Score: ")
                    .append(String.format("%.1f", overallScore))
                    .append("\n");
        }
        report.append("\n");

        // Highest Scorer Details
        Competitor highestScorer = competitorList.getHighestScoringCompetitor();
        if (highestScorer != null) {
            double overallScore = highestScorer.getOverallScore();
            report.append("Highest Scorer Details:\n")
                    .append(highestScorer.getFullDetails())
                    .append(" - Overall Score: ")
                    .append(String.format("%.1f", overallScore))
                    .append("\n\n");
        }

        // Summary Statistics
        report.append("Summary Statistics:\n");
        report.append("Total Number of Competitors: ").append(competitorList.getTotalNumberOfCompetitors()).append("\n");
        report.append("Average Score: ").append(competitorList.getAverageScore()).append("\n");
        report.append("Maximum Score: ").append(competitorList.getMaxScore()).append("\n");
        report.append("Minimum Score: ").append(competitorList.getMinScore()).append("\n\n");

        // Frequency Report
        report.append("Frequency Report:\n");
        Map<Integer, Integer> scoreFrequency = competitorList.getScoreFrequency();
        for (int score = 0; score <= 5; score++) {
            report.append("Score ").append(score).append(": ")
                    .append(scoreFrequency.getOrDefault(score, 0))
                    .append("\n");
        }

        // Print the report to console
        System.out.println(report);

        // Write the report to a file
        try {
            Files.writeString(Paths.get(outputPath), report);
        } catch (IOException e) {
            System.err.println("Error writing report to file: " + e.getMessage());
        }
    }




    public void displayCompetitorDetails() {
        Scanner scanner = new Scanner(System.in);
        int competitorNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter competitor number from the report (e.g.100-119): ");
                competitorNumber = scanner.nextInt();

                if (competitorNumber >= 100 && competitorNumber <= 119) {
                    validInput = true; // Valid number, exit the loop
                } else {
                    System.out.println("Please enter a competitor number from the report visible above.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        Competitor competitor = competitorList.findCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            System.out.println(competitor.getShortDetails()); // Provide the competitor details
        } else {
            System.out.println("Competitor with number " + competitorNumber + " not found.");
        }
    }

}