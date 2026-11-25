package com.android.tools.r8.position;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.references.MethodReference;
import java.util.List;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class MethodPosition implements Position {
    private final MethodReference a;
    private final Position b;

    public MethodPosition(C0684j2 c0684j2) {
        this(c0684j2.E0());
    }

    public static MethodPosition create(o5 o5Var) {
        return create(o5Var.e());
    }

    public boolean equals(Object obj) {
        if (obj instanceof MethodPosition) {
            return this.a.equals(((MethodPosition) obj).a);
        }
        return false;
    }

    @Override // com.android.tools.r8.position.Position
    public String getDescription() {
        return toString();
    }

    public String getHolder() {
        return this.a.getHolderClass().getDescriptor();
    }

    public MethodReference getMethod() {
        return this.a;
    }

    public String getName() {
        return this.a.getMethodName();
    }

    public List<String> getParameterTypes() {
        return (List) this.a.getFormalTypes().stream().map(new MethodPosition$$ExternalSyntheticLambda0()).collect(Collectors.toList());
    }

    public String getReturnType() {
        return this.a.getReturnType().getDescriptor();
    }

    public Position getTextPosition() {
        return this.b;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }

    public MethodPosition(MethodReference methodReference) {
        this(methodReference, Position.UNKNOWN);
    }

    public static MethodPosition create(W0 w0) {
        Position positionH0 = Position.UNKNOWN;
        if (w0.q1() && w0.a1().z0()) {
            positionH0 = w0.a1().K().H0();
        }
        return create(w0.getReference().E0(), positionH0);
    }

    private MethodPosition(MethodReference methodReference, Position position) {
        this.a = methodReference;
        this.b = position;
    }

    public static MethodPosition create(MethodReference methodReference) {
        return new MethodPosition(methodReference, Position.UNKNOWN);
    }

    public static MethodPosition create(MethodReference methodReference, Position position) {
        return new MethodPosition(methodReference, position);
    }
}
