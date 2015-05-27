/**
 *
 */
package studio.archangel.toolkitv2;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.mobsandgeeks.saripaar.Validator;
import studio.archangel.toolkitv2.dialogs.LoadingDialog;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

/**
 * @author Administrator
 */
public class AngelActivity extends Activity {
    public LoadingDialog dialog;
    public boolean destroyed  = false;
    Validator validator;
    private boolean orientation_set = false;
    AngelActionBar bar;
    /**
     * 加载指定的Feature
     *
     * @param savedInstanceState
     * @param feature_id
     */
    protected void onCreate(Bundle savedInstanceState, int[] feature_id, int orientation) {
        super.onCreate(savedInstanceState);
        init(feature_id, orientation);
    }

    protected void onCreate(Bundle savedInstanceState, int[] feature_id) {
        super.onCreate(savedInstanceState);
        init(feature_id, -1);
    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(null, -1);
    }

    public AngelActionBar getAngelActionBar() {
        return bar;
    }

    public void setAngelActionBar(AngelActionBar bar) {
        this.bar = bar;
    }


    protected void setOrientation(int info) {
        if (!shouldOverrideOrientation()) {
            return;
        }
        if (info == -1) {
            return;
        }
        try {
            setRequestedOrientation(info);
            orientation_set = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        destroyed = true;
        if(dialog!=null){
            dialog.forceDismiss();
        }

        super.onDestroy();
    }
    protected boolean shouldOverrideOrientation() {
        return false;
    }

    /**
     * 初始化。将Actionbar的图标设置为App图标，并将点击图标映射为返回键
     *
     * @param feature_id 要加载的Feature的id
     */
    void init(int[] feature_id, int orientation) {
        setOrientation(orientation);
        if (!orientation_set) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (feature_id != null) {
            for (int id : feature_id) {
                requestWindowFeature(id);
                if (id == Window.FEATURE_NO_TITLE) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        }
//        if (feature_id != -10) {
//            requestWindowFeature(feature_id);
//        }
        try {
            getActionBar().setIcon(R.drawable.ic_launcher);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
//            Logger.err(e.toString());
//            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setValidator(Validator.ValidationListener l) {
        validator = new Validator(this);
        validator.setValidationListener(l);
    }

    protected void validate() {
        if (validator != null) {
            validator.validate();
        } else {
            Logger.out("validator is null");
        }
    }
}
