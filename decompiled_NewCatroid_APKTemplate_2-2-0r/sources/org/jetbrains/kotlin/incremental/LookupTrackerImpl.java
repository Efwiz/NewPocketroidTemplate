package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.incremental.components.ScopeKind;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.utils.PlatformUtilsKt;

/* JADX INFO: compiled from: LookupStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\b\u0010,\u001a\u00020-H\u0016J0\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u001a2\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020#2\u0006\u00103\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\u0007\u001a#\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00060\u00060\b¢\u0006\u0002\b\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R\u001c\u0010\"\u001a\u0004\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/incremental/LookupTrackerImpl;", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/incremental/components/LookupTracker;)V", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Interner;", "", "lookups", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "getLookups", "()Lcom/intellij/util/containers/MultiMap;", "pathInterner", "getPathInterner", "()Lcom/intellij/util/containers/Interner;", "prevFilePath", "getPrevFilePath", "()Ljava/lang/String;", "setPrevFilePath", "(Ljava/lang/String;)V", "prevName", "getPrevName", "setPrevName", "prevPosition", "Lorg/jetbrains/kotlin/incremental/components/Position;", "getPrevPosition", "()Lorg/jetbrains/kotlin/incremental/components/Position;", "setPrevPosition", "(Lorg/jetbrains/kotlin/incremental/components/Position;)V", "prevScopeFqName", "getPrevScopeFqName", "setPrevScopeFqName", "prevScopeKind", "Lorg/jetbrains/kotlin/incremental/components/ScopeKind;", "getPrevScopeKind", "()Lorg/jetbrains/kotlin/incremental/components/ScopeKind;", "setPrevScopeKind", "(Lorg/jetbrains/kotlin/incremental/components/ScopeKind;)V", "requiresPosition", "", "getRequiresPosition", "()Z", LineReader.CLEAR, "", PsiKeyword.RECORD, "filePath", "position", "scopeFqName", "scopeKind", "name", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookupTrackerImpl implements LookupTracker {
    private final LookupTracker delegate;
    private final Interner<String> interner;
    private final MultiMap<LookupSymbol, String> lookups;
    private final Interner<String> pathInterner;
    private String prevFilePath;
    private String prevName;
    private Position prevPosition;
    private String prevScopeFqName;
    private ScopeKind prevScopeKind;

    public LookupTrackerImpl(LookupTracker delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
        MultiMap<LookupSymbol, String> multiMapCreateSet = MultiMap.createSet();
        Intrinsics.checkNotNullExpressionValue(multiMapCreateSet, "createSet(...)");
        this.lookups = multiMapCreateSet;
        this.pathInterner = PlatformUtilsKt.createStringInterner();
        this.interner = PlatformUtilsKt.createStringInterner();
        this.prevFilePath = "";
        this.prevScopeFqName = "";
        this.prevName = "";
    }

    public final MultiMap<LookupSymbol, String> getLookups() {
        return this.lookups;
    }

    public final Interner<String> getPathInterner() {
        return this.pathInterner;
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public boolean getRequiresPosition() {
        return this.delegate.getRequiresPosition();
    }

    public final String getPrevFilePath() {
        return this.prevFilePath;
    }

    public final void setPrevFilePath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.prevFilePath = str;
    }

    public final Position getPrevPosition() {
        return this.prevPosition;
    }

    public final void setPrevPosition(Position position) {
        this.prevPosition = position;
    }

    public final String getPrevScopeFqName() {
        return this.prevScopeFqName;
    }

    public final void setPrevScopeFqName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.prevScopeFqName = str;
    }

    public final ScopeKind getPrevScopeKind() {
        return this.prevScopeKind;
    }

    public final void setPrevScopeKind(ScopeKind scopeKind) {
        this.prevScopeKind = scopeKind;
    }

    public final String getPrevName() {
        return this.prevName;
    }

    public final void setPrevName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.prevName = str;
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public void record(String filePath, Position position, String scopeFqName, ScopeKind scopeKind, String name2) {
        boolean z;
        boolean z2;
        boolean z3;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scopeFqName, "scopeFqName");
        Intrinsics.checkNotNullParameter(scopeKind, "scopeKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        boolean z4 = true;
        if (Intrinsics.areEqual(name2, this.prevName)) {
            z = false;
        } else {
            String strIntern = this.interner.intern(name2);
            Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            this.prevName = strIntern;
            z = true;
        }
        if (Intrinsics.areEqual(scopeFqName, this.prevScopeFqName)) {
            z2 = false;
        } else {
            String strIntern2 = this.interner.intern(scopeFqName);
            Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
            this.prevScopeFqName = strIntern2;
            z2 = true;
        }
        if (Intrinsics.areEqual(filePath, this.prevFilePath)) {
            z3 = false;
        } else {
            String strIntern3 = this.pathInterner.intern(filePath);
            Intrinsics.checkNotNullExpressionValue(strIntern3, "intern(...)");
            this.prevFilePath = strIntern3;
            z3 = true;
        }
        if (!z && !z2 && !z3) {
            z4 = false;
        }
        if (z4) {
            this.lookups.putValue(new LookupSymbol(this.prevName, this.prevScopeFqName), this.prevFilePath);
        }
        if (!z4 && Intrinsics.areEqual(this.prevPosition, position) && this.prevScopeKind == scopeKind) {
            return;
        }
        this.prevPosition = position;
        this.prevScopeKind = scopeKind;
        this.delegate.record(this.prevFilePath, position, this.prevScopeFqName, scopeKind, this.prevName);
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public void clear() {
        this.lookups.clear();
        this.prevFilePath = "";
        this.prevPosition = null;
        this.prevScopeFqName = "";
        this.prevScopeKind = null;
        this.prevName = "";
    }
}
