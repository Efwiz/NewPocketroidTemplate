package org.catrobat.catroid.libraries;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.utils.lunoscript.Interpreter;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraries/LoadedLibrary.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/libraries/LoadedLibrary;", "", "id", "", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "(Ljava/lang/String;Lorg/catrobat/catroid/utils/lunoscript/Interpreter;)V", "getId", "()Ljava/lang/String;", "getInterpreter", "()Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LoadedLibrary {
    private final String id;
    private final Interpreter interpreter;

    public static /* synthetic */ LoadedLibrary copy$default(LoadedLibrary loadedLibrary, String str, Interpreter interpreter, int i, Object obj) {
        if ((i & 1) != 0) {
            str = loadedLibrary.id;
        }
        if ((i & 2) != 0) {
            interpreter = loadedLibrary.interpreter;
        }
        return loadedLibrary.copy(str, interpreter);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Interpreter getInterpreter() {
        return this.interpreter;
    }

    public final LoadedLibrary copy(String id2, Interpreter interpreter) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        return new LoadedLibrary(id2, interpreter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoadedLibrary)) {
            return false;
        }
        LoadedLibrary loadedLibrary = (LoadedLibrary) other;
        return Intrinsics.areEqual(this.id, loadedLibrary.id) && Intrinsics.areEqual(this.interpreter, loadedLibrary.interpreter);
    }

    public int hashCode() {
        return (this.id.hashCode() * 31) + this.interpreter.hashCode();
    }

    public String toString() {
        return "LoadedLibrary(id=" + this.id + ", interpreter=" + this.interpreter + Constant.AFTER_QUTO;
    }

    public LoadedLibrary(String id2, Interpreter interpreter) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.id = id2;
        this.interpreter = interpreter;
    }

    public final String getId() {
        return this.id;
    }

    public final Interpreter getInterpreter() {
        return this.interpreter;
    }
}
