package miw.fellowshipfungi.controllers.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.models.profile.EnconterCollectionEntity;

public class EnconterAdapter extends RecyclerView.Adapter<EnconterAdapter.EnconterViewHolder> {


    private List<EnconterCollectionEntity> enconterCollectionEntities;

    public EnconterAdapter(List<EnconterCollectionEntity> enconterCollectionEntities) {

        this.enconterCollectionEntities = enconterCollectionEntities;
        Log.w("EnconterAdapter", this.enconterCollectionEntities.toString());


    }

    @NonNull
    @Override
    public EnconterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enconter, parent, false);
        return new EnconterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnconterViewHolder holder, int position) {
        EnconterCollectionEntity enconterCollectionEntity = enconterCollectionEntities.get(position);


        Picasso.get().load(enconterCollectionEntity.getImgUrl()).into(holder.mushroomImage);
        holder.nameMusshroom.setText(enconterCollectionEntity.getNameMussroom());
        holder.etDate.setText(enconterCollectionEntity.getDate());
        holder.location.setText(enconterCollectionEntity.getLocation());
        holder.weather.setText(enconterCollectionEntity.getWeather());

        holder.deleteEnconter.setTag(enconterCollectionEntity.getEnconterID());
    }

    @Override
    public int getItemCount() {
        return enconterCollectionEntities.size();
    }

    public static class EnconterViewHolder extends RecyclerView.ViewHolder {
        ImageView mushroomImage;
        TextView nameMusshroom, etDate, location, weather;
        Button deleteEnconter;

        public EnconterViewHolder(@NonNull View itemView) {
            super(itemView);
            mushroomImage = itemView.findViewById(R.id.mushroomImage);
            nameMusshroom = itemView.findViewById(R.id.nameMusshroom);
            etDate = itemView.findViewById(R.id.etDate);
            location = itemView.findViewById(R.id.location);
            weather = itemView.findViewById(R.id.weather);
            deleteEnconter = itemView.findViewById(R.id.deleteEnconter);
        }
    }
}
