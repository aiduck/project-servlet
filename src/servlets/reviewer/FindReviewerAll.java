package servlets.reviewer;

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
import com.yhcj.Dao.Reviewer;
import com.yhcj.Dao.impl.ReviewerImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.ReviewerObject;

/**
 * Servlet implementation class FindReviewerAll
 */
@WebServlet("/FindReviewerAll")
public class FindReviewerAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindReviewerAll() {
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
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		// 专家对象
		Reviewer findAllReviewerDao = new ReviewerImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		List<ReviewerObject> reviewerList = new ArrayList<ReviewerObject>();
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
			reviewerList = findAllReviewerDao.findAllReviewer(pageNum,pageSize);
			if(reviewerList != null) {
				result = new  ResponseObject(200,"成功返回专家信息!",reviewerList);
			}
			else {
				result = new ResponseObject(500,"返回专家信息错误!");
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
