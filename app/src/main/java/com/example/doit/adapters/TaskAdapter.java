package com.example.doit.adapters;

import android.text.Layout;
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
import com.example.doit.model.Task;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private SortedList<Task> list;

    public TaskAdapter() {
        list = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.status && (!o2.status)) {
                    return 1;
                }
                if(o2.status && (!o1.status)) {
                    return -1;
                }
                return o1.priority - o2.priority;
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
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<Task> tasks) {
        list.replaceAll(tasks);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskText;
        CheckBox taskCheck;
        ImageView taskDelete;
        LinearLayout taskPriorityIndex;

        int PRIORITY_INDEX_COLOR;
        Task task;

        boolean statusUpdate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText  = (TextView) itemView.findViewById(R.id.textTask);
            taskCheck = (CheckBox) itemView.findViewById(R.id.checkTask);
            taskDelete = (ImageView) itemView.findViewById(R.id.taskDelete);
            taskPriorityIndex = (LinearLayout) itemView.findViewById(R.id.priorityIndex);

            taskDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().deleteTask(task);
                }
            });

            taskCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(!statusUpdate) {
                        task.status = isChecked;
                        App.getInstance().updateTask(task);
                   }
                }
            });
        }

        public void bind(Task task) {
            this.task = task;
            statusUpdate = true;

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
            taskCheck.setChecked(task.status);
            statusUpdate = false;
            taskText.setText(task.text);
        }
    }
}
