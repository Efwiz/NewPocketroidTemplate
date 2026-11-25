package org.catrobat.catroid.ui.recyclerview.repository;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/repository/LocalHashVersionRepository.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalHashVersionRepository.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u0003H&J\u0012\u0010\t\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u0003H&¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/repository/LocalHashVersionRepository;", "", "getFeaturedProjectsHashVersion", "", "getProjectsCategoriesHashVersion", "reset", "", "setFeaturedProjectsHashVersion", "hashVersion", "setProjectsCategoriesHashVersion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LocalHashVersionRepository {
    String getFeaturedProjectsHashVersion();

    String getProjectsCategoriesHashVersion();

    void reset();

    void setFeaturedProjectsHashVersion(String hashVersion);

    void setProjectsCategoriesHashVersion(String hashVersion);
}
