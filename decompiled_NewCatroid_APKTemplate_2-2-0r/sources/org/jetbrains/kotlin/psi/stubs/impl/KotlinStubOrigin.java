package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KotlinStubOrigin.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u000b2\u00020\u0001:\u0003\u000b\f\rB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH$R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "", "()V", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "serializeContent", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "Companion", "Facade", "MultiFileFacade", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$Facade;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$MultiFileFacade;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinStubOrigin {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ KotlinStubOrigin(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final KotlinStubOrigin deserialize(StubInputStream stubInputStream) {
        return INSTANCE.deserialize(stubInputStream);
    }

    @JvmStatic
    public static final void serialize(KotlinStubOrigin kotlinStubOrigin, StubOutputStream stubOutputStream) {
        INSTANCE.serialize(kotlinStubOrigin, stubOutputStream);
    }

    protected abstract int getKind();

    protected abstract void serializeContent(StubOutputStream dataStream);

    /* JADX INFO: compiled from: KotlinStubOrigin.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$Companion;", "", "()V", "deserialize", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "origin", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void serialize(KotlinStubOrigin origin, StubOutputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            if (origin == null) {
                dataStream.writeInt(0);
            } else {
                dataStream.writeInt(origin.getKind());
                origin.serializeContent(dataStream);
            }
        }

        @JvmStatic
        public final KotlinStubOrigin deserialize(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            int i = dataStream.readInt();
            if (i == 1) {
                return Facade.INSTANCE.deserializeContent(dataStream);
            }
            if (i != 2) {
                return null;
            }
            return MultiFileFacade.INSTANCE.deserializeContent(dataStream);
        }
    }

    private KotlinStubOrigin() {
    }

    /* JADX INFO: compiled from: KotlinStubOrigin.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\bHÖ\u0001J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$Facade;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "className", "", "(Ljava/lang/String;)V", "getClassName", "()Ljava/lang/String;", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "serializeContent", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "toString", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Facade extends KotlinStubOrigin {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        public static final int KIND = 1;
        private final String className;

        public static /* synthetic */ Facade copy$default(Facade facade, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = facade.className;
            }
            return facade.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getClassName() {
            return this.className;
        }

        public final Facade copy(String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            return new Facade(className);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Facade) && Intrinsics.areEqual(this.className, ((Facade) other).className);
        }

        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin
        protected int getKind() {
            return 1;
        }

        public int hashCode() {
            return this.className.hashCode();
        }

        public String toString() {
            return "Facade(className=" + this.className + ')';
        }

        public final String getClassName() {
            return this.className;
        }

        /* JADX INFO: compiled from: KotlinStubOrigin.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$Facade$Companion;", "", "()V", "KIND", "", "deserializeContent", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$Facade;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Facade deserializeContent(StubInputStream dataStream) throws IOException {
                Intrinsics.checkNotNullParameter(dataStream, "dataStream");
                String nameString = dataStream.readNameString();
                if (nameString == null) {
                    return null;
                }
                return new Facade(nameString);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Facade(String className) {
            super(null);
            Intrinsics.checkNotNullParameter(className, "className");
            this.className = className;
        }

        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin
        protected void serializeContent(StubOutputStream dataStream) throws IOException {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            dataStream.writeName(this.className);
        }
    }

    /* JADX INFO: compiled from: KotlinStubOrigin.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\nHÖ\u0001J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\n8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$MultiFileFacade;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "className", "", "facadeClassName", "(Ljava/lang/String;Ljava/lang/String;)V", "getClassName", "()Ljava/lang/String;", "getFacadeClassName", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "serializeContent", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "toString", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class MultiFileFacade extends KotlinStubOrigin {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        public static final int KIND = 2;
        private final String className;
        private final String facadeClassName;

        public static /* synthetic */ MultiFileFacade copy$default(MultiFileFacade multiFileFacade, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = multiFileFacade.className;
            }
            if ((i & 2) != 0) {
                str2 = multiFileFacade.facadeClassName;
            }
            return multiFileFacade.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getClassName() {
            return this.className;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getFacadeClassName() {
            return this.facadeClassName;
        }

        public final MultiFileFacade copy(String className, String facadeClassName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(facadeClassName, "facadeClassName");
            return new MultiFileFacade(className, facadeClassName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MultiFileFacade)) {
                return false;
            }
            MultiFileFacade multiFileFacade = (MultiFileFacade) other;
            return Intrinsics.areEqual(this.className, multiFileFacade.className) && Intrinsics.areEqual(this.facadeClassName, multiFileFacade.facadeClassName);
        }

        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin
        protected int getKind() {
            return 1;
        }

        public int hashCode() {
            return (this.className.hashCode() * 31) + this.facadeClassName.hashCode();
        }

        public String toString() {
            return "MultiFileFacade(className=" + this.className + ", facadeClassName=" + this.facadeClassName + ')';
        }

        public final String getClassName() {
            return this.className;
        }

        public final String getFacadeClassName() {
            return this.facadeClassName;
        }

        /* JADX INFO: compiled from: KotlinStubOrigin.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$MultiFileFacade$Companion;", "", "()V", "KIND", "", "deserializeContent", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin$MultiFileFacade;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final MultiFileFacade deserializeContent(StubInputStream dataStream) throws IOException {
                String nameString;
                Intrinsics.checkNotNullParameter(dataStream, "dataStream");
                String nameString2 = dataStream.readNameString();
                if (nameString2 == null || (nameString = dataStream.readNameString()) == null) {
                    return null;
                }
                return new MultiFileFacade(nameString2, nameString);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MultiFileFacade(String className, String facadeClassName) {
            super(null);
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(facadeClassName, "facadeClassName");
            this.className = className;
            this.facadeClassName = facadeClassName;
        }

        @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin
        protected void serializeContent(StubOutputStream dataStream) throws IOException {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            dataStream.writeName(this.className);
            dataStream.writeName(this.facadeClassName);
        }
    }
}
