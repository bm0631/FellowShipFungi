package miw.fellowshipfungi.controllers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.models.ask.recognitionmodels.AnswerEntity;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<AnswerEntity> answers;

    public AnswerAdapter(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnswerEntity answer = answers.get(position);
        holder.answerButton.setText(answer.getText());
        holder.answerButton.setTag(answer.getNextNode());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button answerButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answerButton = itemView.findViewById(R.id.answerButton);

        }


    }


}

