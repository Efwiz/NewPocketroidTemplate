package org.jetbrains.kotlin.js.backend.ast;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;

/* JADX INFO: compiled from: jsScopes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 \u00192\u00020\u0001:\u0002\u0019\u001aB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0004H\u0016R,\u0010\b\u001a\u001a\u0012\b\u0012\u00060\nR\u00020\u00000\tj\f\u0012\b\u0012\u00060\nR\u00020\u0000`\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR*\u0010\u000e\u001a\u001e\u0012\b\u0012\u00060\nR\u00020\u0000\u0018\u00010\tj\u000e\u0012\b\u0012\u00060\nR\u00020\u0000\u0018\u0001`\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\b\u0018\u00010\nR\u00020\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "parent", MediaTrack.ROLE_DESCRIPTION, "", "useParentScopeStack", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Ljava/lang/String;Z)V", "labelScopes", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope$LabelScope;", "Lkotlin/collections/ArrayList;", "getLabelScopes", "()Ljava/util/ArrayList;", "labelScopesImpl", "topLabelScope", "getTopLabelScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope$LabelScope;", "enterLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "outputName", "exitLabel", "", "findLabel", "Companion", "LabelScope", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JsDeclarationScope extends JsScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<String> RESERVED_WORDS = SetsKt.plus((Set) IdentifierPolicyKt.getRESERVED_KEYWORDS(), (Iterable) SetsKt.setOf((Object[]) new String[]{"NaN", "isNaN", "Infinity", "undefined", "Error", CommonClassNames.JAVA_LANG_OBJECT_SHORT, "Math", CommonClassNames.JAVA_LANG_STRING_SHORT, "Number", "Boolean", "Date", "Array", "RegExp", JsonFactory.FORMAT_NAME_JSON, "require", "define", "module", ConfigConstants.CONFIG_KEY_WINDOW, "self", "Kotlin"}));
    private ArrayList<LabelScope> labelScopesImpl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsDeclarationScope(JsScope parent, String description, boolean z) {
        super(parent, description);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(description, "description");
        this.labelScopesImpl = ((parent instanceof JsDeclarationScope) && z) ? ((JsDeclarationScope) parent).labelScopesImpl : null;
    }

    public /* synthetic */ JsDeclarationScope(JsScope jsScope, String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsScope, str, (i & 4) != 0 ? false : z);
    }

    private final LabelScope getTopLabelScope() {
        ArrayList<LabelScope> arrayList = this.labelScopesImpl;
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return (LabelScope) CollectionsKt.last((List) arrayList);
    }

    private final ArrayList<LabelScope> getLabelScopes() {
        ArrayList<LabelScope> arrayList = this.labelScopesImpl;
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList<LabelScope> arrayList2 = new ArrayList<>();
        this.labelScopesImpl = arrayList2;
        return arrayList2;
    }

    public JsName enterLabel(String label, String outputName) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(outputName, "outputName");
        LabelScope labelScope = new LabelScope(this, getTopLabelScope(), label, outputName);
        getLabelScopes().add(labelScope);
        return labelScope.getLabelName();
    }

    public void exitLabel() {
        ArrayList<LabelScope> labelScopes = getLabelScopes();
        labelScopes.isEmpty();
    }

    public JsName findLabel(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        LabelScope topLabelScope = getTopLabelScope();
        if (topLabelScope != null) {
            return topLabelScope.findName(label);
        }
        return null;
    }

    /* JADX INFO: compiled from: jsScopes.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0018\u00010\u0000R\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000f\u001a\u00020\u0005H\u0014J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope$LabelScope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "parent", "Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope;", "ident", "", "outputIdent", "(Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope;Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope$LabelScope;Ljava/lang/String;Ljava/lang/String;)V", "getIdent", "()Ljava/lang/String;", "labelName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getLabelName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "findOwnName", "name", "hasOwnName", "", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class LabelScope extends JsScope {
        private final String ident;
        private final JsName labelName;
        final /* synthetic */ JsDeclarationScope this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LabelScope(JsDeclarationScope jsDeclarationScope, LabelScope labelScope, String ident, String outputIdent) {
            super(labelScope, "Label scope for " + ident);
            Intrinsics.checkNotNullParameter(ident, "ident");
            Intrinsics.checkNotNullParameter(outputIdent, "outputIdent");
            this.this$0 = jsDeclarationScope;
            this.ident = ident;
            this.labelName = new JsName(outputIdent, true);
        }

        public final String getIdent() {
            return this.ident;
        }

        public final JsName getLabelName() {
            return this.labelName;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
        protected JsName findOwnName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            if (Intrinsics.areEqual(name2, this.ident)) {
                return this.labelName;
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
        public boolean hasOwnName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            if (!JsDeclarationScope.INSTANCE.getRESERVED_WORDS().contains(name2) && !Intrinsics.areEqual(name2, this.ident)) {
                JsName jsName = this.labelName;
                if (!Intrinsics.areEqual(name2, jsName != null ? jsName.getIdent() : null)) {
                    JsScope parent = getParent();
                    if (!(parent != null ? parent.hasOwnName(name2) : false)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /* JADX INFO: compiled from: jsScopes.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope$Companion;", "", "()V", "RESERVED_WORDS", "", "", "getRESERVED_WORDS", "()Ljava/util/Set;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<String> getRESERVED_WORDS() {
            return JsDeclarationScope.RESERVED_WORDS;
        }
    }
}
