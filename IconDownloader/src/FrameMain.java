import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Michael on 2015/2/6.
 */
public class FrameMain extends JFrame {
    public FrameMain() {
        super();
        setTitle("IconDownloader");
        setLayout(new BorderLayout());
        setSize(800, 480);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        add(new PanelMain(), BorderLayout.CENTER);
        validate();


    }
}
