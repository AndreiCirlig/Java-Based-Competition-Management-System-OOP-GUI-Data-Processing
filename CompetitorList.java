import java.util.*;

public class CompetitorList {
    private  List<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    public  void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public void removeCompetitor(Competitor competitorToRemove) {
        if (competitorToRemove == null) {
            return;
        }

        // Using an iterator to safely remove an item while iterating
        Iterator<Competitor> iterator = competitors.iterator();
        while (iterator.hasNext()) {
            Competitor competitor = iterator.next();
            if (competitor.getCompetitorNumber() == competitorToRemove.getCompetitorNumber()) {
                iterator.remove();
                break;
            }
        }
    }

    public  Competitor getHighestScoringCompetitor() {
        return competitors.stream()
                .max(Comparator.comparingDouble(Competitor::getOverallScore))
                .orElse(null);
    }

    public int getTotalNumberOfCompetitors() {
        return competitors.size();
    }

    // Average of All Competitors' Scores
    public double getAverageScore() {
        double totalSum = 0;
        int totalScores = 0;
        for (Competitor competitor : competitors) {
            for (int score : competitor.getScoreArray()) {
                totalSum += score;
                totalScores++;
            }
        }
        return totalScores > 0 ? totalSum / totalScores : 0.0;
    }

    // Maximum Score Across All Competitors
    public int getMaxScore() {
        return competitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScoreArray()))
                .max()
                .orElse(0);
    }

    // Minimum Score Across All Competitors
    public int getMinScore() {
        return competitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScoreArray()))
                .min()
                .orElse(0);
    }

    // Frequency Report for Individual Scores
    public Map<Integer, Integer> getScoreFrequency() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i <= 5; i++) {
            frequencyMap.put(i, 0);
        }
        competitors.forEach(competitor -> {
            for (int score : competitor.getScoreArray()) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        });
        return frequencyMap;
    }



    public List<Competitor> getCompetitors() {
        return competitors;
    }


    public Competitor findCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }
}