package org.jetbrains.kotlin.fir.declarations;

import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.android.dx.cf.attrib.AttSynthetic;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.fir.declarations.utils.FirScriptCustomizationKind;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0011\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001eB1\b\u0004\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u0003H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n\u0082\u0001\u0011\u001f !\"#$%&'()*+,-./¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "", "displayName", "", "fromSupertypes", "", "generated", "fromSource", "(Ljava/lang/String;ZZZ)V", "getFromSource", "()Z", "getFromSupertypes", "getGenerated", "toString", "BuiltIns", "Delegated", "DynamicScope", "Enhancement", "ImportedFromObjectOrStatic", "IntersectionOverride", "Java", "Library", "Plugin", "Precompiled", "RenamedForOverride", "SamConstructor", "ScriptCustomization", PngChunkTextVar.KEY_Source, "SubstitutionOverride", AttSynthetic.ATTRIBUTE_NAME, "WrappedIntegerOperator", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$BuiltIns;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Delegated;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$DynamicScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Enhancement;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ImportedFromObjectOrStatic;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$IntersectionOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Library;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Plugin;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Precompiled;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$RenamedForOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SamConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Source;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$WrappedIntegerOperator;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDeclarationOrigin {
    private final String displayName;
    private final boolean fromSource;
    private final boolean fromSupertypes;
    private final boolean generated;

    public /* synthetic */ FirDeclarationOrigin(String str, boolean z, boolean z2, boolean z3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, z2, z3);
    }

    private FirDeclarationOrigin(String str, boolean z, boolean z2, boolean z3) {
        this.displayName = str;
        this.fromSupertypes = z;
        this.generated = z2;
        this.fromSource = z3;
    }

    public /* synthetic */ FirDeclarationOrigin(String str, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? false : z3, null);
    }

    public final boolean getFromSupertypes() {
        return this.fromSupertypes;
    }

    public final boolean getGenerated() {
        return this.generated;
    }

    public final boolean getFromSource() {
        return this.fromSource;
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Source;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Source extends FirDeclarationOrigin {
        public static final Source INSTANCE = new Source();

        private Source() {
            super(null, false, false, true, 7, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Library;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Library extends FirDeclarationOrigin {
        public static final Library INSTANCE = new Library();

        private Library() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Precompiled;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Precompiled extends FirDeclarationOrigin {
        public static final Precompiled INSTANCE = new Precompiled();

        private Precompiled() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$BuiltIns;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BuiltIns extends FirDeclarationOrigin {
        public static final BuiltIns INSTANCE = new BuiltIns();

        private BuiltIns() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "displayName", "", "fromSource", "", "(Ljava/lang/String;Z)V", "Library", PngChunkTextVar.KEY_Source, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java$Library;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java$Source;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Java extends FirDeclarationOrigin {
        public /* synthetic */ Java(String str, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, z);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java$Source;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Source extends Java {
            public static final Source INSTANCE = new Source();

            private Source() {
                super("Java(Source)", true, null);
            }
        }

        private Java(String str, boolean z) {
            super(str, false, false, z, 6, null);
        }

        public /* synthetic */ Java(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? false : z, null);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java$Library;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Library extends Java {
            public static final Library INSTANCE = new Library();

            private Library() {
                super("Java(Library)", false, 2, null);
            }
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\t\u0003\u0004\u0005\u0006\u0007\b\t\n\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\t\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", "DataClassMember", "DelegateField", "Error", "FakeFunction", "ForwardDeclaration", "JavaProperty", "PluginFile", "TypeAliasConstructor", "ValueClassMember", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$DataClassMember;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$DelegateField;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$Error;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$FakeFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$ForwardDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$JavaProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$PluginFile;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$TypeAliasConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$ValueClassMember;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Synthetic extends FirDeclarationOrigin {
        public /* synthetic */ Synthetic(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$DataClassMember;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DataClassMember extends Synthetic {
            public static final DataClassMember INSTANCE = new DataClassMember();

            private DataClassMember() {
                super(null);
            }
        }

        private Synthetic() {
            super(null, false, false, false, 15, null);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$ValueClassMember;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ValueClassMember extends Synthetic {
            public static final ValueClassMember INSTANCE = new ValueClassMember();

            private ValueClassMember() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$JavaProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class JavaProperty extends Synthetic {
            public static final JavaProperty INSTANCE = new JavaProperty();

            private JavaProperty() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$DelegateField;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DelegateField extends Synthetic {
            public static final DelegateField INSTANCE = new DelegateField();

            private DelegateField() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$PluginFile;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class PluginFile extends Synthetic {
            public static final PluginFile INSTANCE = new PluginFile();

            private PluginFile() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$Error;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Error extends Synthetic {
            public static final Error INSTANCE = new Error();

            private Error() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$TypeAliasConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TypeAliasConstructor extends Synthetic {
            public static final TypeAliasConstructor INSTANCE = new TypeAliasConstructor();

            private TypeAliasConstructor() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$FakeFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FakeFunction extends Synthetic {
            public static final FakeFunction INSTANCE = new FakeFunction();

            private FakeFunction() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic$ForwardDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ForwardDeclaration extends Synthetic {
            public static final ForwardDeclaration INSTANCE = new ForwardDeclaration();

            private ForwardDeclaration() {
                super(null);
            }
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$DynamicScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DynamicScope extends FirDeclarationOrigin {
        public static final DynamicScope INSTANCE = new DynamicScope();

        private DynamicScope() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SamConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SamConstructor extends FirDeclarationOrigin {
        public static final SamConstructor INSTANCE = new SamConstructor();

        private SamConstructor() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Enhancement;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Enhancement extends FirDeclarationOrigin {
        public static final Enhancement INSTANCE = new Enhancement();

        private Enhancement() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ImportedFromObjectOrStatic;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImportedFromObjectOrStatic extends FirDeclarationOrigin {
        public static final ImportedFromObjectOrStatic INSTANCE = new ImportedFromObjectOrStatic();

        private ImportedFromObjectOrStatic() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0005\u0006B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "displayName", "", "(Ljava/lang/String;)V", "CallSite", "DeclarationSite", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride$CallSite;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride$DeclarationSite;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class SubstitutionOverride extends FirDeclarationOrigin {
        public /* synthetic */ SubstitutionOverride(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride$DeclarationSite;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DeclarationSite extends SubstitutionOverride {
            public static final DeclarationSite INSTANCE = new DeclarationSite();

            private DeclarationSite() {
                super("SubstitutionOverride(DeclarationSite)", null);
            }
        }

        private SubstitutionOverride(String str) {
            super(str, true, false, false, 12, null);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride$CallSite;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class CallSite extends SubstitutionOverride {
            public static final CallSite INSTANCE = new CallSite();

            private CallSite() {
                super("SubstitutionOverride(CallSite)", null);
            }
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$IntersectionOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntersectionOverride extends FirDeclarationOrigin {
        public static final IntersectionOverride INSTANCE = new IntersectionOverride();

        private IntersectionOverride() {
            super(null, true, false, false, 13, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Delegated;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Delegated extends FirDeclarationOrigin {
        public static final Delegated INSTANCE = new Delegated();

        private Delegated() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$RenamedForOverride;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RenamedForOverride extends FirDeclarationOrigin {
        public static final RenamedForOverride INSTANCE = new RenamedForOverride();

        private RenamedForOverride() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$WrappedIntegerOperator;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WrappedIntegerOperator extends FirDeclarationOrigin {
        public static final WrappedIntegerOperator INSTANCE = new WrappedIntegerOperator();

        private WrappedIntegerOperator() {
            super(null, false, false, false, 15, null);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/declarations/utils/FirScriptCustomizationKind;", "(Lorg/jetbrains/kotlin/fir/declarations/utils/FirScriptCustomizationKind;)V", "getKind", "()Lorg/jetbrains/kotlin/fir/declarations/utils/FirScriptCustomizationKind;", "Default", "ResultProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization$Default;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization$ResultProperty;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ScriptCustomization extends FirDeclarationOrigin {
        private final FirScriptCustomizationKind kind;

        public /* synthetic */ ScriptCustomization(FirScriptCustomizationKind firScriptCustomizationKind, DefaultConstructorMarker defaultConstructorMarker) {
            this(firScriptCustomizationKind);
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization$Default;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Default extends ScriptCustomization {
            public static final Default INSTANCE = new Default();

            private Default() {
                super(FirScriptCustomizationKind.DEFAULT, null);
            }
        }

        private ScriptCustomization(FirScriptCustomizationKind firScriptCustomizationKind) {
            super(null, false, false, false, 15, null);
            this.kind = firScriptCustomizationKind;
        }

        public final FirScriptCustomizationKind getKind() {
            return this.kind;
        }

        /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization$ResultProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$ScriptCustomization;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ResultProperty extends ScriptCustomization {
            public static final ResultProperty INSTANCE = new ResultProperty();

            private ResultProperty() {
                super(FirScriptCustomizationKind.RESULT_PROPERTY, null);
            }
        }
    }

    /* JADX INFO: compiled from: FirDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Plugin;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "(Lorg/jetbrains/kotlin/GeneratedDeclarationKey;)V", "getKey", "()Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Plugin extends FirDeclarationOrigin {
        private final GeneratedDeclarationKey key;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Plugin(GeneratedDeclarationKey key) {
            super("Plugin[" + key + ']', false, true, false, 10, null);
            Intrinsics.checkNotNullParameter(key, "key");
            this.key = key;
        }

        public final GeneratedDeclarationKey getKey() {
            return this.key;
        }
    }

    public String toString() {
        String str = this.displayName;
        if (str != null) {
            return str;
        }
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        return simpleName;
    }
}
