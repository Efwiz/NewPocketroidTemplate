package org.catrobat.catroid.retrofit.models;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/models/FeaturedProject.class
  classes14.dex
 */
/* JADX INFO: compiled from: Models.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003JE\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/retrofit/models/FeaturedProject;", "", "id", "", "project_id", "project_url", "name", "author", "featured_image", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAuthor", "()Ljava/lang/String;", "getFeatured_image", "getId", "getName", "getProject_id", "getProject_url", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FeaturedProject {
    private final String author;
    private final String featured_image;
    private final String id;
    private final String name;
    private final String project_id;
    private final String project_url;

    public static /* synthetic */ FeaturedProject copy$default(FeaturedProject featuredProject, String str, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = featuredProject.id;
        }
        if ((i & 2) != 0) {
            str2 = featuredProject.project_id;
        }
        String str7 = str2;
        if ((i & 4) != 0) {
            str3 = featuredProject.project_url;
        }
        String str8 = str3;
        if ((i & 8) != 0) {
            str4 = featuredProject.name;
        }
        String str9 = str4;
        if ((i & 16) != 0) {
            str5 = featuredProject.author;
        }
        String str10 = str5;
        if ((i & 32) != 0) {
            str6 = featuredProject.featured_image;
        }
        return featuredProject.copy(str, str7, str8, str9, str10, str6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getProject_id() {
        return this.project_id;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getProject_url() {
        return this.project_url;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getAuthor() {
        return this.author;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getFeatured_image() {
        return this.featured_image;
    }

    public final FeaturedProject copy(String id2, String project_id, String project_url, String name2, String author, String featured_image) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(project_id, "project_id");
        Intrinsics.checkNotNullParameter(project_url, "project_url");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(featured_image, "featured_image");
        return new FeaturedProject(id2, project_id, project_url, name2, author, featured_image);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FeaturedProject)) {
            return false;
        }
        FeaturedProject featuredProject = (FeaturedProject) other;
        return Intrinsics.areEqual(this.id, featuredProject.id) && Intrinsics.areEqual(this.project_id, featuredProject.project_id) && Intrinsics.areEqual(this.project_url, featuredProject.project_url) && Intrinsics.areEqual(this.name, featuredProject.name) && Intrinsics.areEqual(this.author, featuredProject.author) && Intrinsics.areEqual(this.featured_image, featuredProject.featured_image);
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.project_id.hashCode()) * 31) + this.project_url.hashCode()) * 31) + this.name.hashCode()) * 31) + this.author.hashCode()) * 31) + this.featured_image.hashCode();
    }

    public String toString() {
        return "FeaturedProject(id=" + this.id + ", project_id=" + this.project_id + ", project_url=" + this.project_url + ", name=" + this.name + ", author=" + this.author + ", featured_image=" + this.featured_image + Constant.AFTER_QUTO;
    }

    public FeaturedProject(String id2, String project_id, String project_url, String name2, String author, String featured_image) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(project_id, "project_id");
        Intrinsics.checkNotNullParameter(project_url, "project_url");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(featured_image, "featured_image");
        this.id = id2;
        this.project_id = project_id;
        this.project_url = project_url;
        this.name = name2;
        this.author = author;
        this.featured_image = featured_image;
    }

    public final String getId() {
        return this.id;
    }

    public final String getProject_id() {
        return this.project_id;
    }

    public final String getProject_url() {
        return this.project_url;
    }

    public final String getName() {
        return this.name;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final String getFeatured_image() {
        return this.featured_image;
    }
}
