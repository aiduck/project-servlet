package servlets.TeacherFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//用于上传材料的删除全部
@WebServlet("/TeacherProjectMangeFileDeleteAll")
public class TeacherProjectMangeFileDeleteAll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String state = request.getParameter("state");
        String type = request.getParameter("type");

        //登录项目管理
        Teacher teacherDao = new TeacherImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        //先删除原有的上传文件
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(state)){
            List<ProjectFileObject> list = teacherDao.TeacherQueryByStateAndTypeFiles(id,state,type);
            if(list!=null){
                for(ProjectFileObject psf:list){
                    String filePath=psf.getFilePath();
                    File file = new File(this.getServletContext().getRealPath(filePath));

                    if(file.exists()){
                        file.delete();
                    }
                }
            }
            Integer msg = teacherDao.TeacherDeleteByTypeFiles(id,state,type);
            if(msg<0){
                result = new ResponseObject(500,"删除失败！");
            }else
                result = new ResponseObject(200,"删除成功！");
        }else {
            result = new ResponseObject(500,"url地址错误！");
        }

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
