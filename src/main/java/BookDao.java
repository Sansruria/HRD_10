import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class BookDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	HttpServletRequest request;

	public BookDao(HttpServletRequest request) {
		System.out.println("BookDao call");
		this.request = request; // 뷰페이지 입력한 데이터
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@//106.243.194.229:3200/xe", "shoh", "1234");
			System.out.println("con=" + con);
			System.out.println("db 연결 성공");
		} catch (Exception e) {
			System.out.println("db 연결 실패");
			e.printStackTrace();
		}

	}// 생성자 End

	// close 생략 가능
//	public void close() throws SQLException {
//		if (rs != null)
//			rs.close();
//		if (pstmt != null)
//			pstmt.close();
//		if (con != null)
//			con.close();
//	}

	public String bookSalesRegFrm_getNewSaleNo() {
		System.out.println("getNewSaleNo call");
		// nextval쓰면 view 페이지를 띄울때마다 시퀀스가 증가하는 문제점
		// currval쓰면 새로운 session(접속)때마다 에러
		String sql = "SELECT MAX(SALENO)+1 NEWSALENO FROM tbl_salelist_202002";
		// Book book=null;
		try {
			pstmt = con.prepareStatement(sql);// 파싱 1번
			rs = pstmt.executeQuery(); // sql문 실행 //0반환
			if (rs.next()) {
				// book=new Book();
				// book.setSaleNo(rs.getInt("NEWSALENO")); //새 매출번호
				int saleNo = rs.getInt("NEWSALENO");
				// 저장된 매출 데이터가 없을 때 0을 반환(있다면 생략가능)
//				if (saleNo == 0) {
//					saleNo = 1000001;
//				}
				System.out.println("saleNo=" + saleNo);
				request.setAttribute("newSaleNo", saleNo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "bookSalesRegFrm.jsp"; // 도서매출 등록 페이지
	}

	public String bookSalesInsert() { // 도서매출등록
		String sql = "INSERT INTO TBL_SALELIST_202002 VALUES(?,?,?,?,?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql); // 파싱 1번
			// System.out.println("saleno=" + request.getParameter("saleNo"));
			pstmt.setInt(1, Integer.parseInt(request.getParameter("saleNo")));
			pstmt.setString(2, request.getParameter("saleDate"));
			// System.out.println("saleDate=" + request.getParameter("saleDate"));
			pstmt.setString(3, request.getParameter("bCode"));
			// System.out.println("bCode=" + request.getParameter("bCode"));
			pstmt.setInt(4, Integer.parseInt(request.getParameter("aMount")));
			// System.out.println("aMount=" +
			// Integer.parseInt(request.getParameter("aMount")));
			pstmt.setString(5, request.getParameter("approved")); // Y or N
			result = pstmt.executeUpdate(); // sql 실행 (insert, delete, update)
			// 성공메세지를 view페이지에서 미리 출력했다면 if문 생락가능
//			if (result != 0) { // 작업성공
//				request.setAttribute("msg", "매출전표가 정상적으로 등록 되었습니다!");
//			} else {
//				System.out.println("도서등록 실패");
//			}
		} catch (SQLException e) {
			System.out.println("도서추가 예외 발생");
			e.printStackTrace();
		}
		// return "bookSalesRegFrm"; // url, 도서매출등록 뷰로 이동
		return "totalSalesSelect"; // url , 통합매출조회 뷰 이동
	}

	public String totalSalesSelect() { // 통합매출조회
		// """: 자바텍스트 블록, JDK15이상
		String sql = """
				SELECT S.SALENO,C.COMCODE,C.COMNAME,TO_CHAR(S.SALEDATE,'YYYY-MM-DD') AS SALEDATE,
				S.BCODE,B.BNAME,S.AMOUNT,TO_CHAR(S.AMOUNT*B.COST,'L999,999,999') AS PRICE, S.APPROVED
				FROM TBL_SALELIST_202002 S, TBL_BOOK_202002 B, TBL_COMPANY_202002 C
				WHERE S.BCODE=B.BCODE AND B.COMCODE=C.COMCODE""";
		try {
			pstmt = con.prepareStatement(sql);// 파싱 1번
			rs = pstmt.executeQuery();// select실행
			ArrayList<Book> bsList = new ArrayList<Book>();
			while (rs.next()) {
				Book b = new Book();//HashMap or DTO(Entity)  
				b.setSaleNo(rs.getInt("SALENO")); // rs.getInt(1)
				b.setComCode(rs.getString("COMCODE")); // rs.getString(2)
				b.setComName(rs.getString("COMNAME"));// rs.getString(3)
				b.setSaleDate(rs.getString("SALEDATE"));// rs.getString(4)
				b.setbCode(rs.getString("BCODE"));// rs.getString(5)
				b.setbName(rs.getString("BNAME"));// rs.getString(6)
				b.setAmount(rs.getInt("AMOUNT")); // rs.getInt(7)
				b.setPrice(rs.getString("PRICE")); // 개별*수량 ,rs.getString(8)
				if (rs.getString("APPROVED").equals("Y")) { // rs.getString(9)
					b.setApproved("승인"); // Y
				} else {
					b.setApproved("미승인"); // N
				}
				bsList.add(b);
			}
			request.setAttribute("bsList", bsList);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("통합매출조회 예외발생");
		}
		return "totalSalesSelect.jsp";
	}

	public String byComSelect() { // 출판사별 매출조회

		String sql = """
				SELECT C.COMCODE,C.COMNAME,TO_CHAR(SUM(S.AMOUNT*B.COST),'L999,999,999') AS PRICE
				FROM TBL_SALELIST_202002 S,TBL_Book_202002 B,TBL_COMPANY_202002 C
				WHERE S.BCODE=B.BCODE AND B.COMCODE=C.COMCODE
				GROUP BY C.COMCODE,C.COMNAME
				ORDER BY PRICE DESC""";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<Book> comList = new ArrayList<Book>();
			while (rs.next()) {
				Book b = new Book();
				b.setComCode(rs.getString("COMCODE")); // rs.getString(1)
				b.setComName(rs.getString("COMNAME")); // rs.getString(2)
				b.setPrice(rs.getString("PRICE")); // rs.getString(3)
				comList.add(b);
			}
			request.setAttribute("comList", comList);
		} catch (SQLException e) {
			System.out.println("출판사별 매출조회 예외");
			e.printStackTrace();
		}

		return "byComSelect.jsp";
	}

	public String byBookSelect() { // 도서별 매출조회

		String sql = """
				SELECT B.BCODE,B.BNAME,TO_CHAR(SUM(S.AMOUNT*B.COST),'L999,999,999') AS PRICE
				FROM TBL_SALELIST_202002 S,TBL_Book_202002 B,TBL_COMPANY_202002 C
				WHERE S.BCODE=B.BCODE AND B.COMCODE=C.COMCODE
				GROUP BY B.BCODE,B.BNAME
				ORDER BY PRICE DESC""";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<Book> bookList = new ArrayList<Book>();
			while (rs.next()) {
				Book b = new Book();
				b.setbCode(rs.getString("BCODE")); // rs.getString(1)
				b.setbName(rs.getString("BNAME")); // rs.getString(2)
				b.setPrice(rs.getString("PRICE")); // rs.getString(3)
				bookList.add(b);
			}

			request.setAttribute("bookList", bookList);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return "byBookSelect.jsp";
	}

	public String comUpdateFrm() {
		String comCode = request.getParameter("comCode");
		String sql = "SELECT * FROM TBL_COMPANY_202002 WHERE COMCODE=?";
		try {
			pstmt = con.prepareStatement(sql); // 파싱1번
			pstmt.setNString(1, comCode);
			rs = pstmt.executeQuery(); // sql실행
			Book book = null;
			if (rs.next()) {
				book = new Book();
				book.setComCode(rs.getString("COMCODE")); // rs.getString(1)
				book.setComName(rs.getString("COMNAME")); // rs.getString(2)
				request.setAttribute("com", book);
			}
		} catch (SQLException e) {
			System.out.println("comUpdateFrm 예외");
			e.printStackTrace();
		}
		return "comUpdateFrm.jsp";
	}

	public String comUpdate() {
		String comCode = request.getParameter("comCode");
		String comName = request.getParameter("comName");
		System.out.println("1=" + comCode);
		System.out.println("2=" + comName);
		String sql = "UPDATE TBL_COMPANY_202002 SET COMNAME=? WHERE COMCODE=?";
		String path = null;
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql); // 파싱1번
			pstmt.setString(1, comName);
			pstmt.setString(2, comCode);
			result = pstmt.executeUpdate(); // sql실행
			if (result != 0) {
				System.out.println("수정 성공");
				path = "byComSelect"; // url
			} else {
				System.out.println("수정 실패");
				path = "comUpdateFrm.jsp";
			}
		} catch (SQLException e) {
			System.out.println("comUpdate 예외 발생");
			e.printStackTrace();
		}
		return path;
	}

}
