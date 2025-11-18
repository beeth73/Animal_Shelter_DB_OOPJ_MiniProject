import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShelterDAO {

    public List<Shelter> getAll() throws SQLException {
        List<Shelter> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("No DB connection");
            String sql = "SELECT Sid, Sname, loc FROM SHELTER";
            try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Shelter(rs.getInt("Sid"), rs.getString("Sname"), rs.getString("loc")));
                }
            }
        }
        return list;
    }

    public boolean insert(Shelter s) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("No DB connection");
            String sql = "INSERT INTO SHELTER (Sid, Sname, loc) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, s.getSid());
                ps.setString(2, s.getSname());
                ps.setString(3, s.getLocation());
                return ps.executeUpdate() == 1;
            }
        }
    }

    public boolean update(Shelter s) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("No DB connection");
            String sql = "UPDATE SHELTER SET Sname = ?, loc = ? WHERE Sid = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, s.getSname());
                ps.setString(2, s.getLocation());
                ps.setInt(3, s.getSid());
                return ps.executeUpdate() == 1;
            }
        }
    }

    public boolean delete(int sid) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("No DB connection");
            String sql = "DELETE FROM SHELTER WHERE Sid = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, sid);
                return ps.executeUpdate() == 1;
            }
        }
    }
}
