package org.jetbrains.kotlin.js.backend.ast;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.apk.PathMap;
import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsImport.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u001e\u001fB#\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007¢\u0006\u0002\u0010\bB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0002H\u0016J\u001c\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport;", "Lorg/jetbrains/kotlin/js/backend/ast/SourceInfoAwareJsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "module", "", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Element;", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Element;)V", "target", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;)V", "", "getElements", "()Ljava/util/List;", "getModule", "()Ljava/lang/String;", "getTarget", "()Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "acceptChildren", "deepCopy", "traverse", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Element", "Target", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsImport extends SourceInfoAwareJsNode implements JsStatement {
    private final String module;
    private final Target target;

    public final String getModule() {
        return this.module;
    }

    public final Target getTarget() {
        return this.target;
    }

    public JsImport(String module, Target target) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(target, "target");
        this.module = module;
        this.target = target;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsImport(String module, Element... elements) {
        this(module, new Target.Elements(ArraysKt.toMutableList(elements)));
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(elements, "elements");
    }

    public final List<Element> getElements() {
        Target target = this.target;
        Intrinsics.checkNotNull(target, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsImport.Target.Elements");
        return ((Target.Elements) target).getElements();
    }

    /* JADX INFO: compiled from: JsImport.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", "", "()V", "All", "Default", "Elements", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$All;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$Default;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$Elements;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Target {
        public /* synthetic */ Target(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: JsImport.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$Elements;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Element;", "(Ljava/util/List;)V", "getElements", "()Ljava/util/List;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Elements extends Target {
            private final List<Element> elements;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Elements(List<Element> elements) {
                super(null);
                Intrinsics.checkNotNullParameter(elements, "elements");
                this.elements = elements;
            }

            public final List<Element> getElements() {
                return this.elements;
            }
        }

        private Target() {
        }

        /* JADX INFO: compiled from: JsImport.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$Default;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", "name", "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)V", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Default extends Target {
            private final JsNameRef name;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Default(JsNameRef name2) {
                super(null);
                Intrinsics.checkNotNullParameter(name2, "name");
                this.name = name2;
            }

            public final JsNameRef getName() {
                return this.name;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public Default(String name2) {
                this(new JsNameRef(name2));
                Intrinsics.checkNotNullParameter(name2, "name");
            }
        }

        /* JADX INFO: compiled from: JsImport.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target$All;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Target;", PathMap.NAME_alias, "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)V", "getAlias", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class All extends Target {
            private final JsNameRef alias;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public All(JsNameRef alias) {
                super(null);
                Intrinsics.checkNotNullParameter(alias, "alias");
                this.alias = alias;
            }

            public final JsNameRef getAlias() {
                return this.alias;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public All(String alias) {
                this(new JsNameRef(alias));
                Intrinsics.checkNotNullParameter(alias, "alias");
            }
        }
    }

    /* JADX INFO: compiled from: JsImport.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImport$Element;", "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)V", "getAlias", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Element {
        private final JsNameRef alias;
        private final JsName name;

        public Element(JsName name2, JsNameRef jsNameRef) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.alias = jsNameRef;
        }

        public /* synthetic */ Element(JsName jsName, JsNameRef jsNameRef, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(jsName, (i & 2) != 0 ? null : jsNameRef);
        }

        public final JsName getName() {
            return this.name;
        }

        public final JsNameRef getAlias() {
            return this.alias;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitImport(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Target target = this.target;
        if (target instanceof Target.All) {
            visitor.accept(((Target.All) target).getAlias());
            return;
        }
        if (target instanceof Target.Default) {
            visitor.accept(((Target.Default) target).getName());
            return;
        }
        if (target instanceof Target.Elements) {
            Iterator<T> it = ((Target.Elements) target).getElements().iterator();
            while (it.hasNext()) {
                JsNameRef alias = ((Element) it.next()).getAlias();
                if (alias != null) {
                    visitor.accept(alias);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsStatement deepCopy() {
        return new JsImport(this.module, this.target);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext v, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        v.visit(this, ctx);
        v.endVisit(this, ctx);
    }
}
