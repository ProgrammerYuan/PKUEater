import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


/**
 * Created by Michael on 2015/2/6.
 */
public class Util {
    public static void processUrl(String url, final String local) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            return;
        }
        Elements es = doc.select("a[title=PNG 格式图标下载]");
        if (es == null) {
            return;
        }
        for (Element e : es) {
            final String href = e.attr("href");
            String s = href.substring(0, href.lastIndexOf("/"));
            String[] array = s.split("/", -1);
            final String name1 = array[array.length - 2];
            final String name2 = array[array.length - 1];
//            System.out.println(href);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    download(href, local + "/" + name1 + "_" + name2 + ".png");
                }
            }).start();
        }
    }

    public static void download(String url, String file_path) {
        URL website = null;
        try {
            website = new URL(url);

            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(file_path);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
