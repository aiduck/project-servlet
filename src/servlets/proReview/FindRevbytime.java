package servlets.proReview;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ResponseObject;

@WebServlet("/FindRevbytime")
public class FindRevbytime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindRevbytime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String proId = request.getParameter("proId");
		String proStatus = request.getParameter("proStatus");
		//学生对象
		ProReview findAllProDao = new ProReviewImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		ProRevObject proList= new ProRevObject();
		
		if(StringUtils.isNotBlank(proId)&&StringUtils.isNotBlank(proStatus)) {
			proList = findAllProDao.findRevbytime(proId,proStatus);
			if(proList != null) {
				result = new  ResponseObject(200,"成功返回项目评审信息!",proList);
			}
			else {
				result = new ResponseObject(500,"返回项目信息评审错误!");
			}
		}
		else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
