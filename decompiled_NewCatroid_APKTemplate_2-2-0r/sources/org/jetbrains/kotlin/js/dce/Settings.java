package org.jetbrains.kotlin.js.dce;

import kotlin.Metadata;

/* JADX INFO: compiled from: printTree.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/Settings;", "", "printNestedMembers", "", "showLocations", "(ZZ)V", "getPrintNestedMembers", "()Z", "getShowLocations", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Settings {
    private final boolean printNestedMembers;
    private final boolean showLocations;

    public Settings(boolean z, boolean z2) {
        this.printNestedMembers = z;
        this.showLocations = z2;
    }

    public final boolean getPrintNestedMembers() {
        return this.printNestedMembers;
    }

    public final boolean getShowLocations() {
        return this.showLocations;
    }
}
