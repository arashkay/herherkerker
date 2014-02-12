package com.tectual.herherkerker.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tectual.herherkerker.R;
import com.tectual.herherkerker.web.data.JsonAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arash on 7/02/2014.
 */
public class QuestionAdapter extends ArrayAdapter<JsonAnswer> {


    private List<JsonAnswer> items;
    private Context context;
    private boolean single;
    private String selected;
    private List<String> selected_items;

    public QuestionAdapter(Context context, int textViewResourceId,
                       List<JsonAnswer> items, boolean single) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
        this.single = single;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        JsonAnswer obj = items.get(position);
        if (obj != null) {
            if(single){
                v = vi.inflate(R.layout.single_answer, null);
                RadioButton radio = (RadioButton) v.findViewById(R.id.answer);
                radio.setText(obj.title);
                radio.setChecked(selected==obj.value);
                radio.setTag(obj.value);
                radio.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected = v.getTag().toString();
                        ListView listview = (ListView) v.getParent().getParent();
                        for (int i = 0; i < listview.getChildCount(); i++) {
                            RadioButton radio = (RadioButton) ((LinearLayout)listview.getChildAt(i)).getChildAt(0);
                            radio.setChecked(radio.getTag()==selected);
                        }
                    }
                });
            }else{
                v = vi.inflate(R.layout.multi_answer, null);
                CheckBox checkbox = ((CheckBox) v.findViewById(R.id.answer));
                checkbox.setText(obj.title + obj.title);
                checkbox.setTag(obj.value);
                checkbox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String item = v.getTag().toString();
                        int i = selected_items.indexOf(item);
                        if(v.isSelected()){
                            if(i==-1) selected_items.add(item);
                        }else{
                            if(i!=-1) selected_items.remove(i);
                        }
                    }
                });
            }
        }

        return v;
    }

    public List<String> getSelectedItems() {
        List<String> result = new ArrayList<String>();
        if(single){
            if(selected!=null && !selected.isEmpty())
                result.add(selected);
        }else{
            result = selected_items;
        }
        return result;
    }
}
