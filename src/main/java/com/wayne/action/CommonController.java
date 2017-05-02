package com.wayne.action;

import com.wayne.common.VerifyCodeUtils;
import com.wayne.common.WebUtils;
import com.wayne.model.BbsUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wayne.action.BaseController.CODE_NAME;

/**
 * 验证码
 * @Author TAO
 * @Date 2017/4/4 20:51
 */
@Controller
@RequestMapping("/bbs/common")
public class CommonController {

    static String filePath = null;
    static {
        filePath = System.getProperty("user.dir");
        File file = new File("upload",filePath);
        file.mkdirs();
    }

    @RequestMapping("/authImage")
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute(CODE_NAME);
        session.setAttribute(CODE_NAME, verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        String rootPath = filePath;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        try {
            BbsUser user = WebUtils.currentUser(request, response);
            if (null == user) {
                map.put("error", 1);
                map.put("msg", "上传出错，请先登录！");
                return map;
            }
            //从剪切板粘贴上传没有后缀名，通过此方法可以获取后缀名
            Matcher matcher = Pattern.compile("^image/(.+)$",Pattern.CASE_INSENSITIVE).matcher(file.getContentType());
            if(matcher.find()){
                String newName = UUID.randomUUID().toString()+System.currentTimeMillis()+"."+matcher.group(1);
                String filePaths = rootPath + "/upload/";
                File fileout = new File(filePaths);
                if(!fileout.exists()){
                    fileout.mkdirs();
                }
                FileCopyUtils.copy(file.getBytes(), new File(filePaths+ newName));
                map.put("file_path", request.getContextPath()+"/bbs/common/showPic/" + newName);
                map.put("msg","图片上传成功！");
                map.put("success", true);
                return map;
            }else{
                map.put("success","不支持的上传文件格式！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "图片上传出错！");
        }
        return map;
    }

    /**
     * 用于富文本编辑器找到图片路径
     * @param path
     * @param ext
     * @param request
     * @param response
     */
    @RequestMapping("/showPic/{path}.{ext}")
    public void showPic(@PathVariable String path, @PathVariable String ext, HttpServletRequest request, HttpServletResponse response){
        String rootPath = filePath;

        try {
            String filePath = rootPath + "/upload/" + path+"."+ext;
            FileInputStream fins = new FileInputStream(filePath);
            response.setContentType("image/jpeg");
            FileCopyUtils.copy(fins, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ;
    }

}
