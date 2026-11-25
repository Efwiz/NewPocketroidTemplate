package org.catrobat.catroid.utils.lunoscript;

import android.util.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Scope.class
  classes14.dex
 */
/* JADX INFO: compiled from: Scope.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\"B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\tH\u0002J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012J\u0016\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0012J\u001e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u000bJ\u0016\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\bJ\u0016\u0010 \u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010!\u001a\u00020\tR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Scope;", "", "enclosing", "(Lorg/catrobat/catroid/utils/lunoscript/Scope;)V", "getEnclosing$catroid_apktemplateTemplateRelease", "()Lorg/catrobat/catroid/utils/lunoscript/Scope;", "locals", "", "Lorg/catrobat/catroid/utils/lunoscript/AstNode;", "", CompoundEntry.NAME_values, "", "Lorg/catrobat/catroid/utils/lunoscript/Scope$ScopeEntry;", "getValues", "()Ljava/util/Map;", "ancestor", "distance", "assign", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "nameToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "value", "assignAt", "", "define", "name", "isConstant", "", "get", "getAt", "lookUpVariable", "expr", "resolve", ConfigConstants.CONFIG_KEY_DEPTH, "ScopeEntry", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Scope {
    private final Scope enclosing;
    private final Map<AstNode, Integer> locals;
    private final Map<String, ScopeEntry> values;

    /* JADX WARN: Multi-variable type inference failed */
    public Scope() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Scope$ScopeEntry.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Scope.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Scope$ScopeEntry;", "", "value", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "isConstant", "", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue;Z)V", "()Z", "getValue", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ScopeEntry {
        private final boolean isConstant;
        private final LunoValue value;

        public static /* synthetic */ ScopeEntry copy$default(ScopeEntry scopeEntry, LunoValue lunoValue, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                lunoValue = scopeEntry.value;
            }
            if ((i & 2) != 0) {
                z = scopeEntry.isConstant;
            }
            return scopeEntry.copy(lunoValue, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final LunoValue getValue() {
            return this.value;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsConstant() {
            return this.isConstant;
        }

        public final ScopeEntry copy(LunoValue value, boolean isConstant) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new ScopeEntry(value, isConstant);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ScopeEntry)) {
                return false;
            }
            ScopeEntry scopeEntry = (ScopeEntry) other;
            return Intrinsics.areEqual(this.value, scopeEntry.value) && this.isConstant == scopeEntry.isConstant;
        }

        public int hashCode() {
            return (this.value.hashCode() * 31) + Boolean.hashCode(this.isConstant);
        }

        public String toString() {
            return "ScopeEntry(value=" + this.value + ", isConstant=" + this.isConstant + Constant.AFTER_QUTO;
        }

        public ScopeEntry(LunoValue value, boolean z) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
            this.isConstant = z;
        }

        public final LunoValue getValue() {
            return this.value;
        }

        public final boolean isConstant() {
            return this.isConstant;
        }
    }

    public Scope(Scope scope) {
        this.enclosing = scope;
        this.values = new LinkedHashMap();
        this.locals = new LinkedHashMap();
    }

    public /* synthetic */ Scope(Scope scope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : scope);
    }

    /* JADX INFO: renamed from: getEnclosing$catroid_apktemplateTemplateRelease, reason: from getter */
    public final Scope getEnclosing() {
        return this.enclosing;
    }

    public final Map<String, ScopeEntry> getValues() {
        return this.values;
    }

    public final LunoValue assign(Token nameToken, LunoValue value) {
        Intrinsics.checkNotNullParameter(nameToken, "nameToken");
        Intrinsics.checkNotNullParameter(value, "value");
        String lexeme = nameToken.getLexeme();
        if (this.values.containsKey(lexeme)) {
            ScopeEntry scopeEntry = this.values.get(lexeme);
            Intrinsics.checkNotNull(scopeEntry);
            if (scopeEntry.isConstant()) {
                throw new LunoRuntimeError("Cannot assign to a constant variable '" + lexeme + "'.", nameToken.getLine(), null, 4, null);
            }
            Map<String, ScopeEntry> map = this.values;
            ScopeEntry scopeEntry2 = map.get(lexeme);
            Intrinsics.checkNotNull(scopeEntry2);
            map.put(lexeme, ScopeEntry.copy$default(scopeEntry2, value, false, 2, null));
            return value;
        }
        Scope scope = this.enclosing;
        if (scope != null) {
            return scope.assign(nameToken, value);
        }
        throw new LunoRuntimeError("Undefined variable '" + lexeme + "'.", nameToken.getLine(), null, 4, null);
    }

    public final void define(String name2, LunoValue value, boolean isConstant) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Log.d("LunoScope", "DEFINE: " + (isConstant ? "val" : PsiKeyword.VAR) + " '" + name2 + "' = " + value + " in scope " + this);
        this.values.containsKey(name2);
        this.values.put(name2, new ScopeEntry(value, isConstant));
    }

    public final void define(String name2, LunoValue value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        define(name2, value, false);
    }

    public final LunoValue get(Token nameToken) {
        Intrinsics.checkNotNullParameter(nameToken, "nameToken");
        String lexeme = nameToken.getLexeme();
        Log.d("LunoScope", "GET: var '" + lexeme + "' from scope " + this + " (enclosing: " + this.enclosing + "). Keys: " + this.values.keySet());
        if (this.values.containsKey(lexeme)) {
            ScopeEntry scopeEntry = this.values.get(lexeme);
            Intrinsics.checkNotNull(scopeEntry);
            return scopeEntry.getValue();
        }
        Scope scope = this.enclosing;
        if (scope != null) {
            return scope.get(nameToken);
        }
        throw new LunoRuntimeError("Undefined variable '" + lexeme + "'.", nameToken.getLine(), null, 4, null);
    }

    public final LunoValue getAt(int distance, String name2) {
        LunoValue value;
        Intrinsics.checkNotNullParameter(name2, "name");
        ScopeEntry scopeEntry = ancestor(distance).values.get(name2);
        return (scopeEntry == null || (value = scopeEntry.getValue()) == null) ? LunoValue.Null.INSTANCE : value;
    }

    public final void assignAt(int distance, Token nameToken, LunoValue value) {
        Intrinsics.checkNotNullParameter(nameToken, "nameToken");
        Intrinsics.checkNotNullParameter(value, "value");
        Scope scopeAncestor = ancestor(distance);
        String lexeme = nameToken.getLexeme();
        if (scopeAncestor.values.containsKey(lexeme)) {
            ScopeEntry scopeEntry = scopeAncestor.values.get(lexeme);
            Intrinsics.checkNotNull(scopeEntry);
            if (scopeEntry.isConstant()) {
                throw new LunoRuntimeError("Cannot assign to a constant variable '" + lexeme + "'.", nameToken.getLine(), null, 4, null);
            }
            Map<String, ScopeEntry> map = scopeAncestor.values;
            ScopeEntry scopeEntry2 = map.get(lexeme);
            Intrinsics.checkNotNull(scopeEntry2);
            map.put(lexeme, ScopeEntry.copy$default(scopeEntry2, value, false, 2, null));
            return;
        }
        scopeAncestor.values.put(lexeme, new ScopeEntry(value, false));
    }

    private final Scope ancestor(int distance) {
        Scope scope = this;
        for (int i = 0; i < distance; i++) {
            scope = scope.enclosing;
            if (scope == null) {
                throw new LunoRuntimeError("Scope nesting error.", 0, null, 6, null);
            }
        }
        return scope;
    }

    public final void resolve(AstNode expr, int depth) {
        Intrinsics.checkNotNullParameter(expr, "expr");
        this.locals.put(expr, Integer.valueOf(depth));
    }

    public final LunoValue lookUpVariable(Token name2, AstNode expr) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(expr, "expr");
        Integer num = this.locals.get(expr);
        if (num != null) {
            return getAt(num.intValue(), name2.getLexeme());
        }
        return get(name2);
    }
}
