package com.example.usermanagementapp.view;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermanagementapp.R;
import com.example.usermanagementapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView, listener, users);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.textViewName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        holder.textViewEmail.setText(currentUser.getEmail());
        String avatarUrl = currentUser.getAvatar();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            // Load user avatar with Glide
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(avatarUrl))
                    .placeholder(R.drawable.placeholder_avatar)
                    .into(holder.imageViewAvatar);
        } else {
            // Load a placeholder image if avatarUrl is null or empty
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder_avatar) // Use a local drawable as a placeholder
                    .into(holder.imageViewAvatar);
        }

    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewEmail;
        private final ImageView imageViewAvatar;
        private final Button buttonEdit;
        private final Button buttonDelete;

        private List<User> users;
        private OnItemClickListener listener;

        public UserViewHolder(@NonNull View itemView, OnItemClickListener listener, List<User> users) {
            super(itemView);
            this.listener = listener;
            this.users = users;

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            imageViewAvatar = itemView.findViewById(R.id.image_view_avatar);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            buttonEdit.setOnClickListener(view -> {
                if (listener != null && getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onEditClick(users.get(getAbsoluteAdapterPosition()));
                }
            });

            buttonDelete.setOnClickListener(view -> {
                if (listener != null && getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(users.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onEditClick(User user);

        void onDeleteClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
