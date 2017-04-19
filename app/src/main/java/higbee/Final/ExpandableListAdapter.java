package higbee.Final;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mazlin Higbee on 4/18/2017.
 * adapted from
 * http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> mListDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
    }


    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPos)).size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return this.mListDataHeader.get(groupPos);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        return  this.mListDataChild.get(this.mListDataHeader.get(groupPos)).get(childPos);
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPos);
        //inflates layout if the view is null
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;

    }

    @Override
    public View getChildView(int groupPos, final int childPos, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPos,childPos);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = infalInflater.inflate(R.layout.list_item,null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
