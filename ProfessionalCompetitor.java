public class ProfessionalCompetitor extends Competitor {
    private String professionalLevel; // Additional attribute specific to professional competitors

    public ProfessionalCompetitor(int competitorNumber, Name name, int age, String gender, String country, String professionalLevel, int scores[]) {
        super(competitorNumber, name, age, gender, country, scores);
        this.professionalLevel = professionalLevel;
    }

    public String getProfessionalLevel() {
        return professionalLevel;
    }

    @Override
    public String toString() {
        return getName().getFullName() + "-Professional(" + getProfessionalLevel() + ")";
    }
}
