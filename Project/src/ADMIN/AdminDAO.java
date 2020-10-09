package ADMIN;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminDAO {
	Scanner sc = new Scanner(System.in);
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	String c_name;
	boolean isOk = true;

	public void getConn() {
		try {// �ڵ������ ����Ǿ��µ� ����������
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";

			// 2. connection ����
			conn = DriverManager.getConnection(url, db_id, db_pw);
			if (conn != null) {
				System.out.println();
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

	public void list() {
		getConn();
		System.out.println("=================================���=================================");
		String sql = "select rpad(c_num,15,' ')as c_num, rpad(c_name,15,' ') as c_name, rpad(c_brand,15,' ') as c_brand, c_ivnt from car";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				int num = rs.getInt(1);
				String c_name = rs.getString(2);
				String c_brand = rs.getString(3);
				int ivnt = rs.getInt(4);
				System.out.println("  ��Ϲ�ȣ : " + num + "\t�̸� : " + c_name + "\t�귣�� : " + c_brand + "\t��� : " + ivnt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println("========================================================================");

	}

	public void c_invn() {
		getConn();

		try {
			do {
				System.out.print("�߰��� ������ ��Ϲ�ȣ�� �Է��ϼ��� >>>>>>>>>>>");
				int num = sc.nextInt();
				System.out.print("�߰��� ä���� �Է��ϼ��� >>>>>>>>>>>>>>>>>");
				int ivnt = sc.nextInt();

				String sql1 = "select count(c_num) from car where c_num=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setInt(1, num);
				rs = psmt.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					System.out.println("�������� �ʴ� �����Դϴ�. ���� ��Ϲ�ȣ�� �ٽ� �Է����ּ���");
					System.out.println();
				} else {

					String sql2 = "update car set c_ivnt=c_ivnt+? where c_num=? ";
					psmt = conn.prepareStatement(sql2);
					psmt.setInt(1, ivnt);
					psmt.setInt(2, num);

					int cnt = psmt.executeUpdate();
					if (cnt > 0) {
						System.out.println();
						System.out.println("��� ���������� �߰��Ǿ����ϴ�.");
					} else {
						System.out.println();
					}

					if (cnt == 0) {
						System.out.println("��� �߸� �Է��ϼ̽��ϴ�.");
						System.out.println();
					} else {
						System.out.println();
						System.out.println("��� �� �߰��Ͻðڽ��ϱ�?");
						System.out.println("[1] ��    [2] �ƴϿ�");
						System.out.print(">>>>>>>>>>>>>>>>>>>>>>>");
						int i = sc.nextInt();
						if (i == 2) {
							isOk = false;
						}
					}
				}

			} while (isOk);
		} catch (Exception e) {
			System.out.println("����߰� ����");

			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void c_in() {

		getConn();
		int c_invnt = 0;
		String c_name = null;
		String c_brand = null;
		try {

			do {
				System.out.print("�߰��� ������ �Է��ϼ��� >>>>>>>>>>>>>> ");
				c_name = sc.next();
				System.out.print("�߰��� ������ �귣�带 �Է��ϼ��� >>>>>>>> ");
				c_brand = sc.next();
				System.out.print("�߰��� ������ ����� �Է��ϼ��� >>>>>>>> ");
				c_invnt = sc.nextInt();

				String sql1 = "select count(c_name) from car where c_name=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, c_name);
				rs = psmt.executeQuery();
				rs.next();
				if (rs.getInt(1) > 0) {
					System.out.println(">>>>>> �̹� ��� �ִ� �����Դϴ�.");
					System.out.println(">>>>>> ���ο� ������ �Է����ּ���");
					System.out.println();
				} else {

					String sql = "insert into car values(c_num_seq.nextval, ?,?,?)";
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					psmt.setString(2, c_brand);
					psmt.setInt(3, c_invnt);

					int cnt = psmt.executeUpdate();
					if (cnt > 0) {
						System.out.println("���������� �ԷµǼ̽��ϴ�.");
					} else {
						System.out.println("");
					}

					if (c_invnt == 0) {
						System.out.println("�߸��Է��ϼ̽��ϴ�.");
						System.out.println();
					} else {
						System.out.println("�� �߰��Ͻðڽ��ϱ�?");
						System.out.println("[1] �� \t [2] �ƴϿ�");
						System.out.print(">>>>>>>>>>>>>>>>>>>>>>>");
						int i = sc.nextInt();
						if (i == 2) {
							isOk = false;
						}
					}

				}
			} while (isOk);

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

	public void order() {
		// �� ���, �����, �� �̸�, �귣��, �ּ�
	}

	public void a_menu() {
		System.out.println();
		System.out.println("====================================MENU====================================");
		System.out.println(" [1] ���� ��� \t [2] ����߰� \t [3] ����ǰ��� \t [4] �ֹ�����\t [5] �ֹ����");
		System.out.println();
		System.out.print(">>>>>>>>>>>>>>>>>  ");
	}

	public void join() {
		getConn();
		try {
			System.out.println();
			System.out.println("=============ȸ������=============");
			String sql = "insert into admin values (?,?,?)";
			do {
				System.out.print("���̵� �Է��ϼ��� : ");
				String a_id = sc.next();

				String sql1 = "select a_id from admin where a_id=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, a_id);
				rs = psmt.executeQuery();
				if (rs.next() == false) {
					System.out.print("�н����带 �Է��ϼ��� : ");
					String a_pw = sc.next();
					System.out.print("�̸��� �Է��ϼ��� : ");
					String a_name = sc.next();
					
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, a_id);
					psmt.setString(2, a_pw);
					psmt.setString(3, a_name);
					int cnt = psmt.executeUpdate();

					if (cnt > 0) {
						System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�.");
						System.out.println();
						isOk = false;
					} else {
						System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�.");
						System.out.println();
					}

				}else {
					System.out.println("�̹� �����ϴ� ���̵� �ֽ��ϴ�.");
					System.out.println();
				}

			} while (isOk);

		} catch (Exception e) {
			System.out.println("ȸ������ ����");
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void login() {
		getConn();
		try {
			do {
				System.out.print("���̵� �Է��ϼ��� >>>>> ");
				String a_id = sc.next();
				System.out.print("�н����带 �Է��ϼ��� >>>>> ");
				String a_pw = sc.next();

				String sql = "select a_id, a_pw from admin where a_id=? and a_pw=?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, a_id);
				psmt.setString(2, a_pw);
				rs = psmt.executeQuery();
				while (rs.next()) {
					rs.getString(1);
					rs.getString(2);

					if (rs.getString(1).equals(a_id)) {
						if (!rs.getString(2).equals(a_pw)) {
							System.out.println("��й�ȣ�� �߸��Է��ϼ̽��ϴ�.");
							System.out.println();
						}
					} else {
						System.out.println("���̵� �߸��Է��ϼ̽��ϴ�.");
						System.out.println();
					}
					isOk = false;

				}
				String sql1 = "select a_name from admin where a_id=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, a_id);
				rs = psmt.executeQuery();
				while (rs.next()) {
					System.out.println();
					System.out.println(rs.getString("a_name") + "����� ȯ���մϴ�!");

				}

			} while (isOk);
		}

		catch (Exception e) {
			System.out.println("�α��� ����");
			e.printStackTrace();
		} finally {
			close();
		}
	}
}
