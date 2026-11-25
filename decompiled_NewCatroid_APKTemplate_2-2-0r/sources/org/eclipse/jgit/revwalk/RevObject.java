package org.eclipse.jgit.revwalk;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import net.bytebuddy.asm.Advice;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

/* JADX INFO: loaded from: classes13.dex */
public abstract class RevObject extends ObjectIdOwnerMap.Entry {
    static final int PARSED = 1;
    int flags;

    public final ObjectId getId() {
        return this;
    }

    public abstract int getType();

    abstract void parseBody(RevWalk revWalk) throws IOException;

    abstract void parseHeaders(RevWalk revWalk) throws IOException;

    RevObject(AnyObjectId anyObjectId) {
        super(anyObjectId);
    }

    public final boolean has(RevFlag revFlag) {
        return (revFlag.mask & this.flags) != 0;
    }

    public final boolean hasAny(RevFlagSet revFlagSet) {
        return (revFlagSet.mask & this.flags) != 0;
    }

    public final boolean hasAll(RevFlagSet revFlagSet) {
        return (this.flags & revFlagSet.mask) == revFlagSet.mask;
    }

    public final void add(RevFlag revFlag) {
        this.flags = revFlag.mask | this.flags;
    }

    public final void add(RevFlagSet revFlagSet) {
        this.flags = revFlagSet.mask | this.flags;
    }

    public final void remove(RevFlag revFlag) {
        this.flags = (~revFlag.mask) & this.flags;
    }

    public final void remove(RevFlagSet revFlagSet) {
        this.flags = (~revFlagSet.mask) & this.flags;
    }

    @Override // org.eclipse.jgit.lib.AnyObjectId
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.typeString(getType()));
        sb.append(StringUtil.SPACE);
        sb.append(name());
        sb.append(StringUtil.SPACE);
        appendCoreFlags(sb);
        return sb.toString();
    }

    protected void appendCoreFlags(StringBuilder sb) {
        sb.append((this.flags & 32) != 0 ? 'o' : '-');
        sb.append((this.flags & 64) != 0 ? 'q' : '-');
        sb.append((this.flags & 16) != 0 ? Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL : '-');
        sb.append((this.flags & 8) != 0 ? Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL : '-');
        sb.append((this.flags & 4) != 0 ? 'u' : '-');
        sb.append((this.flags & 2) != 0 ? Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL : '-');
        sb.append((this.flags & 1) != 0 ? Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL : '-');
    }
}
