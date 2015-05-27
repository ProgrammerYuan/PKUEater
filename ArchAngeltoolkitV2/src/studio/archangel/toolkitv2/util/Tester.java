package studio.archangel.toolkitv2.util;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import studio.archangel.toolkitv2.util.text.DateProvider;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 测试数据提供者
 * Created by Michael on 2014/9/16.
 */
public class Tester {
    /**
     * 一段纯文本，用来从其中获得字符串
     */
    static String raw_string = "回首人类的历史，我们会发现许多古代的工艺至今难以复现，而很多技术，实际上经历了一个重复失落和再发明的过程。近几百年来的技术爆炸到底是个偶然还是必然，现在还无从知晓。再往深了质疑，连近几百年来的技术爆炸是否成立也成问题，因为随着现代性的演进，人类一边在不停地发明出新的技术，一边在不停地失去旧的技术。我们的社会从没有现在这样先进，也从没有现在这样单一。技术在未来到底是会不停地爆炸下去，还是会反过来将人类吞噬，现在是个谁也说不准的事情。因此断定技术爆炸是文明的一条支配法则，显然是过于短视了。还要提宇宙的特殊性的话，我只能说，依据小说的内容，连三体文明都是技术匀速发展的，凭什么断定这个概念的合理性呢？最后谈谈理论的取向问题。在自然科学里面，我们常说“发现”了一条原理，包含的意思，是说客观规律是不以人的意志为转移的，只等着人类的意识去接近。而在社会科学里，尤其是八十年代以来，我们常说某人“发明”了一条原理，因为我们承认，社会科学的理论（或者更前卫一点说，我们认为人类所有的理论）都是“发明”出来的。是带着特殊用意的建构。《三体》中的宇宙社会学也是这样，用意无非是要营造出一种残酷的宇宙丛林法则来，好支撑小说情节的展开。然而即使我承认这只是小说的笔法而已，我还是要挑剔一番，因为我实在不忍心看到这样一部充满了坚实的自然科学细节的幻想小说，在社会科学方面出现这样不成熟的设想。这与天马行空的前瞻性幻想不同，大刘的“宇宙社会学”再现出来的，其实只是社会学早在四五十年前就已经抛弃的一些陈腐思想，有些想法的渊源，甚至可以上溯到启蒙时代一些自然科学家和政治学家对于人类社会所做的臆测，以及从自己学科出发的轻率比附。尽管充满童趣，然而很遗憾，这些“人对人是狼”、“社会遵循数学模型”、“技术不断进步”之类的幻觉，在社会学里已经属于史前史了。从理论根源上去追溯这种社会观是一条路径。让我更感兴趣的小说中具体的思想路径。叶文洁从自己文革的经历中得出这样的社会观，可以理解。我也可以理解经历过文革的一代人这样去认知社会。但是，与他们认为“文革把整个人类社会还原到了原点”，故此可以通过文革中的人际关系去认识社会不同，我认为文革状态是人类社会的一种极端形态，就此把“社会”定义成一场“无仁义的战争”，恐怕有失偏颇。";
    /**
     * 为了避免重复计算长度，把测试字符串库的长度存起来
     */
    static int raw_string_length = raw_string.length();
    static final int default_image_width = 200;
    static final int default_image_height = 200;
    /**
     * 获得测试图片的网页地址——搜狗壁纸...
     */
    static String url = "http://www.poocg.com/works/tjwork/page/";
    //    static String url = "http://bizhi.sogou.com/newly/jingpin";
    static ArrayList<String> url_list = new ArrayList<String>();

    /**
     * 获得随机字符串
     *
     * @param length_min 最小长度
     * @param length_max 最大长度
     * @return 随机字符串，长度取值区间为[{@code length_min},{@code length_max}]
     */
    public static String getString(int length_min, int length_max) {
        StringBuilder sb = new StringBuilder();
        int length = (int) (Math.random() * (length_max + 1 - length_min) + length_min);
        for (int i = 0; i < length; i++) {
            int x = (int) (Math.random() * raw_string_length);
            sb = sb.append(raw_string.charAt(x));
        }
        return sb.toString();
    }

    /**
     * 获得随机日期
     *
     * @param format 日期格式
     * @param start  开始日期
     * @param end    结束日期
     * @return 随机日期，取值区间为[{@code length_min},{@code length_max}]
     */
    public static String getDate(String format, String start, String end) {
        long l1 = DateProvider.getDate(format, start);
        long l2 = DateProvider.getDate(format, end);
        long l = (long) (Math.min(l1, l2) + Math.random() * (Math.abs(l2 - l1) + 1));
        return DateProvider.getDate(format, l);
    }

    /**
     * 获得随机整数
     *
     * @param min
     * @param max
     * @return 随机整数，取值区间为[{@code min},{@code max}]
     */
    public static int getInt(int min, int max) {
        return (int) (min + Math.random() * (max + 1 - min));
    }

    /**
     * 获得随机浮点数
     *
     * @param min
     * @param max
     * @return 随机浮点数，取值区间为[{@code min},{@code max}]
     */
    public static float getFloat(float min, float max) {
        return (float) (min + Math.random() * (max + 1 - min));
    }

    /**
     * 获得随机布尔值
     *
     * @return 随机true或false
     */
    public static boolean getBoolean() {
        return Math.random() < 0.5f;
    }

    //
//    /**
//     * 获得随机图片地址
//     *
//     * @param min 最小值
//     * @param max 最大值
//     * @return 一堆随机图片的地址
//     */
//    public static ArrayList<String> getImages(int min, int max) {
//        ArrayList<String> urls = new ArrayList<String>();
//        int count = getInt(min, max);
//        for (int i = 0; i < count; i++) {
//            urls.add(getImage());
//        }
//        return urls;
//    }
    public static void loadImages() {
        if (url_list.isEmpty()) {
            try {
                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.GET, url + Tester.getInt(1, 5), null, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo, JSONObject data) {
                        String html = null;
                        try {
                            html = new String(responseInfo.result.toString().getBytes(), "utf-8");
//                            html = new String(responseInfo.result.toString().getBytes(), "gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Logger.out("loadImages:" + html);
//                        int start = html.indexOf("<div class=\"TheWorksList TheWorksList-innerpage\">");
//                        html = html.substring(start, html.length());
//                        html = html.substring(0, html.indexOf("<div class=\"jp_more\""));
                        Document doc = Jsoup.parse(html);
                        Elements es = doc.select("img[src$=.jpg]");
                        for (Element e : es) {
                            url_list.add(e.attr("src"));
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg, JSONObject data) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        String path = "";

//        try {
//            path = url_list.get((int) (Math.random() * url_list.size()));
//        } catch (Exception e) {
//        }

    }

    /**
     * 获得随机图片地址
     *
     * @param width  宽度
     * @param height 高度
     * @return 指定尺寸的随机图片的地址
     */
    public static String getImage(int width, int height) {
        return "http://lorempixel.com/" + width + "/" + height + "/";
    }

    /**
     * @return 随机图片的地址。尺寸是默认尺寸
     */
    public static String getImage() {
        return url_list.get(Tester.getInt(0, url_list.size()-1));
//        return getImage(default_image_width, default_image_height);
    }
//    /**
//     * 获得随机图片地址
//     *
//     * @return {@link studio.archangel.toolkitv2.util.Tester#url} 中的图片
//     */
//    public static String getImage() {
//        if (url_list.isEmpty()) {
//            try {
//                HttpUtils http = new HttpUtils();
//                http.send(HttpRequest.HttpMethod.GET, url, null, new RequestCallBack<Object>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<Object> responseInfo, JSONObject data) {
//                        String html = null;
//                        try {
//                            html = new String(responseInfo.result.toString().getBytes(), "gb2312");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        int start = html.indexOf("<div class=\"jp-item\">");
//                        html = html.substring(start, html.length());
//                        html = html.substring(0, html.indexOf("<div class=\"jp_more\""));
//                        Document doc = Jsoup.parse(html);
//                        Elements es = doc.select("img[src]");
//                        for (Element e : es) {
//                            url_list.add(e.attr("src"));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg, JSONObject data) {
//
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String path = "";
//
//        try {
//            path = url_list.get((int) (Math.random() * url_list.size()));
//        } catch (Exception e) {
//        }
//
//        return path;
//    }
}
