package miw.fellowshipfungi.models.profile;

public class ProfileData {
    private long lengthCollection;
    private int streak;
    private double bestResult;

    public void setLengthCollection(long lengthCollection) {
        this.lengthCollection = lengthCollection;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setBestResult(double bestResult) {
        this.bestResult = bestResult;
    }

    public long getLengthCollection() {
        return lengthCollection;
    }

    public int getStreak() {
        return streak;
    }

    public double getBestResult() {
        return bestResult;
    }
}
