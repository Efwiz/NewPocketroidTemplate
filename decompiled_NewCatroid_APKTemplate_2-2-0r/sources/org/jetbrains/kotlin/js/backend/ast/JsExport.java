package org.jetbrains.kotlin.js.backend.ast;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.apk.PathMap;
import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: JsExport.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u001f B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u001b\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB\u0019\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0019\u001a\u00020\u0002H\u0016J\u001c\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsExport;", "Lorg/jetbrains/kotlin/js/backend/ast/SourceInfoAwareJsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Element;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Element;)V", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "subject", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;", "fromModule", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;Ljava/lang/String;)V", "getFromModule", "()Ljava/lang/String;", "getSubject", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "acceptChildren", "deepCopy", "traverse", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Element", "Subject", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExport extends SourceInfoAwareJsNode implements JsStatement {
    private final String fromModule;
    private final Subject subject;

    public /* synthetic */ JsExport(Subject subject, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(subject, (i & 2) != 0 ? null : str);
    }

    public final Subject getSubject() {
        return this.subject;
    }

    public final String getFromModule() {
        return this.fromModule;
    }

    public JsExport(Subject subject, String str) {
        Intrinsics.checkNotNullParameter(subject, "subject");
        this.subject = subject;
        this.fromModule = str;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public JsExport(Element element) {
        this((Subject) new Subject.Elements(CollectionsKt.listOf(element)), (String) null, 2, (DefaultConstructorMarker) (0 == true ? 1 : 0));
        Intrinsics.checkNotNullParameter(element, "element");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsExport(JsNameRef name2, JsName jsName) {
        this(new Element(name2, jsName));
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    public /* synthetic */ JsExport(JsNameRef jsNameRef, JsName jsName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsNameRef, (i & 2) != 0 ? null : jsName);
    }

    /* JADX INFO: compiled from: JsExport.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;", "", "()V", "All", "Elements", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject$All;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject$Elements;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Subject {
        public /* synthetic */ Subject(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: JsExport.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject$Elements;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Element;", "(Ljava/util/List;)V", "getElements", "()Ljava/util/List;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Elements extends Subject {
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

        private Subject() {
        }

        /* JADX INFO: compiled from: JsExport.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject$All;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Subject;", "()V", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class All extends Subject {
            public static final All INSTANCE = new All();

            private All() {
                super(null);
            }
        }
    }

    /* JADX INFO: compiled from: JsExport.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsExport$Element;", "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "getAlias", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Element {
        private final JsName alias;
        private final JsNameRef name;

        public Element(JsNameRef name2, JsName jsName) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.alias = jsName;
        }

        public /* synthetic */ Element(JsNameRef jsNameRef, JsName jsName, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(jsNameRef, (i & 2) != 0 ? null : jsName);
        }

        public final JsName getAlias() {
            return this.alias;
        }

        public final JsNameRef getName() {
            return this.name;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitExport(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Subject subject = this.subject;
        if (subject instanceof Subject.Elements) {
            Iterator<T> it = ((Subject.Elements) subject).getElements().iterator();
            while (it.hasNext()) {
                visitor.accept(((Element) it.next()).getName());
            }
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsStatement deepCopy() {
        return new JsExport(this.subject, this.fromModule);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext v, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        v.visit(this, ctx);
        v.endVisit(this, ctx);
    }
}
