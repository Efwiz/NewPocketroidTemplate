package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: MfvcNodeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField;", "", "()V", "Field", "Property", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField$Field;", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField$Property;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrPropertyOrIrField {
    public /* synthetic */ IrPropertyOrIrField(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField$Property;", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField;", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)V", "getProperty", "()Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Property extends IrPropertyOrIrField {
        private final IrProperty property;

        public static /* synthetic */ Property copy$default(Property property, IrProperty irProperty, int i, Object obj) {
            if ((i & 1) != 0) {
                irProperty = property.property;
            }
            return property.copy(irProperty);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrProperty getProperty() {
            return this.property;
        }

        public final Property copy(IrProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return new Property(property);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Property) && Intrinsics.areEqual(this.property, ((Property) other).property);
        }

        public int hashCode() {
            return this.property.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Property(IrProperty property) {
            super(null);
            Intrinsics.checkNotNullParameter(property, "property");
            this.property = property;
        }

        public final IrProperty getProperty() {
            return this.property;
        }

        public String toString() {
            return DumpIrTreeKt.dump$default(this.property, null, 1, null);
        }
    }

    private IrPropertyOrIrField() {
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField$Field;", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "getField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Field extends IrPropertyOrIrField {
        private final IrField field;

        public static /* synthetic */ Field copy$default(Field field, IrField irField, int i, Object obj) {
            if ((i & 1) != 0) {
                irField = field.field;
            }
            return field.copy(irField);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrField getField() {
            return this.field;
        }

        public final Field copy(IrField field) {
            Intrinsics.checkNotNullParameter(field, "field");
            return new Field(field);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Field) && Intrinsics.areEqual(this.field, ((Field) other).field);
        }

        public int hashCode() {
            return this.field.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(IrField field) {
            super(null);
            Intrinsics.checkNotNullParameter(field, "field");
            this.field = field;
        }

        public final IrField getField() {
            return this.field;
        }

        public String toString() {
            return DumpIrTreeKt.dump$default(this.field, null, 1, null);
        }
    }
}
