package org.catrobat.catroid.ui.recyclerview.repository;

import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.retrofit.models.FeaturedProject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/repository/FeaturedProjectsRepository.class
  classes14.dex
 */
/* JADX INFO: compiled from: FeaturedProjectsRepository.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/repository/FeaturedProjectsRepository;", "", "getFeaturedProjects", "Lkotlinx/coroutines/flow/Flow;", "", "Lorg/catrobat/catroid/retrofit/models/FeaturedProject;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FeaturedProjectsRepository {
    Flow<List<FeaturedProject>> getFeaturedProjects();
}
