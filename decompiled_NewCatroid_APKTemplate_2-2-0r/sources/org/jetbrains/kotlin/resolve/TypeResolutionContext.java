package org.jetbrains.kotlin.resolve;

import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: loaded from: classes6.dex */
public class TypeResolutionContext {
    public final boolean abbreviated;
    public final boolean allowBareTypes;
    public final boolean allowIntersectionTypes;
    public final boolean checkBounds;
    public final boolean isDebuggerContext;
    public final LexicalScope scope;
    public final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3 || i == 5) {
            objArr[0] = "trace";
        } else {
            objArr[0] = "scope";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/TypeResolutionContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TypeResolutionContext(LexicalScope lexicalScope, BindingTrace bindingTrace, boolean z, boolean z2, boolean z3) {
        this(lexicalScope, bindingTrace, z, z2, z3, false, false);
        if (lexicalScope == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TypeResolutionContext(LexicalScope lexicalScope, BindingTrace bindingTrace, boolean z, boolean z2, boolean z3, boolean z4) {
        this(lexicalScope, bindingTrace, z, z2, z3, z4, false);
        if (lexicalScope == null) {
            $$$reportNull$$$0(2);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(3);
        }
    }

    public TypeResolutionContext(LexicalScope lexicalScope, BindingTrace bindingTrace, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(4);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(5);
        }
        this.scope = lexicalScope;
        this.trace = bindingTrace;
        this.checkBounds = z;
        this.allowBareTypes = z2;
        this.isDebuggerContext = z3;
        this.abbreviated = z4;
        this.allowIntersectionTypes = z5;
    }

    public TypeResolutionContext noBareTypes() {
        return new TypeResolutionContext(this.scope, this.trace, this.checkBounds, false, this.isDebuggerContext, this.abbreviated, this.allowIntersectionTypes);
    }
}
