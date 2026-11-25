package kotlin.script.experimental.api;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u001f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\u0004¢\u0006\u0002\u0010\nJ\u0011\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0001H\u0096\u0002J\t\u0010\u0010\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0004HÂ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u000f\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0004HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u001b"}, d2 = {"Lkotlin/script/experimental/api/ReplSnippetIdImpl;", "Lkotlin/script/experimental/api/ReplSnippetId;", "Ljava/io/Serializable;", "no", "", "generation", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lkotlin/script/experimental/api/SourceCode;", "(IILkotlin/script/experimental/api/SourceCode;)V", "codeHash", "(III)V", "getGeneration", "()I", "getNo", "compareTo", "other", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "toString", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReplSnippetIdImpl implements ReplSnippetId, Serializable {
    private static final long serialVersionUID = 1;
    private final int codeHash;
    private final int generation;
    private final int no;

    /* JADX INFO: renamed from: component3, reason: from getter */
    private final int getCodeHash() {
        return this.codeHash;
    }

    public static /* synthetic */ ReplSnippetIdImpl copy$default(ReplSnippetIdImpl replSnippetIdImpl, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = replSnippetIdImpl.no;
        }
        if ((i4 & 2) != 0) {
            i2 = replSnippetIdImpl.generation;
        }
        if ((i4 & 4) != 0) {
            i3 = replSnippetIdImpl.codeHash;
        }
        return replSnippetIdImpl.copy(i, i2, i3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getNo() {
        return this.no;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getGeneration() {
        return this.generation;
    }

    public final ReplSnippetIdImpl copy(int no, int generation, int codeHash) {
        return new ReplSnippetIdImpl(no, generation, codeHash);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReplSnippetIdImpl)) {
            return false;
        }
        ReplSnippetIdImpl replSnippetIdImpl = (ReplSnippetIdImpl) other;
        return this.no == replSnippetIdImpl.no && this.generation == replSnippetIdImpl.generation && this.codeHash == replSnippetIdImpl.codeHash;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.no) * 31) + Integer.hashCode(this.generation)) * 31) + Integer.hashCode(this.codeHash);
    }

    public String toString() {
        return "ReplSnippetIdImpl(no=" + this.no + ", generation=" + this.generation + ", codeHash=" + this.codeHash + ')';
    }

    public ReplSnippetIdImpl(int i, int i2, int i3) {
        this.no = i;
        this.generation = i2;
        this.codeHash = i3;
    }

    @Override // kotlin.script.experimental.api.ReplSnippetId
    public int getGeneration() {
        return this.generation;
    }

    @Override // kotlin.script.experimental.api.ReplSnippetId
    public int getNo() {
        return this.no;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ReplSnippetIdImpl(int i, int i2, SourceCode code) {
        this(i, i2, code.getText().hashCode());
        Intrinsics.checkNotNullParameter(code, "code");
    }

    @Override // java.lang.Comparable
    public int compareTo(ReplSnippetId other) {
        Intrinsics.checkNotNullParameter(other, "other");
        ReplSnippetIdImpl replSnippetIdImpl = other instanceof ReplSnippetIdImpl ? (ReplSnippetIdImpl) other : null;
        if (replSnippetIdImpl == null) {
            return -1;
        }
        Integer numValueOf = Integer.valueOf(Intrinsics.compare(getNo(), replSnippetIdImpl.getNo()));
        if (numValueOf.intValue() == 0) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        Integer numValueOf2 = Integer.valueOf(Intrinsics.compare(getGeneration(), replSnippetIdImpl.getGeneration()));
        Integer num = numValueOf2.intValue() != 0 ? numValueOf2 : null;
        if (num != null) {
            return num.intValue();
        }
        return Intrinsics.compare(this.codeHash, replSnippetIdImpl.codeHash);
    }
}
