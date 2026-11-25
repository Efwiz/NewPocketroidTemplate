package io.github.rosemoe.sora.lang.completion.snippet;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PlaceholderDefinition.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B=\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\nHÆ\u0003JA\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nHÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020\u0003HÖ\u0001J\t\u0010(\u001a\u00020\u0006HÖ\u0001R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006)"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/snippet/PlaceholderDefinition;", "", "id", "", "choices", "", "", PathTree.NAME_elements, "Lio/github/rosemoe/sora/lang/completion/snippet/PlaceHolderElement;", "transform", "Lio/github/rosemoe/sora/lang/completion/snippet/Transform;", "(ILjava/util/List;Ljava/util/List;Lio/github/rosemoe/sora/lang/completion/snippet/Transform;)V", "getChoices", "()Ljava/util/List;", "setChoices", "(Ljava/util/List;)V", "getElements", "setElements", "getId", "()I", "setId", "(I)V", "text", "getText$editor_release", "()Ljava/lang/String;", "setText$editor_release", "(Ljava/lang/String;)V", "getTransform", "()Lio/github/rosemoe/sora/lang/completion/snippet/Transform;", "setTransform", "(Lio/github/rosemoe/sora/lang/completion/snippet/Transform;)V", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PlaceholderDefinition {
    private List<String> choices;
    private List<? extends PlaceHolderElement> elements;
    private int id;
    private String text;
    private Transform transform;

    public PlaceholderDefinition(int i) {
        this(i, null, null, null, 14, null);
    }

    public PlaceholderDefinition(int i, List<String> list) {
        this(i, list, null, null, 12, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PlaceholderDefinition(int i, List<String> list, List<? extends PlaceHolderElement> elements) {
        this(i, list, elements, null, 8, null);
        Intrinsics.checkNotNullParameter(elements, "elements");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PlaceholderDefinition copy$default(PlaceholderDefinition placeholderDefinition, int i, List list, List list2, Transform transform, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = placeholderDefinition.id;
        }
        if ((i2 & 2) != 0) {
            list = placeholderDefinition.choices;
        }
        if ((i2 & 4) != 0) {
            list2 = placeholderDefinition.elements;
        }
        if ((i2 & 8) != 0) {
            transform = placeholderDefinition.transform;
        }
        return placeholderDefinition.copy(i, list, list2, transform);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    public final List<String> component2() {
        return this.choices;
    }

    public final List<PlaceHolderElement> component3() {
        return this.elements;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Transform getTransform() {
        return this.transform;
    }

    public final PlaceholderDefinition copy(int id2, List<String> choices, List<? extends PlaceHolderElement> elements, Transform transform) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return new PlaceholderDefinition(id2, choices, elements, transform);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlaceholderDefinition)) {
            return false;
        }
        PlaceholderDefinition placeholderDefinition = (PlaceholderDefinition) other;
        return this.id == placeholderDefinition.id && Intrinsics.areEqual(this.choices, placeholderDefinition.choices) && Intrinsics.areEqual(this.elements, placeholderDefinition.elements) && Intrinsics.areEqual(this.transform, placeholderDefinition.transform);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        List<String> list = this.choices;
        int iHashCode2 = (((iHashCode + (list == null ? 0 : list.hashCode())) * 31) + this.elements.hashCode()) * 31;
        Transform transform = this.transform;
        return iHashCode2 + (transform != null ? transform.hashCode() : 0);
    }

    public String toString() {
        return "PlaceholderDefinition(id=" + this.id + ", choices=" + this.choices + ", elements=" + this.elements + ", transform=" + this.transform + Constant.AFTER_QUTO;
    }

    public PlaceholderDefinition(int i, List<String> list, List<? extends PlaceHolderElement> elements, Transform transform) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.id = i;
        this.choices = list;
        this.elements = elements;
        this.transform = transform;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final List<String> getChoices() {
        return this.choices;
    }

    public final void setChoices(List<String> list) {
        this.choices = list;
    }

    public /* synthetic */ PlaceholderDefinition(int i, List list, ArrayList arrayList, Transform transform, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list, (i2 & 4) != 0 ? new ArrayList() : arrayList, (i2 & 8) != 0 ? null : transform);
    }

    public final List<PlaceHolderElement> getElements() {
        return this.elements;
    }

    public final void setElements(List<? extends PlaceHolderElement> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.elements = list;
    }

    public final Transform getTransform() {
        return this.transform;
    }

    public final void setTransform(Transform transform) {
        this.transform = transform;
    }

    /* JADX INFO: renamed from: getText$editor_release, reason: from getter */
    public final String getText() {
        return this.text;
    }

    public final void setText$editor_release(String str) {
        this.text = str;
    }
}
