public class Shelter {
    private int Sid;
    private String Sname;
    private String location;

    public Shelter(int Sid, String Sname, String location) {
        this.Sid = Sid;
        this.Sname = Sname;
        this.location = location;
    }

    public int getSid() { return Sid; }
    public String getSname() { return Sname; }
    public String getLocation() { return location; }
}
