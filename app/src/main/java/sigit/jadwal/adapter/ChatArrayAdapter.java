package sigit.jadwal.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sigit.jadwal.R;

/**
 * Created by sigit on 16/03/2017.
 */

public class ChatArrayAdapter extends ArrayAdapter<String>{
    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;

    ArrayList<String> records;
    Context context;


    public ChatArrayAdapter(Context context, int textViewResourceId, ArrayList<String> records) {
        super(context, textViewResourceId,records);
        this.context=context;
        this.records=records;    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_message, parent, false);
        }
        String[] row_items=records.get(position).split("__");
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(row_items[0]);
        return row;
    }
}
