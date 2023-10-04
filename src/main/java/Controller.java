import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//주의) ojdbc(오라클), taglib(톰캣) 라이브러리는 webapp/WEB-INF/lib에 반드시 추가할 것.
@WebServlet({"/bookSalesRegFrm","/bookSalesInsert","/totalSalesSelect","/byComSelect","/byBookSelect", "/comUpdateFrm","/comUpdate"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");  //한글깨짐 방지
		String url=request.getServletPath();
		System.out.println("url="+url);
		
		BookDao bDao= new BookDao(request);  //서비스 클래스 없이 DAO
		String path=null;
		switch(url) {
		case "/bookSalesRegFrm":  //도서매출등록창을 시퀀스 또는 최대값을 띄운다.
			path=bDao.bookSalesRegFrm_getNewSaleNo();
			break;
		case "/bookSalesInsert":  //도서매출등록
			path=bDao.bookSalesInsert();
			break;
		case "/totalSalesSelect": //통합매출조회
			path=bDao.totalSalesSelect();
			break;
		case "/byComSelect": //출판사별 매출조회
			path=bDao.byComSelect();
			break;
		case "/byBookSelect": //도서별 매출조회
			path=bDao.byBookSelect();
			break;	
		case "/comUpdateFrm":  //도서매출등록창을 시퀀스와 띄운다.
			path=bDao.comUpdateFrm();
			break;
		case "/comUpdate":  //도서매출등록창을 시퀀스와 띄운다.
			path=bDao.comUpdate();
			break;
		default :
			System.out.println("잘못된 url");
		}
		//close 생략
//		try {
//			bDao.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(path!=null) {
			System.out.println("path="+path); //path:  url, jsp  
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
