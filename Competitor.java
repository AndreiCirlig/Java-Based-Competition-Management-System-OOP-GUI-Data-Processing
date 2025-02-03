
import java.util.*;
import java.util.stream.Collectors;

public class Competitor {
    protected int competitorNumber;
    protected Name name;
    protected int age;
    protected String country;
    protected String gender;
    protected int[] scores = new int[4]; // Array of integer scores



    public Competitor(int competitorNumber, Name name, int age, String gender, String country, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.country = country;
        setScores(scores);
    }

    public String getFullDetailsForAll() {
        return String.format("Competitor number %d, name %s, country %s.\n%s is aged %d and has an overall score of %.1f.",
                competitorNumber, name.getFullName(), country, name.getFirstName(), age, getOverallScore());
    }


    @Override
    public String toString() {
        return this.name.getFullName();
    }

    public String getFullDetails() {
        String scoresString = Arrays.stream(this.scores)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        return String.format("Competitor Number: %d, Name: %s, Age: %d, Gender: %s, Country: %s, Scores: %s",
                this.competitorNumber,
                formatCompetitorName(this.name),
                this.age,
                this.gender,
                this.country,
                scoresString);
    }

    private String formatCompetitorName(Name name) {
        return String.join(" ", name.getFirstName(), name.getMiddleName(), name.getLastName()).trim();
    }

    public String getShortDetails() {
        return String.format("CN %d (%s) has overall score %.1f",
                this.competitorNumber,
                this.getName().getInitials(),
                this.getOverallScore());
    }


    public int getCompetitorNumber() {
        return competitorNumber;
    }
    public Name getName() {
        return name;
    }

    public int[] getScoreArray() {
        return scores;
    }
    public double getOverallScore() {
        double totalScore = 0.0;
        double weightSum = 0.0;
        double[] weights = {1.0, 1.2, 1.4, 1.6, 1.8, 2.0}; // Example weights

        for (int i = 0; i < scores.length; i++) {
            double weight = weights[i % weights.length];
            totalScore += scores[i] * weight;
            weightSum += weight;
        }

        double averageScore = totalScore / weightSum;
        return Math.round(averageScore * 100.0) / 100.0; // Rounds to two decimal places
    }

    public void setScores(int[] scores) {
        if (scores.length != this.scores.length) {
            throw new IllegalArgumentException("Scores array must have exactly " + this.scores.length + " elements.");
        }
        for (int score : scores) {
            if (score < 0 || score > 6) {
                throw new IllegalArgumentException("Score values must be between 0 and 5.");
            }
        }
        this.scores = Arrays.copyOf(scores, scores.length); // Copy for immutability
    }


    public int getAge() {
        return age;
    }

    public Object getGender() {
        return gender;
    }

    public Object getCountry() {
        return country;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
