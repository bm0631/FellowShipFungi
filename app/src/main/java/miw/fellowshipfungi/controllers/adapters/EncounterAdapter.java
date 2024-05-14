package miw.fellowshipfungi.controllers.adapters;

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
import miw.fellowshipfungi.models.profile.EncounterCollectionEntity;

public class EncounterAdapter extends RecyclerView.Adapter<EncounterAdapter.EncounterViewHolder> {


    private List<EncounterCollectionEntity> encounterCollectionEntities;

    public EncounterAdapter(List<EncounterCollectionEntity> encounterCollectionEntities) {

        this.encounterCollectionEntities = encounterCollectionEntities;
    }

    @NonNull
    @Override
    public EncounterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encounter, parent, false);
        return new EncounterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EncounterViewHolder holder, int position) {
        EncounterCollectionEntity encounterCollectionEntity = encounterCollectionEntities.get(position);


        Picasso.get().load(encounterCollectionEntity.getImgUrl()).into(holder.mushroomImage);
        holder.nameMushroom.setText(encounterCollectionEntity.getNameMussroom());
        holder.etDate.setText(encounterCollectionEntity.getDate());
        holder.location.setText(encounterCollectionEntity.getLocation());
        holder.weather.setText(encounterCollectionEntity.getWeather());

        holder.deleteEncounter.setTag(encounterCollectionEntity.getEncounterID());
    }

    @Override
    public int getItemCount() {
        return encounterCollectionEntities.size();
    }

    public static class EncounterViewHolder extends RecyclerView.ViewHolder {
        ImageView mushroomImage;
        TextView nameMushroom, etDate, location, weather;
        Button deleteEncounter;

        public EncounterViewHolder(@NonNull View itemView) {
            super(itemView);
            mushroomImage = itemView.findViewById(R.id.mushroomImage);
            nameMushroom = itemView.findViewById(R.id.nameMushroom);
            etDate = itemView.findViewById(R.id.etDate);
            location = itemView.findViewById(R.id.location);
            weather = itemView.findViewById(R.id.weather);
            deleteEncounter = itemView.findViewById(R.id.deleteEncounter);
        }
    }
}
