package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Scope.class
  classes14.dex
 */
/* JADX INFO: compiled from: Scope.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J+\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/Scope;", "", "project", "Lorg/catrobat/catroid/content/Project;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lcom/badlogic/gdx/scenes/scene2d/actions/SequenceAction;", "(Lorg/catrobat/catroid/content/Project;Lorg/catrobat/catroid/content/Sprite;Lcom/badlogic/gdx/scenes/scene2d/actions/SequenceAction;)V", "getProject", "()Lorg/catrobat/catroid/content/Project;", "getSequence", "()Lcom/badlogic/gdx/scenes/scene2d/actions/SequenceAction;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Scope {
    private final Project project;
    private final SequenceAction sequence;
    private final Sprite sprite;

    public static /* synthetic */ Scope copy$default(Scope scope, Project project, Sprite sprite, SequenceAction sequenceAction, int i, Object obj) {
        if ((i & 1) != 0) {
            project = scope.project;
        }
        if ((i & 2) != 0) {
            sprite = scope.sprite;
        }
        if ((i & 4) != 0) {
            sequenceAction = scope.sequence;
        }
        return scope.copy(project, sprite, sequenceAction);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Project getProject() {
        return this.project;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Sprite getSprite() {
        return this.sprite;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final SequenceAction getSequence() {
        return this.sequence;
    }

    public final Scope copy(Project project, Sprite sprite, SequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        return new Scope(project, sprite, sequence);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Scope)) {
            return false;
        }
        Scope scope = (Scope) other;
        return Intrinsics.areEqual(this.project, scope.project) && Intrinsics.areEqual(this.sprite, scope.sprite) && Intrinsics.areEqual(this.sequence, scope.sequence);
    }

    public int hashCode() {
        Project project = this.project;
        int iHashCode = (((project == null ? 0 : project.hashCode()) * 31) + this.sprite.hashCode()) * 31;
        SequenceAction sequenceAction = this.sequence;
        return iHashCode + (sequenceAction != null ? sequenceAction.hashCode() : 0);
    }

    public String toString() {
        return "Scope(project=" + this.project + ", sprite=" + this.sprite + ", sequence=" + this.sequence + Constant.AFTER_QUTO;
    }

    public Scope(Project project, Sprite sprite, SequenceAction sequenceAction) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        this.project = project;
        this.sprite = sprite;
        this.sequence = sequenceAction;
    }

    public final Project getProject() {
        return this.project;
    }

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final SequenceAction getSequence() {
        return this.sequence;
    }
}
