package com.ikun.cm.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: HeKun
 * @date: 2024/11/11 19:58
 * @description:
 */
@SpringBootTest
public class MyStrTest {

    @Test
    void handleStr1(){
        String jsonStr = "{"
                + "\"date\": \"2024-11-08 10:53:37\","
                + "\"summary\": \"11月6日下午，《外圆内方沈阳城》作家进校园阅读分享会在图书馆一楼刑警书店举行。\","
                + "\"owner\": \"1403508930\","
                + "\"delivertype\": \"0\","
                + "\"contentid\": \"33382\","
                + "\"title\": \"图书馆举办《外圆内方沈阳城》作家进校园阅读分享会\","
                + "\"content\": \"<p class=\\\"vsbcontent_start\\\">11月6日下午，《外圆内方沈阳城》作家进校园阅读分享会在图书馆一楼刑警书店举行。本次活动邀请沈阳出版社总编辑闫志宏、《外圆内方沈阳城》作者佟悦两位嘉宾，通过将沈阳老建筑的历史、文化、审美、科学、时代等元素有机整合，为现场师生带来了一场主题为《资深沈阳人，带你City Walk》的精彩分享，带领大家沿着历史的脚步在沈阳漫步，品读建筑与人与城与时代关系的生动图谱。</p><p style=\\\"text-align: center\\\"><vsbimg src=\\\"/_vsl/4DE85BA9FB1D2CB61C18B2A3EA6E88CC/8C028D4C/5351E\\\" width=\\\"500\\\" vsbhref=\\\"vurl\\\" vurl=\\\"/_vsl/4DE85BA9FB1D2CB61C18B2A3EA6E88CC/8C028D4C/5351E\\\" vheight=\\\"\\\" vwidth=\\\"500\\\"></vsbimg></p>\","
                + "\"url\": \"//www.cipuc.edu.cn/info/2701/21465.htm\","
                + "\"sourcenewsid\": \"21465\","
                + "\"sourceowner\": \"1403508930\","
                + "\"picurl\": \"\","
                + "\"newsid\": \"21465\","
                + "\"showtimes\": \"169\","
                + "\"keyword\": \"进 方沈阳 作家 城 校园 圆 外 阅读 举办 分享 图书馆 会 \""
                + "}";

        // 将 JSON 字符串解析为 JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        // 获取 picurl 和 content 字段
        String picurl = jsonObject.getString("picurl");
        String content = jsonObject.getString("content");

        // 判断 picurl 是否为空
        if (picurl == null || picurl.isEmpty()) {
            // 正则表达式匹配第一个 vsbimg 的 src
            Pattern pattern = Pattern.compile("<vsbimg src=\\\"(.*?)\\\"");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                // 提取出第一个 src 的值
                picurl = matcher.group(1);
            }
        }

        // 打印或返回最终 picurl
        System.out.println("最终 picurl: " + picurl);
    }
}
