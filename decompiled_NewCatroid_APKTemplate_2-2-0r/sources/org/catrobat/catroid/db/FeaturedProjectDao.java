package org.catrobat.catroid.db;

import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.retrofit.models.FeaturedProject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/FeaturedProjectDao.class
  classes14.dex
 */
/* JADX INFO: compiled from: FeaturedProjectDao.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H'J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H'J\u0016\u0010\t\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H'¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/db/FeaturedProjectDao;", "", "()V", "deleteAll", "", "getFeaturedProjects", "Lkotlinx/coroutines/flow/Flow;", "", "Lorg/catrobat/catroid/retrofit/models/FeaturedProject;", "insertFeaturedProjects", "projects", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FeaturedProjectDao {
    public abstract void deleteAll();

    public abstract Flow<List<FeaturedProject>> getFeaturedProjects();

    public abstract void insertFeaturedProjects(List<FeaturedProject> projects);
}
