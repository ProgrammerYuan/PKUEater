/**
 *
 */
package studio.archangel.toolkitv2.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import studio.archangel.toolkitv2.interfaces.OnCacheGeneratedListener;

import java.util.List;

/**
 * 双重布局适配器。有可能有bug，不建议使用
 *
 * @author Michael
 */
@Deprecated
public abstract class CommonBinaryAdapter<T> extends ArrayAdapter<T> {
    /**
     * 缓存监听器
     *
     * @see studio.archangel.toolkitv2.interfaces.OnCacheGeneratedListener
     */
    protected OnCacheGeneratedListener<T> l;
    /**
     * 上下文
     */
    Context c;
    /**
     * 与此适配器绑定的两个布局的资源ID
     */
    int layoutA_id, layoutB_id;

    /**
     * 初始化
     *
     * @param context      上下文
     * @param list         实体数据
     * @param item_layoutA 布局A的资源ID
     * @param item_layoutB 布局B的资源ID
     */
    public CommonBinaryAdapter(Context context, List<T> list, int item_layoutA, int item_layoutB) {
        super(context, 0, list);
        c = context;
        layoutA_id = item_layoutA;
        layoutB_id = item_layoutB;

    }

    /**
     * 是否应该用布局B来显示
     *
     * @param t 实体数据
     * @return
     */
    public abstract boolean isSuitableForLayoutB(T t);


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) getContext();
        final T item = getItem(position);
        View rowView = convertView;

        CommonAdapterViewCache cache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(isSuitableForLayoutB(item) ? layoutB_id : layoutA_id, null);

            cache = new CommonAdapterViewCache(rowView);
            rowView.setTag(cache);
        } else {
            cache = (CommonAdapterViewCache) rowView.getTag();
        }
        if (l != null) {
            l.onCacheGenerated(cache, item);
        }
        return rowView;
    }

}
