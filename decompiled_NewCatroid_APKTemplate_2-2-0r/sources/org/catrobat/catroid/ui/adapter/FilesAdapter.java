package org.catrobat.catroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/FilesAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: FilesAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001bBY\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0013H\u0016J\u0018\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0013H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/ui/adapter/FilesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/catroid/ui/adapter/FilesAdapter$ViewHolder;", "project", "Lorg/catrobat/catroid/content/Project;", "files", "", "Ljava/io/File;", "onDelete", "Lkotlin/Function1;", "", "onCopy", "onOpen", "(Lorg/catrobat/catroid/content/Project;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "formatFileSize", "", "size", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilesAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<File> files;
    private final Function1<File, Unit> onCopy;
    private final Function1<File, Unit> onDelete;
    private final Function1<File, Unit> onOpen;
    private final Project project;

    /* JADX WARN: Multi-variable type inference failed */
    public FilesAdapter(Project project, List<? extends File> files, Function1<? super File, Unit> onDelete, Function1<? super File, Unit> onCopy, Function1<? super File, Unit> onOpen) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(onDelete, "onDelete");
        Intrinsics.checkNotNullParameter(onCopy, "onCopy");
        Intrinsics.checkNotNullParameter(onOpen, "onOpen");
        this.project = project;
        this.files = files;
        this.onDelete = onDelete;
        this.onCopy = onCopy;
        this.onOpen = onOpen;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/FilesAdapter$ViewHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FilesAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/ui/adapter/FilesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", Promotion.ACTION_VIEW, "Landroid/view/View;", "(Landroid/view/View;)V", "deleteButton", "Landroid/widget/ImageButton;", "getDeleteButton", "()Landroid/widget/ImageButton;", "fileIcon", "Landroid/widget/ImageView;", "getFileIcon", "()Landroid/widget/ImageView;", PsiTreeChangeEvent.PROP_FILE_NAME, "Landroid/widget/TextView;", "getFileName", "()Landroid/widget/TextView;", "fileSize", "getFileSize", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton deleteButton;
        private final ImageView fileIcon;
        private final TextView fileName;
        private final TextView fileSize;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(View view) {
            super(view);
            Intrinsics.checkNotNullParameter(view, "view");
            View viewFindViewById = view.findViewById(R.id.file_name);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.fileName = (TextView) viewFindViewById;
            View viewFindViewById2 = view.findViewById(R.id.file_size);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.fileSize = (TextView) viewFindViewById2;
            View viewFindViewById3 = view.findViewById(R.id.file_icon);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
            this.fileIcon = (ImageView) viewFindViewById3;
            View viewFindViewById4 = view.findViewById(R.id.deleteButton);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
            this.deleteButton = (ImageButton) viewFindViewById4;
        }

        public final TextView getFileName() {
            return this.fileName;
        }

        public final TextView getFileSize() {
            return this.fileSize;
        }

        public final ImageView getFileIcon() {
            return this.fileIcon;
        }

        public final ImageButton getDeleteButton() {
            return this.deleteButton;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new ViewHolder(viewInflate);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:53:0x011a  */
    /* JADX WARN: Code duplicated, block: B:63:0x0139  */
    /* JADX WARN: Code duplicated, block: B:76:0x0161  */
    /* JADX WARN: Code duplicated, block: B:80:0x016e  */
    /* JADX WARN: Code duplicated, block: B:81:0x0172  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        int i;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final File file = this.files.get(position);
        holder.getFileName().setText(file.getName());
        if (file.isDirectory()) {
            holder.getFileIcon().setImageResource(R.drawable.folder_copy_24px);
            if (Intrinsics.areEqual(file.getName(), "..")) {
                holder.getDeleteButton().setVisibility(8);
                holder.getFileSize().setText("");
            } else {
                holder.getDeleteButton().setVisibility(0);
                File[] fileArrListFiles = file.listFiles();
                holder.getFileSize().setText("Elements: " + (fileArrListFiles != null ? fileArrListFiles.length : 0));
            }
        } else {
            holder.getDeleteButton().setVisibility(0);
            String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            switch (lowerCase) {
                case "rscene":
                    i = R.drawable.deployed_code_24px;
                    break;
                case "js":
                case "kt":
                case "md":
                case "py":
                    i = R.drawable.code_24px;
                    break;
                case "gif":
                    i = R.drawable.ic_draw_image;
                    break;
                case "glb":
                    i = R.drawable.deployed_code_24px;
                    break;
                case "jpg":
                    i = R.drawable.ic_draw_image;
                    break;
                case "m4a":
                    i = R.drawable.ic_music_library;
                    break;
                case "lua":
                    i = R.drawable.code_24px;
                    break;
                case "mp3":
                    i = R.drawable.ic_music_library;
                    break;
                case "obj":
                    i = R.drawable.deployed_code_24px;
                    break;
                case "ogg":
                    i = R.drawable.ic_music_library;
                    break;
                case "png":
                    i = R.drawable.ic_draw_image;
                    break;
                case "txt":
                    i = R.drawable.code_24px;
                    break;
                case "wav":
                    i = R.drawable.ic_music_library;
                    break;
                case "xml":
                case "java":
                    i = R.drawable.code_24px;
                    break;
                case "jpeg":
                    i = R.drawable.ic_draw_image;
                    break;
                case "json":
                    i = R.drawable.code_24px;
                    break;
                case "webp":
                    i = R.drawable.ic_draw_image;
                    break;
                default:
                    i = R.drawable.file_present_24px;
                    break;
            }
            holder.getFileIcon().setImageResource(i);
            if (file.exists()) {
                holder.getFileSize().setText(formatFileSize(file.length()));
            } else {
                holder.getFileSize().setText("File not found");
            }
        }
        holder.getDeleteButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.adapter.FilesAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesAdapter.onBindViewHolder$lambda$0(this.f$0, file, view);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.adapter.FilesAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilesAdapter.onBindViewHolder$lambda$1(this.f$0, file, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.adapter.FilesAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return FilesAdapter.onBindViewHolder$lambda$2(this.f$0, file, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(FilesAdapter this$0, File file, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        this$0.onDelete.invoke(file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(FilesAdapter this$0, File file, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        this$0.onOpen.invoke(file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$2(FilesAdapter this$0, File file, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        this$0.onCopy.invoke(file);
        return true;
    }

    public final String formatFileSize(long size) {
        int i = 0;
        String[] strArr = {"B", "KB", "MB", "GB", "TB"};
        double d = size;
        while (d >= 1024.0d && i < 4) {
            d /= (double) 1024;
            i++;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.1f %s", Arrays.copyOf(new Object[]{Double.valueOf(d), strArr[i]}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.files.size();
    }
}
