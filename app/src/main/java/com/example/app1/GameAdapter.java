package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> games;
    private Context context;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.tvGameName.setText(game.getName());
        holder.ivGameIcon.setImageResource(game.getIconResId());
        
        holder.btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(context, game.getActivityClass());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGameIcon;
        TextView tvGameName;
        Button btnPlay;

        GameViewHolder(View itemView) {
            super(itemView);
            ivGameIcon = itemView.findViewById(R.id.ivGameIcon);
            tvGameName = itemView.findViewById(R.id.tvGameName);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }
} 