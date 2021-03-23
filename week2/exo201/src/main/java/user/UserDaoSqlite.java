package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;

public class UserDaoSqlite implements UserDao {

    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_LASTNAME = "lastname";
    private static final String COL_EMAIL = "email";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    protected Connection conn;

    public UserDaoSqlite(String userFilePath) throws SQLException {

        String jdbcUrl = String.format("jdbc:sqlite:%s", userFilePath);
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(jdbcUrl);
        this.conn = ds.getConnection();
    }

    @Override
    public void add(User user, String password) {
        try (
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (firstname, lastname, email, password) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, password);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user, String password) {
        try (
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ? WHERE id = ?")) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, password);
            ps.setLong(5, user.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(long id) {
        User user = null;
        try (PreparedStatement ps = conn.prepareStatement("SELECT firstname, lastname, email WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            user = new User();
            user.setId(id);
            user.setFirstname(rs.getString(COL_FIRSTNAME));
            user.setLastname(rs.getString(COL_LASTNAME));
            user.setEmail(rs.getString(COL_EMAIL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (
            PreparedStatement ps = conn.prepareStatement("SELECT id, firstname, lastname FROM users WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString(COL_FIRSTNAME));
                user.setLastname(rs.getString(COL_LASTNAME));
                user.setEmail(email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public long checkPassword(String email, String password) {
        User user = null;
        try (
            PreparedStatement ps = conn.prepareStatement("SELECT id, firstname, lastname FROM users WHERE email = ? and password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString(COL_FIRSTNAME));
                user.setLastname(rs.getString(COL_LASTNAME));
                user.setEmail(email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user == null ? -1 : user.getId();
    }

    /**
     * delete a user that match this ID
     */
    @Override
    public void delete(long id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long exists(String email) {
        User user = findByEmail(email);

        return user == null ? -1 : user.getId();
    }

}
