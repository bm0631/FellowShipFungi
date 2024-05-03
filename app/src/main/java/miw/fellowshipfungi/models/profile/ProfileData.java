package miw.fellowshipfungi.models.profile;

public class ProfileData {
    private long lengthCollection;
    private int streak;
    private double bestResult;
    private String username;

    public long getLengthCollection() {
        return lengthCollection;
    }

    public void setLengthCollection(long lengthCollection) {
        this.lengthCollection = lengthCollection;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public double getBestResult() {
        return bestResult;
    }

    public void setBestResult(double bestResult) {
        this.bestResult = bestResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
