
public class AmateurCompetitor extends Competitor {
    private final String amateurLevel; // Additional attribute specific to amateur competitors

    public AmateurCompetitor(int competitorNumber, Name name, int age, String gender, String country, String amateurLevel, int scores[]) {
        super(competitorNumber, name, age, gender, country, scores);
        this.amateurLevel = amateurLevel;
    }



    public String getAmateurLevel() {
        return amateurLevel;
    }

    @Override
    public String toString() {
        return getName().getFullName() + "-Amateur(" + getAmateurLevel() + ")";
    }
}

