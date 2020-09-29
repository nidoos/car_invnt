package CAR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CarDAO {
	Scanner sc = new Scanner(System.in);
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	String c_name;

	public void getConn() {
		try {// �ڵ������ ����Ǿ��µ� ����������
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";

			// 2. connection ����
			conn = DriverManager.getConnection(url, db_id, db_pw);
			if (conn != null) {
				System.out.println("���� ����");
			} else {
				System.out.println("���� ����");
			}
		}
		// ������ �� ��� ��� �����鿡 ���ؼ� catch
		catch (Exception e) {
			System.out.println("getConn ����!");
		}

	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void c_insert() {
		getConn();
		try {
			int choice = 0;
			System.out.println("������ �߰� �Ͻðڽ��ϱ�?");
			System.out.println("�߰�[1] ����[2]");
			choice = sc.nextInt();
			if (choice == 1) {
				do {

					System.out.print("���� �̸� : ");
					c_name = sc.next();
					System.out.print("��ó⵵ : ");
					int year = sc.nextInt();
					System.out.print("���� : ");
					String c_type = sc.next();
					System.out.print("���� : ");
					float price = sc.nextFloat();
					System.out.print("brand : ");
					String brand = sc.next();

					String sql = "insert into car values(r_num_seq.nextval,?,?,?,?,?)";

					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					psmt.setInt(2, year);
					psmt.setString(3, c_type);
					psmt.setFloat(4, price);
					psmt.setString(5, brand);

					int cnt = psmt.executeUpdate();

					if (cnt > 0)
						System.out.println("�߰��Ǿ����ϴ�.");
					else
						System.out.println("�߰��� ������ ������ϴ�.");
					System.out.println("��� �Ͻðڽ��ϱ�?");
					System.out.println("�߰�[1] ����[2]");
					choice = sc.nextInt();
				} while (choice == 1);
				System.out.println("����Ǿ����ϴ�.");
			} else
				System.out.println("����Ǿ����ϴ�.");
		} catch (Exception e) {
			System.out.println("insert ����");
			e.printStackTrace();
		} finally {
			close();
		}
		invnt();
	}

	public void invnt() {

		boolean isOk = false;
		String shipment = null;
		int invnt = 0;
		getConn();

		try {

			do {

				System.out.print("������ : ");
				shipment = sc.next();

				System.out.print("��� : ");
				invnt = sc.nextInt();

				if (invnt == 0) {
					System.out.println("�߸��Է��ϼ̽��ϴ�.");
					System.out.println();
				} else {
					isOk = true;
					
				}
			} while (isOk);

			String sql = "insert into grg values ((select r_num from car where c_name = ?),?,?)";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, c_name);
			psmt.setString(2, shipment);
			psmt.setInt(3, invnt);

			int cnt = psmt.executeUpdate();

			if (cnt > 0) {
				System.out.println("��� �߰� ����");
				
			} else {
				System.out.println("��� �߰� ����");
			}
		}

		catch (Exception e) {
			System.out.println("insert ����");
			e.printStackTrace();
		}

		// 4. ����ݱ�
		// conn, psmt ����
		finally {
			close();
		}

	}

	public void c_ovr() {
		getConn();
		try {
			int choice = 0;
			System.out.println("������ �߰� �Ͻðڽ��ϱ�?");
			System.out.println("�߰�[1] ����[2]");
			choice = sc.nextInt();
			if (choice == 1) {
				do {

					System.out.print("���� �̸� : ");
					c_name = sc.next();

					String sql = "select count(c_name) from car where c_name=?";

					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					
					rs=psmt.executeQuery();
					
					if(rs.getInt(1)>0) {
					System.out.println("�ߺ������� �Դϴ�.");
					}	
				} while (choice == 1);
				System.out.println("����Ǿ����ϴ�.");
			} else
				System.out.println("����Ǿ����ϴ�.");
		} catch (Exception e) {
			System.out.println("insert ����");
			e.printStackTrace();
		} finally {
			close();
		}
	}





}
