package uguu.gao.wafu.myanimesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import uguu.gao.wafu.javaMAL.AnimeResult;

/**
 * Created by aki on 26/09/13.
 * Information
 * Synopsis
 * Related Anime
 * Characters + Seiyuu
 * Staff
 */
public class AnimeSingleResultExpandableListAdapter extends BaseExpandableListAdapter {
    AnimeResult result;
    Context context;
    String[] groupNames = {"Information", "Synopsis", "Related Works", "Characters", "Staff"};

    public AnimeSingleResultExpandableListAdapter(Context context, AnimeResult result) {
        super();
        this.context = context;
        this.result = result;
    }

    public View getChildView(int position, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int getGroupCount() {
        return groupNames.length;
    }

    public boolean hasStableIds() {
        return true;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public Object getGroup(int groupPosition) {
        return groupNames[groupPosition];
    }

    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String header = groupNames[groupPosition];
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.anime_single_group, null);
        }
       // TextView label = (TextView) convertView.findViewById(R.id.anime_expandableOption);
       // label.setText(header);

        return convertView;
    }
}

