package uguu.gao.wafu.myanimesearch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aki on 26/09/13.
 */

abstract class ToggleItem {
    ToggleListView tlv;
    LayoutInflater vi;
    View headingView;
    ViewGroup containerView;
    ImageView toggle;
    TextView heading;
    boolean toggled;
    int id;
    Context context;

    int indexOfParent;

    public ToggleItem(Context context, final ToggleListView tlv, int containerLayoutId, int headingLayoutId, String headingText) {
        this.context = context;

        this.tlv = tlv;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headingView = vi.inflate(headingLayoutId, null);
        toggle = (ImageView) headingView.findViewById(R.id.headingButton);
        toggle.setHapticFeedbackEnabled(true);

        heading = (TextView) headingView.findViewById(R.id.headingText);
        heading.setText(headingText);
        toggled = false;

        containerView = (ViewGroup) vi.inflate(containerLayoutId, null);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexOfParent = tlv.getContainerView().indexOfChild(tlv.getContainerView().findViewById(id));
                indexOfParent = tlv.getContainerView().indexOfChild(headingView);

                toggle.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                if (!toggled) {
                    toggle.setImageResource(R.drawable.ic_find_previous_holo_light);
                    createContainerView();
                    tlv.getContainerView().addView(containerView, indexOfParent + 1);
                } else {
                    onToggleOff();
                }
                toggled = !toggled;
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        if (toggled) {
//            toggle.setImageResource(R.drawable.ic_find_previous_holo_light);
//            containerView.setVisibility(View.VISIBLE);
//            tlv.getContainerView().addView(containerView, indexOfParent + 1);
//
//        } else {
//            onToggleOff();
//        }
//        toggled = !toggled;
//    }

    void onToggleOff() {
        toggle.setImageResource(R.drawable.ic_find_next_holo_light);
        indexOfParent = tlv.getContainerView().indexOfChild(headingView);
        ((ViewGroup)containerView.getParent()).removeViewAt(indexOfParent + 1);
    }

    abstract void createContainerView();

    void addContainerViewToParent() {
        ((ViewGroup)containerView.getParent()).addView(containerView, indexOfParent + 1);
//        containerView.setVisibility(View.INVISIBLE);
    }
}

class ToggleListView extends View  {

    LinearLayout rootView;
    ArrayList<ToggleItem> toggles;

    public ToggleListView(Context context, AttributeSet attrs, LinearLayout layout) {
        super(context, attrs);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layout;
        //rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        toggles = new ArrayList<ToggleItem>();
    }

    void addItem(ToggleItem ti) {
        toggles.add(ti);
    }

    void addViews() {
        for (ToggleItem t : toggles) {
            rootView.addView(t.headingView);
        }
    }

    LinearLayout getContainerView() {
        return rootView;
    }
}
