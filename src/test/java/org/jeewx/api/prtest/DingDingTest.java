package org.jeewx.api.prtest;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.dingtalk.api.base.JdtBaseAPI;
import com.jeecg.dingtalk.api.core.response.Response;
import com.jeecg.dingtalk.api.core.vo.AccessToken;
import com.jeecg.dingtalk.api.core.vo.PageResult;
import com.jeecg.dingtalk.api.department.JdtDepartmentAPI;
import com.jeecg.dingtalk.api.department.vo.Department;
import com.jeecg.dingtalk.api.message.JdtMessageAPI;
import com.jeecg.dingtalk.api.message.vo.Message;
import com.jeecg.dingtalk.api.message.vo.TextMessage;
import com.jeecg.dingtalk.api.user.JdtUserAPI;
import com.jeecg.dingtalk.api.user.body.GetUserListBody;
import com.jeecg.dingtalk.api.user.vo.User;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
* @Description: 钉钉第三方测试类
*
* @author: wangshuai
* @date: 2024/9/11 上午10:45
*/
public class DingDingTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DingDingTest.class);

    /**
     * 获取部门下的用户
     */
    @Test
    public void getUserList() {
        String accessToken = this.getAccessToken();
        log.info("accessToken====="+accessToken);
        Response<Department> department = JdtDepartmentAPI.getDepartmentById(1, accessToken);
        //获取部门信息
        if(department.isSuccess()){
            Department departmentResult = department.getResult();
            log.info("~~~~~~~~~~~~部门id~~~~~~~~~~~~~~"+ departmentResult.getDept_id());
            log.info("~~~~~~~~~~~~部门名称~~~~~~~~~~~~~~"+ departmentResult.getName());
            log.info("获取部门下的用户");
            GetUserListBody getUserListBody = new GetUserListBody(departmentResult.getDept_id(), 0, 100);
            Response<PageResult<User>> response = JdtUserAPI.getUserListByDeptId(getUserListBody, accessToken);
            if(response.isSuccess()){
                PageResult<User> result = response.getResult();
                log.info("~~~~~~~~~~~用户列表~~~~~~~~~~"+ JSONObject.toJSONString(result.getList()));
            }
        }
    }

    /**
     * 发送模板消息 到钉钉
     */
    @Test
    public void sendMessageToDing() {
        String accessToken = this.getAccessToken();
        Message<TextMessage> textMessage = new Message<>("2820902354", new TextMessage("测试钉钉消息"));
        textMessage.setTo_all_user(true);
        Response<String> response = JdtMessageAPI.sendTextMessage(textMessage, accessToken);
        if(response.isSuccess()){
            log.info("钉钉消息发送状态：" + response.getResult());
        }
    }
    
    public String getAccessToken() {
        AccessToken accessToken = JdtBaseAPI.getAccessToken("dingdbgpfnv56wtdpli9", "8wJHchpNq7VTgOrkVVixJaLmaBT6akB8tispdhLO31E-qLGDBuZAg1rGWZswplSo");
        return accessToken.getAccessToken();
    }
}
