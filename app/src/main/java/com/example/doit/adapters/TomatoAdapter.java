package com.example.doit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.doit.App;
import com.example.doit.R;
import com.example.doit.logic.TomatoTimer;
import com.example.doit.model.Task;

import java.util.List;

public class TomatoAdapter extends RecyclerView.Adapter<TomatoAdapter.TomatoViewHolder>  {

    private SortedList<Task> tomatoList;

    public TomatoAdapter() {
       tomatoList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.priority > o2.priority) {
                    return 1;
                }
                if(o1.priority < o2.priority) {
                    return -1;
                }
                return (int) (o1.time - o2.time);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return  item1.ID == item2.ID;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public TomatoAdapter.TomatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TomatoAdapter.TomatoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tomato_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TomatoAdapter.TomatoViewHolder holder, int position) {
        holder.bind(tomatoList.get(position));
    }

    @Override
    public int getItemCount() {
        return tomatoList.size();
    }

    public void setItems(List<Task> tasks) {
        tomatoList.replaceAll(tasks);
    }

    static class TomatoViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        LinearLayout taskPriorityIndex;

        int PRIORITY_INDEX_COLOR;
        Task task;
        boolean statusUpdate;

        public TomatoViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText  = (TextView) itemView.findViewById(R.id.text);
            taskPriorityIndex = (LinearLayout) itemView.findViewById(R.id.priority);
        }

        public void bind(Task task) {
            this.task = task;

            switch (task.priority) {
                case 5:
                    break;
                case 1:
                    PRIORITY_INDEX_COLOR = R.drawable.priority_item_one;
                    break;
                case 2:
                    PRIORITY_INDEX_COLOR = R.drawable.priority_item_two;
                    break;
                case 3:
                    PRIORITY_INDEX_COLOR = R.drawable.priority_item_tree;
                    break;
                case 4:
                    PRIORITY_INDEX_COLOR = R.drawable.priority_item_four;
                    break;
            }

            taskPriorityIndex.setBackgroundResource(PRIORITY_INDEX_COLOR);
            taskText.setText(task.text);
        }
    }
}
