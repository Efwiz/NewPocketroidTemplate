package org.catrobat.catroid.ui.filepicker;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.io.File;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/FilePickerAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FilePickerAdapter extends RVAdapter<File> {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/FilePickerAdapter$OnItemClickListener.class
      classes14.dex
     */
    public interface OnItemClickListener {
        void onItemClick(File item);

        boolean onItemLongClick(File item);
    }

    protected FilePickerAdapter(List<File> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_file, parent, false));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        File file = (File) this.items.get(position);
        holder.title.setText(file.getName());
        ((FileViewHolder) holder).subtitle.setText(file.getAbsolutePath());
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }
}
