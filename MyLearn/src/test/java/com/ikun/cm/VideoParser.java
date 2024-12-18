package com.ikun.cm;

import com.ikun.cm.util.UUIDUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoParser {
    public static void main(String[] args) {
        String html = "<p>&nbsp;&nbsp;&nbsp;&nbsp;测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候</p>\n" +
                "<embed autoplay=\"false\" draggable=\"false\" src=\"/system/resource/swf/flowplayer.commercial.swf\" duration=\"18760\" width=\"500\" video-pic=\"/storage/979D23AF5F1301F70C7E28AD62B5B7B7/thumbnails\" video-src=\"/storage/979D23AF5F1301F70C7E28AD62B5B7B7/mp4\" source-url=\"/storage/979D23AF5F1301F70C7E28AD62B5B7B7/mp4\" alias-label=\"video\" name=\"132都3adad‘.mp4\" height=\"auto\" vsburl=\"https://wlzx.hebtu.edu.cn/resources/43/202403/F567993B164C49D7A6C10A19146555AD.mp4\" ue_src=\"https://wlzx.hebtu.edu.cn/resources/43/202403/F567993B164C49D7A6C10A19146555AD.mp4\" class=\"edui-upload-video\" vsbmp4video=\"true\" controls=\"IMAGEWINDOW,ControlPanel,StatusBar\" console=\"Clip1\" vsbautoplay=\"true\">\n" +
                "<p>&nbsp;&nbsp;&nbsp;&nbsp;的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候测试多个视频的时候</p>\n" +
                "<embed autoplay=\"false\" draggable=\"false\" src=\"/system/resource/swf/flowplayer.commercial.swf\" duration=\"27600\" width=\"500\" video-pic=\"/storage/0FA4B69230ACE71F97C2A494CABC15D9/thumbnails\" video-src=\"/storage/0FA4B69230ACE71F97C2A494CABC15D9/mp4\" source-url=\"/storage/0FA4B69230ACE71F97C2A494CABC15D9/mp4\" alias-label=\"video\" name=\"13对呵2dadg.mp4\" height=\"auto\" vsburl=\"https://wlzx.hebtu.edu.cn/resources/43/202403/7265388ADC654D50BFD75F9CAAB9F01E.mp4\" ue_src=\"https://wlzx.hebtu.edu.cn/resources/43/202403/7265388ADC654D50BFD75F9CAAB9F01E.mp4\" class=\"edui-upload-video\" vsbmp4video=\"true\" controls=\"IMAGEWINDOW,ControlPanel,StatusBar\" console=\"Clip1\" vsbautoplay=\"true\">";

        Document doc = Jsoup.parse(html);

        Elements embeds = doc.select("embed");
        for (Element embed : embeds) {
            String videoSrc = embed.attr("vsburl");
            String videoName = embed.attr("name");
            String uuid = UUIDUtil.getUUid(12, true);

            // 创建新的 div 标签
            Element div = new Element("div");

            // 设置 div 标签的属性
            div.attr("_uuid", uuid);
            div.attr("id", uuid);
            div.attr("class", "edui-faked-video");
            div.attr("style", "display:block;width:800px;height:450px;margin:0 auto;");
            div.attr("_src", videoSrc);
            div.attr("width", "800");
            div.attr("height", "450");

            // 创建内部的空 div 标签
            Element innerDiv = new Element("div");
            innerDiv.attr("id", uuid);

            // 创建 script 标签
            Element script = new Element("script");
            script.appendText("new DPlayer({ container: document.getElementById(\"" + uuid + "\"), video: { url: \"" + videoSrc + "\", type: \"auto\" } });");

            // 将内部的空 div 标签和 script 标签添加到 div 标签内
            div.appendChild(innerDiv);
            // 将 script 标签添加到 div 标签内
            div.appendChild(script);

            // 替换原来的 embed 标签
            embed.replaceWith(div);
        }

        System.out.println(doc.toString());
    }


    String handleEmbed(String html) {
        Document doc = Jsoup.parse(html);

        Elements embeds = doc.select("embed");
        for (Element embed : embeds) {
            String videoSrc = embed.attr("vsburl");
            String videoName = embed.attr("name");
            String uuid = UUIDUtil.getUUid(12, true);

            // 创建新的 div 标签
            Element div = new Element("div");

            // 设置 div 标签的属性
            div.attr("_uuid", uuid);
            div.attr("id", uuid);
            div.attr("class", "edui-faked-video");
            div.attr("style", "display:block;width:800px;height:450px;margin:0 auto;");
            div.attr("_src", videoSrc);
            div.attr("width", "800");
            div.attr("height", "450");

            // 创建内部的空 div 标签
            Element innerDiv = new Element("div");
            innerDiv.attr("id", uuid);

            // 创建 script 标签
            Element script = new Element("script");
            script.appendText("new DPlayer({ container: document.getElementById(\"" + uuid + "\"), video: { url: \"" + videoSrc + "\", type: \"auto\" } });");

            // 将内部的空 div 标签和 script 标签添加到 div 标签内
            div.appendChild(innerDiv);
            // 将 script 标签添加到 div 标签内
            div.appendChild(script);
        }
        return doc.toString();
    }
}
