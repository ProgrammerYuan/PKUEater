import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Michael on 2015/2/6.
 */
public class PanelMain extends JPanel {
    JTextField tf_url, tf_local;
    JButton b_ok;

    public PanelMain() {
        super(new BorderLayout(8, 8));
        setPreferredSize(new Dimension(800, 480));
        tf_url = new JTextField("网址");
        tf_url.setPreferredSize(new Dimension(640, 80));
        this.add(tf_url, BorderLayout.NORTH);
        tf_local = new JTextField("存放路径");
        tf_local.setPreferredSize(new Dimension(640, 80));
        this.add(tf_local, BorderLayout.CENTER);
        b_ok = new JButton("开始下载");
        b_ok.setPreferredSize(new Dimension(640, 80));
        this.add(b_ok, BorderLayout.SOUTH);
        b_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = tf_url.getText().trim();
                String local = tf_local.getText().trim();
                if (url.isEmpty() || local.isEmpty()) {
                    return;
                }
                Util.processUrl(url, local);
            }
        });
    }
}
