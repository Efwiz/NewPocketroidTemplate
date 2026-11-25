package org.jetbrains.kotlin.protobuf;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes4.dex */
public abstract class GeneratedMessage extends AbstractMessage implements Serializable {
    protected static boolean alwaysUseFieldBuilders = false;
    private static final long serialVersionUID = 1;

    protected interface BuilderParent {
        void markDirty();
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage> extends MessageOrBuilder {
        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        Message getDefaultInstanceForType();

        <Type> Type getExtension(Extension<MessageType, Type> extension);

        <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i);

        <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension);

        <Type> boolean hasExtension(Extension<MessageType, Type> extension);
    }

    interface ExtensionDescriptorRetriever {
        Descriptors.FieldDescriptor getDescriptor();
    }

    protected abstract FieldAccessorTable internalGetFieldAccessorTable();

    protected void makeExtensionsImmutable() {
    }

    protected abstract Message.Builder newBuilderForType(BuilderParent builderParent);

    protected GeneratedMessage() {
    }

    protected GeneratedMessage(Builder<?> builder) {
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<? extends GeneratedMessage> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public Descriptors.Descriptor getDescriptorForType() {
        return internalGetFieldAccessorTable().descriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
        TreeMap treeMap = new TreeMap();
        for (Descriptors.FieldDescriptor fieldDescriptor : internalGetFieldAccessorTable().descriptor.getFields()) {
            if (fieldDescriptor.isRepeated()) {
                List list = (List) getField(fieldDescriptor);
                if (!list.isEmpty()) {
                    treeMap.put(fieldDescriptor, list);
                }
            } else if (hasField(fieldDescriptor)) {
                treeMap.put(fieldDescriptor, getField(fieldDescriptor));
            }
        }
        return treeMap;
    }

    @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public boolean isInitialized() {
        for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
            if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                return false;
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (fieldDescriptor.isRepeated()) {
                    Iterator it = ((List) getField(fieldDescriptor)).iterator();
                    while (it.hasNext()) {
                        if (!((Message) it.next()).isInitialized()) {
                            return false;
                        }
                    }
                } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap(getAllFieldsMutable());
    }

    @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).has(this);
    }

    @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).get(this);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).has(this);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).get(this);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedCount(this);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
    public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeated(this, i);
    }

    public UnknownFieldSet getUnknownFields() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
        return builder.mergeFieldFrom(i, codedInputStream);
    }

    public static abstract class Builder<BuilderType extends Builder> extends AbstractMessage.Builder<BuilderType> {
        private BuilderParent builderParent;
        private boolean isClean;
        private Builder<BuilderType>.BuilderParentImpl meAsParent;
        private UnknownFieldSet unknownFields;

        protected abstract FieldAccessorTable internalGetFieldAccessorTable();

        protected Builder() {
            this(null);
        }

        protected Builder(BuilderParent builderParent) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent;
        }

        void dispose() {
            this.builderParent = null;
        }

        protected void onBuilt() {
            if (this.builderParent != null) {
                markClean();
            }
        }

        protected void markClean() {
            this.isClean = true;
        }

        protected boolean isClean() {
            return this.isClean;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public BuilderType clear() {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            onChanged();
            return this;
        }

        public Descriptors.Descriptor getDescriptorForType() {
            return internalGetFieldAccessorTable().descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap(getAllFieldsMutable());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
            TreeMap treeMap = new TreeMap();
            for (Descriptors.FieldDescriptor fieldDescriptor : internalGetFieldAccessorTable().descriptor.getFields()) {
                if (fieldDescriptor.isRepeated()) {
                    List list = (List) getField(fieldDescriptor);
                    if (!list.isEmpty()) {
                        treeMap.put(fieldDescriptor, list);
                    }
                } else if (hasField(fieldDescriptor)) {
                    treeMap.put(fieldDescriptor, getField(fieldDescriptor));
                }
            }
            return treeMap;
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public Message.Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).newBuilder();
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getBuilder(this);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return internalGetFieldAccessorTable().getOneof(oneofDescriptor).has(this);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
            return internalGetFieldAccessorTable().getOneof(oneofDescriptor).get(this);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).has(this);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            Object obj = internalGetFieldAccessorTable().getField(fieldDescriptor).get(this);
            return fieldDescriptor.isRepeated() ? Collections.unmodifiableList((List) obj) : obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).set(this, obj);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).clear(this);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            internalGetFieldAccessorTable().getOneof(oneofDescriptor).clear(this);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedCount(this);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeated(this, i);
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).setRepeated(this, i, obj);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).addRepeated(this, obj);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.Message.Builder
        public final BuilderType setUnknownFields(UnknownFieldSet unknownFieldSet) {
            this.unknownFields = unknownFieldSet;
            onChanged();
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public final BuilderType mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFieldSet).build();
            onChanged();
            return this;
        }

        public boolean isInitialized() {
            for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
                if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                    return false;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    if (fieldDescriptor.isRepeated()) {
                        Iterator it = ((List) getField(fieldDescriptor)).iterator();
                        while (it.hasNext()) {
                            if (!((Message) it.next()).isInitialized()) {
                                return false;
                            }
                        }
                    } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return builder.mergeFieldFrom(i, codedInputStream);
        }

        private class BuilderParentImpl implements BuilderParent {
            private BuilderParentImpl() {
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.BuilderParent
            public void markDirty() {
                Builder.this.onChanged();
            }
        }

        protected BuilderParent getParentForChildren() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl();
            }
            return this.meAsParent;
        }

        protected final void onChanged() {
            BuilderParent builderParent;
            if (!this.isClean || (builderParent = this.builderParent) == null) {
                return;
            }
            builderParent.markDirty();
            this.isClean = false;
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage> extends GeneratedMessage implements ExtendableMessageOrBuilder<MessageType> {
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        protected ExtendableMessage(ExtendableBuilder<MessageType, ?> extendableBuilder) {
            super(extendableBuilder);
            this.extensions = extendableBuilder.buildExtensions();
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() == getDescriptorForType()) {
                return;
            }
            String strValueOf = String.valueOf(String.valueOf(extension.getDescriptor().getContainingType().getFullName()));
            String strValueOf2 = String.valueOf(String.valueOf(getDescriptorForType().getFullName()));
            throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 62 + strValueOf2.length()).append("Extension is for type \"").append(strValueOf).append("\" which does not match message type \"").append(strValueOf2).append("\".").toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            Object field = this.extensions.getField(descriptor);
            if (field == null) {
                if (descriptor.isRepeated()) {
                    return (Type) Collections.emptyList();
                }
                if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return (Type) extension.getMessageDefaultInstance();
                }
                return (Type) extension.fromReflectionType(descriptor.getDefaultValue());
            }
            return (Type) extension.fromReflectionType(field);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i) {
            verifyExtensionContainingType(extension);
            return (Type) extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), i));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return MessageReflection.mergeFieldFrom(codedInputStream, builder, extensionRegistryLite, getDescriptorForType(), new MessageReflection.ExtensionAdapter(this.extensions), i);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<Descriptors.FieldDescriptor, Object> next;

            private ExtensionWriter(boolean z) {
                Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> it = ExtendableMessage.this.extensions.iterator();
                this.iter = it;
                if (it.hasNext()) {
                    this.next = it.next();
                }
                this.messageSetWireFormat = z;
            }

            public void writeUntil(int i, CodedOutputStream codedOutputStream) throws IOException {
                while (true) {
                    Map.Entry<Descriptors.FieldDescriptor, Object> entry = this.next;
                    if (entry == null || entry.getKey().getNumber() >= i) {
                        return;
                    }
                    Descriptors.FieldDescriptor key = this.next.getKey();
                    if (this.messageSetWireFormat && key.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !key.isRepeated()) {
                        if (this.next instanceof LazyField.LazyEntry) {
                            codedOutputStream.writeRawMessageSetExtension(key.getNumber(), ((LazyField.LazyEntry) this.next).getField().toByteString());
                        } else {
                            codedOutputStream.writeMessageSetExtension(key.getNumber(), (Message) this.next.getValue());
                        }
                    } else {
                        FieldSet.writeField(key, this.next.getValue(), codedOutputStream);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected ExtendableMessage<MessageType>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        protected Map<Descriptors.FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable();
            allFieldsMutable.putAll(getExtensionFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.hasField(fieldDescriptor);
            }
            return super.hasField(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                Object field = this.extensions.getField(fieldDescriptor);
                if (field != null) {
                    return field;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType());
                }
                return fieldDescriptor.getDefaultValue();
            }
            return super.getField(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedFieldCount(fieldDescriptor);
            }
            return super.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedField(fieldDescriptor, i);
            }
            return super.getRepeatedField(fieldDescriptor, i);
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage, BuilderType extends ExtendableBuilder> extends Builder<BuilderType> implements ExtendableMessageOrBuilder<MessageType> {
        private FieldSet<Descriptors.FieldDescriptor> extensions;

        protected ExtendableBuilder() {
            this.extensions = FieldSet.emptySet();
        }

        protected ExtendableBuilder(BuilderParent builderParent) {
            super(builderParent);
            this.extensions = FieldSet.emptySet();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public BuilderType clear() {
            this.extensions = FieldSet.emptySet();
            return (BuilderType) super.clear();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        private void ensureExtensionsIsMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() == getDescriptorForType()) {
                return;
            }
            String strValueOf = String.valueOf(String.valueOf(extension.getDescriptor().getContainingType().getFullName()));
            String strValueOf2 = String.valueOf(String.valueOf(getDescriptorForType().getFullName()));
            throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 62 + strValueOf2.length()).append("Extension is for type \"").append(strValueOf).append("\" which does not match message type \"").append(strValueOf2).append("\".").toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            Object field = this.extensions.getField(descriptor);
            if (field == null) {
                if (descriptor.isRepeated()) {
                    return (Type) Collections.emptyList();
                }
                if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return (Type) extension.getMessageDefaultInstance();
                }
                return (Type) extension.fromReflectionType(descriptor.getDefaultValue());
            }
            return (Type) extension.fromReflectionType(field);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i) {
            verifyExtensionContainingType(extension);
            return (Type) extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), i));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final <Type> BuilderType setExtension(Extension<MessageType, Type> extension, Type type) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setField(extension.getDescriptor(), extension.toReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, List<Type>> extension, int i, Type type) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(extension.getDescriptor(), i, extension.singularToReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType addExtension(Extension<MessageType, List<Type>> extension, Type type) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(extension.getDescriptor(), extension.singularToReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType clearExtension(Extension<MessageType, ?> extension) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.clearField(extension.getDescriptor());
            onChanged();
            return this;
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldSet<Descriptors.FieldDescriptor> buildExtensions() {
            this.extensions.makeImmutable();
            return this.extensions;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return MessageReflection.mergeFieldFrom(codedInputStream, builder, extensionRegistryLite, getDescriptorForType(), new MessageReflection.BuilderAdapter(this), i);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable();
            allFieldsMutable.putAll(this.extensions.getAllFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                Object field = this.extensions.getField(fieldDescriptor);
                if (field != null) {
                    return field;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType());
                }
                return fieldDescriptor.getDefaultValue();
            }
            return super.getField(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedFieldCount(fieldDescriptor);
            }
            return super.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedField(fieldDescriptor, i);
            }
            return super.getRepeatedField(fieldDescriptor, i);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.hasField(fieldDescriptor);
            }
            return super.hasField(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setField(fieldDescriptor, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.setField(fieldDescriptor, obj);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.clearField(fieldDescriptor);
                onChanged();
                return this;
            }
            return (BuilderType) super.clearField(fieldDescriptor);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setRepeatedField(fieldDescriptor, i, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.setRepeatedField(fieldDescriptor, i, obj);
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
        public BuilderType addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.addRepeatedField(fieldDescriptor, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.addRepeatedField(fieldDescriptor, obj);
        }

        protected final void mergeExtensionFields(ExtendableMessage extendableMessage) {
            ensureExtensionsIsMutable();
            this.extensions.mergeFrom(extendableMessage.extensions);
            onChanged();
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message message, final int i, Class cls, Message message2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: org.jetbrains.kotlin.protobuf.GeneratedMessage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.CachedDescriptorRetriever
            public Descriptors.FieldDescriptor loadDescriptor() {
                return message.getDescriptorForType().getExtensions().get(i);
            }
        }, cls, message2, Extension.ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(Class cls, Message message) {
        return new GeneratedExtension<>(null, cls, message, Extension.ExtensionType.IMMUTABLE);
    }

    private static abstract class CachedDescriptorRetriever implements ExtensionDescriptorRetriever {
        private volatile Descriptors.FieldDescriptor descriptor;

        protected abstract Descriptors.FieldDescriptor loadDescriptor();

        private CachedDescriptorRetriever() {
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtensionDescriptorRetriever
        public Descriptors.FieldDescriptor getDescriptor() {
            if (this.descriptor == null) {
                synchronized (this) {
                    if (this.descriptor == null) {
                        this.descriptor = loadDescriptor();
                    }
                }
            }
            return this.descriptor;
        }
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message message, final String str, Class cls, Message message2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: org.jetbrains.kotlin.protobuf.GeneratedMessage.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.CachedDescriptorRetriever
            protected Descriptors.FieldDescriptor loadDescriptor() {
                return message.getDescriptorForType().findFieldByName(str);
            }
        }, cls, message2, Extension.ExtensionType.MUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(final Class cls, Message message, final String str, final String str2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: org.jetbrains.kotlin.protobuf.GeneratedMessage.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.CachedDescriptorRetriever
            protected Descriptors.FieldDescriptor loadDescriptor() {
                try {
                    return ((Descriptors.FileDescriptor) cls.getClassLoader().loadClass(str).getField("descriptor").get(null)).findExtensionByName(str2);
                } catch (Exception e) {
                    String strValueOf = String.valueOf(String.valueOf(str));
                    throw new RuntimeException(new StringBuilder(strValueOf.length() + 62).append("Cannot load descriptors: ").append(strValueOf).append(" is not a valid descriptor class name").toString(), e);
                }
            }
        }, cls, message, Extension.ExtensionType.MUTABLE);
    }

    public static class GeneratedExtension<ContainingType extends Message, Type> extends Extension<ContainingType, Type> {
        private ExtensionDescriptorRetriever descriptorRetriever;
        private final Method enumGetValueDescriptor;
        private final Method enumValueOf;
        private final Extension.ExtensionType extensionType;
        private final Message messageDefaultInstance;
        private final Class singularType;

        GeneratedExtension(ExtensionDescriptorRetriever extensionDescriptorRetriever, Class cls, Message message, Extension.ExtensionType extensionType) {
            if (Message.class.isAssignableFrom(cls) && !cls.isInstance(message)) {
                String strValueOf = String.valueOf(cls.getName());
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Bad messageDefaultInstance for ".concat(strValueOf) : new String("Bad messageDefaultInstance for "));
            }
            this.descriptorRetriever = extensionDescriptorRetriever;
            this.singularType = cls;
            this.messageDefaultInstance = message;
            if (ProtocolMessageEnum.class.isAssignableFrom(cls)) {
                this.enumValueOf = GeneratedMessage.getMethodOrDie(cls, "valueOf", Descriptors.EnumValueDescriptor.class);
                this.enumGetValueDescriptor = GeneratedMessage.getMethodOrDie(cls, "getValueDescriptor", new Class[0]);
            } else {
                this.enumValueOf = null;
                this.enumGetValueDescriptor = null;
            }
            this.extensionType = extensionType;
        }

        public void internalInit(final Descriptors.FieldDescriptor fieldDescriptor) {
            if (this.descriptorRetriever != null) {
                throw new IllegalStateException("Already initialized.");
            }
            this.descriptorRetriever = new ExtensionDescriptorRetriever() { // from class: org.jetbrains.kotlin.protobuf.GeneratedMessage.GeneratedExtension.1
                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtensionDescriptorRetriever
                public Descriptors.FieldDescriptor getDescriptor() {
                    return fieldDescriptor;
                }
            };
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public Descriptors.FieldDescriptor getDescriptor() {
            ExtensionDescriptorRetriever extensionDescriptorRetriever = this.descriptorRetriever;
            if (extensionDescriptorRetriever == null) {
                throw new IllegalStateException("getDescriptor() called before internalInit()");
            }
            return extensionDescriptorRetriever.getDescriptor();
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public Message getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        protected Extension.ExtensionType getExtensionType() {
            return this.extensionType;
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        protected Object fromReflectionType(Object obj) {
            Descriptors.FieldDescriptor descriptor = getDescriptor();
            if (descriptor.isRepeated()) {
                if (descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE && descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.ENUM) {
                    return obj;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    arrayList.add(singularFromReflectionType(it.next()));
                }
                return arrayList;
            }
            return singularFromReflectionType(obj);
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        protected Object singularFromReflectionType(Object obj) {
            int i = AnonymousClass4.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[getDescriptor().getJavaType().ordinal()];
            if (i != 1) {
                return i != 2 ? obj : GeneratedMessage.invokeOrDie(this.enumValueOf, null, (Descriptors.EnumValueDescriptor) obj);
            }
            return this.singularType.isInstance(obj) ? obj : this.messageDefaultInstance.newBuilderForType().mergeFrom((Message) obj).build();
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        protected Object toReflectionType(Object obj) {
            Descriptors.FieldDescriptor descriptor = getDescriptor();
            if (descriptor.isRepeated()) {
                if (descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.ENUM) {
                    return obj;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    arrayList.add(singularToReflectionType(it.next()));
                }
                return arrayList;
            }
            return singularToReflectionType(obj);
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        protected Object singularToReflectionType(Object obj) {
            return AnonymousClass4.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[getDescriptor().getJavaType().ordinal()] != 2 ? obj : GeneratedMessage.invokeOrDie(this.enumGetValueDescriptor, obj, new Object[0]);
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public int getNumber() {
            return getDescriptor().getNumber();
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public WireFormat.FieldType getLiteType() {
            return getDescriptor().getLiteType();
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public boolean isRepeated() {
            return getDescriptor().isRepeated();
        }

        @Override // org.jetbrains.kotlin.protobuf.Extension
        public Type getDefaultValue() {
            if (isRepeated()) {
                return (Type) Collections.emptyList();
            }
            if (getDescriptor().getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return (Type) this.messageDefaultInstance;
            }
            return (Type) singularFromReflectionType(getDescriptor().getDefaultValue());
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.protobuf.GeneratedMessage$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.JavaType.values().length];
            $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.JavaType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[Descriptors.FieldDescriptor.JavaType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Method getMethodOrDie(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            String strValueOf = String.valueOf(String.valueOf(cls.getName()));
            String strValueOf2 = String.valueOf(String.valueOf(str));
            throw new RuntimeException(new StringBuilder(strValueOf.length() + 45 + strValueOf2.length()).append("Generated message class \"").append(strValueOf).append("\" missing method \"").append(strValueOf2).append("\".").toString(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object invokeOrDie(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static final class FieldAccessorTable {
        private String[] camelCaseNames;
        private final Descriptors.Descriptor descriptor;
        private final FieldAccessor[] fields;
        private volatile boolean initialized;
        private final OneofAccessor[] oneofs;

        private interface FieldAccessor {
            void addRepeated(Builder builder, Object obj);

            void clear(Builder builder);

            Object get(Builder builder);

            Object get(GeneratedMessage generatedMessage);

            Message.Builder getBuilder(Builder builder);

            Object getRepeated(Builder builder, int i);

            Object getRepeated(GeneratedMessage generatedMessage, int i);

            int getRepeatedCount(Builder builder);

            int getRepeatedCount(GeneratedMessage generatedMessage);

            boolean has(Builder builder);

            boolean has(GeneratedMessage generatedMessage);

            Message.Builder newBuilder();

            void set(Builder builder, Object obj);

            void setRepeated(Builder builder, int i, Object obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean supportFieldPresence(Descriptors.FileDescriptor fileDescriptor) {
            return true;
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
            this(descriptor, strArr);
            ensureFieldAccessorsInitialized(cls, cls2);
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr) {
            this.descriptor = descriptor;
            this.camelCaseNames = strArr;
            this.fields = new FieldAccessor[descriptor.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor.getOneofs().size()];
            this.initialized = false;
        }

        public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
            if (this.initialized) {
                return this;
            }
            synchronized (this) {
                if (this.initialized) {
                    return this;
                }
                int length = this.fields.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Descriptors.FieldDescriptor fieldDescriptor = this.descriptor.getFields().get(i);
                    String str = fieldDescriptor.getContainingOneof() != null ? this.camelCaseNames[fieldDescriptor.getContainingOneof().getIndex() + length] : null;
                    if (fieldDescriptor.isRepeated()) {
                        if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                            this.fields[i] = new RepeatedMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                            this.fields[i] = new RepeatedEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                        } else {
                            this.fields[i] = new RepeatedFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                        }
                    } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        this.fields[i] = new SingularMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2, str);
                    } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                        this.fields[i] = new SingularEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2, str);
                    } else {
                        this.fields[i] = new SingularFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2, str);
                    }
                    i++;
                }
                int length2 = this.oneofs.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.oneofs[i2] = new OneofAccessor(this.descriptor, this.camelCaseNames[i2 + length], cls, cls2);
                }
                this.initialized = true;
                this.camelCaseNames = null;
                return this;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldAccessor getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
            if (fieldDescriptor.isExtension()) {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
            return this.fields[fieldDescriptor.getIndex()];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public OneofAccessor getOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            if (oneofDescriptor.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
            return this.oneofs[oneofDescriptor.getIndex()];
        }

        private static class OneofAccessor {
            private final Method caseMethod;
            private final Method caseMethodBuilder;
            private final Method clearMethod;
            private final Descriptors.Descriptor descriptor;

            OneofAccessor(Descriptors.Descriptor descriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                this.descriptor = descriptor;
                String strValueOf = String.valueOf(String.valueOf(str));
                this.caseMethod = GeneratedMessage.getMethodOrDie(cls, new StringBuilder(strValueOf.length() + 7).append("get").append(strValueOf).append("Case").toString(), new Class[0]);
                String strValueOf2 = String.valueOf(String.valueOf(str));
                this.caseMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, new StringBuilder(strValueOf2.length() + 7).append("get").append(strValueOf2).append("Case").toString(), new Class[0]);
                String strValueOf3 = String.valueOf(str);
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf3.length() != 0 ? LineReader.CLEAR.concat(strValueOf3) : new String(LineReader.CLEAR), new Class[0]);
            }

            public boolean has(GeneratedMessage generatedMessage) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber() != 0;
            }

            public boolean has(Builder builder) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() != 0;
            }

            public Descriptors.FieldDescriptor get(GeneratedMessage generatedMessage) {
                int number = ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber();
                if (number > 0) {
                    return this.descriptor.findFieldByNumber(number);
                }
                return null;
            }

            public Descriptors.FieldDescriptor get(Builder builder) {
                int number = ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                if (number > 0) {
                    return this.descriptor.findFieldByNumber(number);
                }
                return null;
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }
        }

        private static class SingularFieldAccessor implements FieldAccessor {
            protected final Method caseMethod;
            protected final Method caseMethodBuilder;
            protected final Method clearMethod;
            protected final Descriptors.FieldDescriptor field;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final boolean hasHasMethod;
            protected final Method hasMethod;
            protected final Method hasMethodBuilder;
            protected final boolean isOneofField;
            protected final Method setMethod;
            protected final Class<?> type;

            SingularFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                Method methodOrDie;
                Method methodOrDie2;
                Method methodOrDie3;
                this.field = fieldDescriptor;
                boolean z = fieldDescriptor.getContainingOneof() != null;
                this.isOneofField = z;
                boolean z2 = FieldAccessorTable.supportFieldPresence(fieldDescriptor.getFile()) || (!z && fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE);
                this.hasHasMethod = z2;
                String strValueOf = String.valueOf(str);
                Method methodOrDie4 = GeneratedMessage.getMethodOrDie(cls, strValueOf.length() != 0 ? "get".concat(strValueOf) : new String("get"), new Class[0]);
                this.getMethod = methodOrDie4;
                String strValueOf2 = String.valueOf(str);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, strValueOf2.length() != 0 ? "get".concat(strValueOf2) : new String("get"), new Class[0]);
                Class<?> returnType = methodOrDie4.getReturnType();
                this.type = returnType;
                String strValueOf3 = String.valueOf(str);
                this.setMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf3.length() != 0 ? FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.concat(strValueOf3) : new String(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), returnType);
                Method methodOrDie5 = null;
                if (z2) {
                    String strValueOf4 = String.valueOf(str);
                    methodOrDie = GeneratedMessage.getMethodOrDie(cls, strValueOf4.length() != 0 ? "has".concat(strValueOf4) : new String("has"), new Class[0]);
                } else {
                    methodOrDie = null;
                }
                this.hasMethod = methodOrDie;
                if (z2) {
                    String strValueOf5 = String.valueOf(str);
                    methodOrDie2 = GeneratedMessage.getMethodOrDie(cls2, strValueOf5.length() != 0 ? "has".concat(strValueOf5) : new String("has"), new Class[0]);
                } else {
                    methodOrDie2 = null;
                }
                this.hasMethodBuilder = methodOrDie2;
                String strValueOf6 = String.valueOf(str);
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf6.length() != 0 ? LineReader.CLEAR.concat(strValueOf6) : new String(LineReader.CLEAR), new Class[0]);
                if (z) {
                    String strValueOf7 = String.valueOf(String.valueOf(str2));
                    methodOrDie3 = GeneratedMessage.getMethodOrDie(cls, new StringBuilder(strValueOf7.length() + 7).append("get").append(strValueOf7).append("Case").toString(), new Class[0]);
                } else {
                    methodOrDie3 = null;
                }
                this.caseMethod = methodOrDie3;
                if (z) {
                    String strValueOf8 = String.valueOf(String.valueOf(str2));
                    methodOrDie5 = GeneratedMessage.getMethodOrDie(cls2, new StringBuilder(strValueOf8.length() + 7).append("get").append(strValueOf8).append("Case").toString(), new Class[0]);
                }
                this.caseMethodBuilder = methodOrDie5;
            }

            private int getOneofFieldNumber(GeneratedMessage generatedMessage) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber();
            }

            private int getOneofFieldNumber(Builder builder) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getMethod, generatedMessage, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                GeneratedMessage.invokeOrDie(this.setMethod, builder, obj);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessage generatedMessage) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethod, generatedMessage, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    return getOneofFieldNumber(generatedMessage) == this.field.getNumber();
                }
                return !get(generatedMessage).equals(this.field.getDefaultValue());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(Builder builder) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    return getOneofFieldNumber(builder) == this.field.getNumber();
                }
                return !get(builder).equals(this.field.getDefaultValue());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessage generatedMessage) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
        }

        private static class RepeatedFieldAccessor implements FieldAccessor {
            protected final Method addRepeatedMethod;
            protected final Method clearMethod;
            protected final Method getCountMethod;
            protected final Method getCountMethodBuilder;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final Method getRepeatedMethod;
            protected final Method getRepeatedMethodBuilder;
            protected final Method setRepeatedMethod;
            protected final Class type;

            RepeatedFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                String strValueOf = String.valueOf(String.valueOf(str));
                this.getMethod = GeneratedMessage.getMethodOrDie(cls, new StringBuilder(strValueOf.length() + 7).append("get").append(strValueOf).append("List").toString(), new Class[0]);
                String strValueOf2 = String.valueOf(String.valueOf(str));
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, new StringBuilder(strValueOf2.length() + 7).append("get").append(strValueOf2).append("List").toString(), new Class[0]);
                String strValueOf3 = String.valueOf(str);
                Method methodOrDie = GeneratedMessage.getMethodOrDie(cls, strValueOf3.length() != 0 ? "get".concat(strValueOf3) : new String("get"), Integer.TYPE);
                this.getRepeatedMethod = methodOrDie;
                String strValueOf4 = String.valueOf(str);
                this.getRepeatedMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, strValueOf4.length() != 0 ? "get".concat(strValueOf4) : new String("get"), Integer.TYPE);
                Class<?> returnType = methodOrDie.getReturnType();
                this.type = returnType;
                String strValueOf5 = String.valueOf(str);
                this.setRepeatedMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf5.length() != 0 ? FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.concat(strValueOf5) : new String(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), Integer.TYPE, returnType);
                String strValueOf6 = String.valueOf(str);
                this.addRepeatedMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf6.length() != 0 ? ProductAction.ACTION_ADD.concat(strValueOf6) : new String(ProductAction.ACTION_ADD), returnType);
                String strValueOf7 = String.valueOf(String.valueOf(str));
                this.getCountMethod = GeneratedMessage.getMethodOrDie(cls, new StringBuilder(strValueOf7.length() + 8).append("get").append(strValueOf7).append("Count").toString(), new Class[0]);
                String strValueOf8 = String.valueOf(String.valueOf(str));
                this.getCountMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, new StringBuilder(strValueOf8.length() + 8).append("get").append(strValueOf8).append("Count").toString(), new Class[0]);
                String strValueOf9 = String.valueOf(str);
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, strValueOf9.length() != 0 ? LineReader.CLEAR.concat(strValueOf9) : new String(LineReader.CLEAR), new Class[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getMethod, generatedMessage, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                clear(builder);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    addRepeated(builder, it.next());
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethod, generatedMessage, Integer.valueOf(i));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethodBuilder, builder, Integer.valueOf(i));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                GeneratedMessage.invokeOrDie(this.setRepeatedMethod, builder, Integer.valueOf(i), obj);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                GeneratedMessage.invokeOrDie(this.addRepeatedMethod, builder, obj);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessage generatedMessage) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessage generatedMessage) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethod, generatedMessage, new Object[0])).intValue();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder builder) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
        }

        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor {
            private Method getValueDescriptorMethod;
            private Method valueOfMethod;

            SingularEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(generatedMessage), new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                super.set(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
            }
        }

        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor {
            private final Method getValueDescriptorMethod;
            private final Method valueOfMethod;

            RepeatedEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) super.get(generatedMessage)).iterator();
                while (it.hasNext()) {
                    arrayList.add(GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, it.next(), new Object[0]));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) super.get(builder)).iterator();
                while (it.hasNext()) {
                    arrayList.add(GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, it.next(), new Object[0]));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(generatedMessage, i), new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, i), new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                super.setRepeated(builder, i, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                super.addRepeated(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
            }
        }

        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor {
            private final Method getBuilderMethodBuilder;
            private final Method newBuilderMethod;

            SingularMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
                String strValueOf = String.valueOf(String.valueOf(str));
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, new StringBuilder(strValueOf.length() + 10).append("get").append(strValueOf).append("Builder").toString(), new Class[0]);
            }

            private Object coerceType(Object obj) {
                return this.type.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).buildPartial();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                super.set(builder, coerceType(obj));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }
        }

        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
            private final Method newBuilderMethod;

            RepeatedMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
            }

            private Object coerceType(Object obj) {
                return this.type.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).build();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                super.setRepeated(builder, i, coerceType(obj));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                super.addRepeated(builder, coerceType(obj));
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, org.jetbrains.kotlin.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }
        }
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new GeneratedMessageLite.SerializedForm(this);
    }
}
