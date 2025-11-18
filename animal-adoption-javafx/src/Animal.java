public class Animal {
    private int Aid;
    private String healthStatus;
    private String type;
    private String breed;
    private int age;
    private int ShelterId;

    public Animal(int Aid, String healthStatus, String type, String breed, int age, int ShelterId) {
        this.Aid = Aid;
        this.healthStatus = healthStatus;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.ShelterId = ShelterId;
    }

    public int getAid() { return Aid; }
    public String getHealthStatus() { return healthStatus; }
    public String getType() { return type; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public int getShelterId() { return ShelterId; }
}
