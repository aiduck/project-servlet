package servlets.ProjectFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@WebServlet("/ProjectMangeFileDownload")
public class ProjectMangeFileDownload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
//        String state = request.getParameter("state");


        //登录项目管理
        Project ProjectDao = new ProjectImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if (StringUtils.isNotBlank(id)){
            response.reset();
            response.setContentType("application/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename= download.zip");

            //看你想下哪些了
            //List<ProjectFileObject> list = ProjectDao.ProjectSignUpQueryByStateFiles(id,state);
            List<ProjectFileObject> list = ProjectDao.ProjectSignUpQueryByTypeFiles(id,type);

            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zos.setMethod(ZipOutputStream.DEFLATED);//设置压缩方法
            DataOutputStream os=null;
            //循环将文件写入压缩流
            for (ProjectFileObject pfo:list){
                String filePath = pfo.getFilePath();
                String fileName = pfo.getFileName();
                String fileFullPath=this.getServletContext().getRealPath(filePath) ;
                File file = new File(fileFullPath);
                try{
                    zos.putNextEntry(new ZipEntry(fileName));
                    os = new DataOutputStream(zos);
                    InputStream is = new FileInputStream(file);
                    byte[] b =new byte[100];
                    int length=0;
                    while((length = is.read(b))!= -1){
                        os.write(b, 0, length);
                    }
                    is.close();
                    zos.closeEntry();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //关闭流
            try {
                os.flush();
                os.close();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            result = new ResponseObject(500,"获取参数失败！");
            out.println(new GsonBuilder().create().toJson(result));
            out.flush();
            out.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request,response);
    }
}
