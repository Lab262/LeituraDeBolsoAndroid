package lab262.leituradebolso.ReadingHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.R;

/**
 * Created by luisresende on 12/09/16.
 */
public class ReadingHistoryListAdapter extends BaseAdapter {

    private static ReadingModel[] data;
    private Context context;

    public ReadingHistoryListAdapter(Context context, ReadingModel[] data) {
        this.context = context;
        this.data = data;
    }

    public void getFilter(){

    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReadingHistoryListRow row;
        ReadingModel currentModel = data[position];

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.reading_history_list_row, parent, false);

            row = new ReadingHistoryListRow();
            row.likeButton = (ImageButton) convertView.findViewById(R.id.likeButton);
            row.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            row.authorTextView = (TextView) convertView.findViewById(R.id.authorTextView);
            row.emojiTextView = (TextView) convertView.findViewById(R.id.emojiTextView);
            row.alertImageView = (ImageView) convertView.findViewById(R.id.alertImageView);

            convertView.setTag(row);

        }else{
            row = (ReadingHistoryListRow) convertView.getTag();
        }


        if (!currentModel.isRead) {
            row.alertImageView.setVisibility(View.INVISIBLE);
        } else {
            row.alertImageView.setVisibility(View.VISIBLE);
        }

        if (!currentModel.isLiked) {
            row.likeButton.setBackgroundResource(R.drawable.liked);
        } else {
            row.likeButton.setBackgroundResource(R.drawable.like);
        }

        row.titleTextView.setText(currentModel.title);
        row.authorTextView.setText(currentModel.author);

        StringBuilder allEmojis = new StringBuilder();
        for(String emoji : currentModel.emojis) {
            if(allEmojis.length() > 0) {
                allEmojis.append(" "); // some divider between the different texts
            }
            allEmojis.append(emoji);
        }

        row.emojiTextView.setText(allEmojis.toString());

        return convertView;
    }
}
