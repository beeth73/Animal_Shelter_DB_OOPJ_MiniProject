public class Adoption {
    private int AnimalId;
    private int AdopterId;
    private String date;
    private double fee;

    public Adoption(int AnimalId, int AdopterId, String date, double fee) {
        this.AnimalId = AnimalId;
        this.AdopterId = AdopterId;
        this.date = date;
        this.fee = fee;
    }

    public int getAnimalId() { return AnimalId; }
    public int getAdopterId() { return AdopterId; }
    public String getDate() { return date; }
    public double getFee() { return fee; }
}
