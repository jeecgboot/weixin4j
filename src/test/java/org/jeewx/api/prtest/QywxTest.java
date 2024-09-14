package org.jeewx.api.prtest;


import com.alibaba.fastjson.JSONObject;
import com.jeecg.qywx.api.base.JwAccessTokenAPI;
import com.jeecg.qywx.api.core.common.AccessToken;
import com.jeecg.qywx.api.core.util.WXUpload;
import com.jeecg.qywx.api.department.JwDepartmentAPI;
import com.jeecg.qywx.api.department.vo.Department;
import com.jeecg.qywx.api.message.JwMessageAPI;
import com.jeecg.qywx.api.message.vo.Text;
import com.jeecg.qywx.api.message.vo.TextEntity;
import com.jeecg.qywx.api.user.JwUserAPI;
import com.jeecg.qywx.api.user.vo.User;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

/**
* @Description: 企业微信第三方测试类
*
* @author: wangshuai
* @date: 2024/9/11 上午10:45
*/
public class QywxTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(QywxTest.class);

    /**
     * 获取部门下的用户
     */
    @Test
    public void getUserList() {
        String accessToken = this.getAccessToken();
        log.info("accessToken====="+accessToken);
        List<Department> allDepartment = JwDepartmentAPI.getAllDepartment(accessToken);
        //获取部门信息
        if(!CollectionUtils.isEmpty(allDepartment)){
            log.info("allDepartment====="+ JSONObject.toJSONString(allDepartment));
            log.info("~~~~~~~~~~~~其中一个部门id~~~~~~~~~~~~~~"+ allDepartment.get(0).getId());
            log.info("~~~~~~~~~~~~其中一个部门名称~~~~~~~~~~~~~~"+ allDepartment.get(0).getName());
            log.info("获取部门下的用户");
            List<User> usersByDepartid = JwUserAPI.getUsersByDepartid(allDepartment.get(0).getId(), "1", null, accessToken);
            if(!CollectionUtils.isEmpty(usersByDepartid)){
                log.info("~~~~~~~~~~~用户列表~~~~~~~~~~"+ JSONObject.toJSONString(usersByDepartid));
            }
        }
    }

    /**
     * 发送模板消息 到企业微信
     */
    @Test
    public void sendMessageToQywx() {
        String accessToken = this.getAccessToken();
        Text text = new Text();
        text.setMsgtype("text");
        text.setTouser("@all");
        TextEntity entity = new TextEntity();
        entity.setContent("测试企业微信消息");
        text.setText(entity);
        text.setAgentid(Integer.parseInt("1000009"));
        JSONObject jsonObject = JwMessageAPI.sendTextMessage(text, accessToken);
        log.info("=========返回系统消息====="+jsonObject);
    }

    /**
     * 复制文件到指定目录
     */
    @Test
    public void writeFile() {
        try (FileInputStream fileInputStream = new FileInputStream("D://images/gdbt.jpg");
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            WXUpload.writeFile(byteArray,"D://images","2.jpg",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
    public String getAccessToken() {
        AccessToken accessToken = JwAccessTokenAPI.getAccessToken("wwda7ff292b81e8d1b", "??");
        return accessToken.getAccesstoken();
    }
}
