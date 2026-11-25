package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ArgumentsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/FakeKotlinCallArgumentForCallableReference;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "index", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "(ILorg/jetbrains/kotlin/name/Name;)V", "argumentName", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getIndex", "()I", "isSpread", "", "()Z", "getName", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeKotlinCallArgumentForCallableReference implements KotlinCallArgument {
    private final int index;
    private final Name name;

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return false;
    }

    public FakeKotlinCallArgumentForCallableReference(int i, Name name2) {
        this.index = i;
        this.name = name2;
    }

    public final int getIndex() {
        return this.index;
    }

    public final Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    /* JADX INFO: renamed from: getArgumentName, reason: from getter */
    public Name getName() {
        return this.name;
    }
}
