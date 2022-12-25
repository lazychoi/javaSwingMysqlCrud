package customerManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Customer {
	private PreparedStatement psmt;
	private Connection con;
	private ResultSet rs;

	// update 
	public void updateCustomer(String id, String name, String tel, String address, String grade) {
		try {
			con = getConnection();
			psmt = con.prepareStatement("UPDATE customertb SET name ='" + name + 
					"', tel = '" + tel + 
					"', address = '" + address + 
					"', grade ='" + grade + 
					"' WHERE id ='" + id +"' ;");
			psmt.executeUpdate();
			System.out.println("고객 정보가 변경되었습니다");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close(psmt, con);
		}
	}
	
	// get last ID
	public int getLastId() {
		try {
			con = getConnection();
			psmt = con.prepareStatement("SELECT id FROM customertb ORDER BY id DESC LIMIT 1;");
			ResultSet rs = psmt.executeQuery();
			rs.next();        //ResultSet에서 데이터를 읽을 때 cursor point를 첫 번째 로우에 맞추어야 한다.
			return rs.getInt(1);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return -1; 
		} finally {
			close(rs, psmt, con);
		}
	}
	
	// delete 
	public void deleteCustomer(String id) {
		try {
			con = getConnection();
			psmt = con.prepareStatement("DELETE FROM customertb WHERE id = '" + id + "'");
			psmt.executeUpdate();
			System.out.println("고객 정보가 삭제되었습니다");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close(psmt, con);
		}
	}

	// table 데이터 가져오기. JTable이 문자열 2차원 배열을 입력값으로 받기 때문에  
	public String[][] getCustomers(){ 
		try {
			con = getConnection();
			psmt = con.prepareStatement("SELECT id, name, tel, address, grade FROM customertb");
			ResultSet rs = psmt.executeQuery();
			ArrayList<String[]> list = new ArrayList<String[]>();   // 여기부터 잘 이해가 안 된다.
			while(rs.next()) {
				list.add(new String[] {
						rs.getString("id"),
						rs.getString("name"),
						rs.getString("tel"),
						rs.getString("address"),
						rs.getString("grade"),
				});
			}
			String[][] arr = new String[list.size()][5];
			System.out.println("데이터 불러오기 완료");
			return list.toArray(arr);

		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			close(psmt, con);
		}
	}

	// table에 데이터 추가
	public int createCustomer(String name, String tel, String address, String grade) {
		try {
			con = getConnection();
			psmt = con.prepareStatement(
					"INSERT INTO customertb "
							+ "(name, tel, address, grade) "
							+ "VALUES "
							+ "('" + name + "', '" + tel + "', '" + address + "', '" + grade + "')"  
					); 
			psmt.executeUpdate();
			psmt = con.prepareStatement("SELECT LAST_INSERT_ID();");
			ResultSet rs = psmt.executeQuery();
			rs.next();
			System.out.println("고객정보가 추가되었습니다.");
			return rs.getInt(1);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			close(psmt, con);
		}
	}

	// database table 생성
	public void createTable() {
		try {
			con = getConnection();
			psmt = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS "
							+ "customertb( id INT NOT NULL AUTO_INCREMENT,"
							+ "name VARCHAR(255),"
							+ "tel VARCHAR(255),"
							+ "address VARCHAR(255),"
							+ "grade VARCHAR(255),"
							+ "PRIMARY KEY(id) )"
					);
			psmt.execute();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close(psmt, con);
		}
	}

	// database 연결
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/sampleDB";
			String user = "root";
			String pwd = "1234567890";

			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB 연결 완료");
			return con;

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
			return null;
		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
			return null;
		}
	}
	
	
	// db 연결 종료 - 종료하지 않으면 제어권이 돌아가지 않아 앱이 먹통이 될 수 있다.
	public static void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
		
		try {
			if(psmt != null )
				psmt.close();
			if(conn != null)
				conn.close();
			if(rs != null)
				rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs, PreparedStatement psmt) {
		
		try {
			if(rs != null)
				rs.close();
			if(psmt != null )
				psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement psmt, Connection con) {
		
		try {
			if(psmt != null )
				psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
