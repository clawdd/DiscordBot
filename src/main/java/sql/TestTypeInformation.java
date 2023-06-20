package sql;

public class TestTypeInformation {

    private String userID;
    private String testType;
    private int pointsPossible;
    private double percentageToPass;
    private int numOfTests;
    private int pointsAdmission;
    public TestTypeInformation(String userID, String testType, int pointsPossible, double percentageToPass, int numOfTests, int pointsAdmission) {

        this.userID = userID;
        this.testType = testType;
        this.pointsPossible = pointsPossible;
        this.percentageToPass = percentageToPass;
        this.numOfTests = numOfTests;
        this.pointsAdmission = pointsAdmission;

    }

    public String getUserID() {
        return userID;
    }

    public String getTestType() {
        return testType;
    }

    public int getPointsPossible() {
        return pointsPossible;
    }

    public double getPercentageToPass() {
        return percentageToPass;
    }

    public int getNumOfTests() {
        return numOfTests;
    }

    public int getPointsAdmission() {
        return pointsAdmission;
    }
}
