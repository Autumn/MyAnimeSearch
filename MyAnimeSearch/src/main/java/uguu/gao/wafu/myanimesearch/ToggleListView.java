package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aki on 26/09/13.
 */

abstract class ToggleItem implements View.OnClickListener {
    ToggleListView tlv;
    LayoutInflater vi;
    View headingView;
    View containerView;
    ImageView toggle;
    TextView heading;
    boolean toggled;
    int id;
    Context context;

    int indexOfParent;

    public ToggleItem(Context context, ToggleListView tlv, int containerLayoutId, String headingText) {
        this.context = context;

        this.tlv = tlv;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headingView = vi.inflate(R.layout.toggle_heading, null);
        toggle = (ImageView) headingView.findViewById(R.id.headingButton);
        toggle.setHapticFeedbackEnabled(true);

        heading = (TextView) headingView.findViewById(R.id.headingText);
        heading.setText(headingText);
        toggled = false;

        containerView = vi.inflate(containerLayoutId, null);
        indexOfParent = tlv.getContainerView().indexOfChild(tlv.getContainerView().findViewById(id));
    }

    @Override
    public void onClick(View view) {
        if (toggled) {
            onToggleOn();

            toggle.setImageResource(R.drawable.ic_find_previous_holo_light);
            tlv.getContainerView().addView(containerView, indexOfParent + 1);

        } else {
            onToggleOff();
        }
        toggled = !toggled;
    }

    void onToggleOff() {
        toggle.setImageResource(R.drawable.ic_find_next_holo_light);
        tlv.getContainerView().removeViewAt(indexOfParent + 1);
    }

    abstract void onToggleOn();
}

class ToggleListView extends View  {

    LinearLayout rootView;
    ArrayList<ToggleItem> toggles;

    public ToggleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = new LinearLayout(context);
        toggles = new ArrayList<ToggleItem>();
    }

    void addItem(ToggleItem ti) {
        rootView.addView(ti.headingView);
        toggles.add(ti);
    }

    LinearLayout getContainerView() {
        return rootView;
    }
}
