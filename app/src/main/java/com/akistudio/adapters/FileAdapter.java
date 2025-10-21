package com.akistudio.adapters; // <-- THIS LINE IS CORRECTED

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.akistudio.databinding.FileListItemBinding;
import com.akistudio.editor.EditorActivity;
import java.io.File;
import java.util.List;

/**
 * Adapter for displaying files and directories in a RecyclerView.
 * This now belongs in the 'adapters' package.
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<File> files;
    private final OnFileClickListener listener;

    public interface OnFileClickListener {
        void onFileClick(File file);
    }

    public FileAdapter(List<File> files, OnFileClickListener listener) {
        this.files = files;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FileListItemBinding binding = FileListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File file = files.get(position);
        holder.bind(file, listener);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void updateFiles(List<File> newFiles) {
        this.files = newFiles;
        notifyDataSetChanged();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        private final FileListItemBinding binding;

        public FileViewHolder(FileListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final File file, final OnFileClickListener listener) {
            binding.fileName.setText(file.getName());
            if (file.isDirectory()) {
                binding.fileIcon.setImageResource(com.akistudio.R.drawable.ic_folder);
            } else {
                binding.fileIcon.setImageResource(com.akistudio.R.drawable.ic_file);
            }

            itemView.setOnClickListener(v -> {
                if (file.isDirectory()) {
                    listener.onFileClick(file);
                } else {
                    // Directly open the editor for files
                    Intent intent = new Intent(itemView.getContext(), EditorActivity.class);
                    intent.putExtra(EditorActivity.EXTRA_FILE_PATH, file.getAbsolutePath());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
