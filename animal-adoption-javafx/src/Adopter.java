public class Adopter {
    private int Adid;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Adopter(int Adid, String name, String address, String email, String phone) {
        this.Adid = Adid;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getAdid() { return Adid; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
