package studio.archangel.toolkitv2.util;

import android.view.View;
import android.widget.EditText;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import studio.archangel.toolkitv2.util.text.Notifier;

/**
 * Created by Michael on 2014/11/17.
 */
public abstract class InputValidator implements Validator.ValidationListener{


    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();

        if (failedView instanceof EditText) {
            failedView.requestFocus();
//            ((EditText) failedView).setError(message);
        } else {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        Notifier.showNormalMsg(failedView.getContext(), message);
    }

}
