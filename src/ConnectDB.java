import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	int r = 0;

	/**
	 * DB 접속
	 */
	public ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 드라이버 로딩: DriverManager에 등록
		} catch (ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
		}

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/test";// 사용하는 데이터베이스명을
																// 포함한 url
			String userId = "root";// 사용자계정
			String userPass = "dksgytkd";// 사용자 패스워드

			conn = DriverManager.getConnection(jdbcUrl, userId, userPass);// Connection
																			// 객체를
																			// 얻어냄

			stmt = conn.createStatement();// Statement 객체를 얻어냄

			System.out.println("제대로 연결되었습니다");// 성공시 화면에 표시됨

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public void InsertDB(String SQL) {
		String name = null;
		String title = null;
		String contents = null;
		String hit = null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			r = stmt.executeUpdate("insert into freeboard "
					+ "(name,title,contents,hit) value ('" + name + "','" + title
					+ "','" + contents + "'," + hit + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (r == 1) {
			System.out.println("성공적으로 글이 입력되었습니다.");
		} else {
			System.out.println("입력 실패!");
		}
	}
}
