package com.reandroid.dex.model;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.EncodedArray;
import com.reandroid.dex.ins.ConstNumber;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.PayloadData;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.value.AnnotationValue;
import com.reandroid.dex.value.ArrayValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.IntValue;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class DexIntegerVisitor extends CombiningIterator<IntegerReference, IntegerReference> {
    public DexIntegerVisitor(DexClassRepository dexClassRepository) {
        super(getEncodedArrayReferences(dexClassRepository), getCodeItemReferences(dexClassRepository));
    }

    private static Iterator<IntegerReference> getEncodedArrayReferences(DexClassRepository dexClassRepository) {
        return new IterableIterator<EncodedArray, IntegerReference>(dexClassRepository.getItems(SectionType.ENCODED_ARRAY)) { // from class: com.reandroid.dex.model.DexIntegerVisitor.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(EncodedArray encodedArray) {
                return DexIntegerVisitor.iterator(encodedArray);
            }
        };
    }

    private static Iterator<IntegerReference> getCodeItemReferences(DexClassRepository dexClassRepository) {
        return new IterableIterator<CodeItem, IntegerReference>(dexClassRepository.getItems(SectionType.CODE)) { // from class: com.reandroid.dex.model.DexIntegerVisitor.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(CodeItem codeItem) {
                return DexIntegerVisitor.iterator(codeItem);
            }
        };
    }

    static Iterator<IntegerReference> iterator(CodeItem codeItem) {
        return new IterableIterator<Ins, IntegerReference>(codeItem.getInstructionList().iterator()) { // from class: com.reandroid.dex.model.DexIntegerVisitor.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(Ins ins) {
                return DexIntegerVisitor.iterator(ins);
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    static Iterator<IntegerReference> iterator(Ins ins) {
        if (ins instanceof ConstNumber) {
            return SingleIterator.of((ConstNumber) ins);
        }
        if (ins instanceof PayloadData) {
            return ((PayloadData) ins).getReferences();
        }
        return EmptyIterator.of();
    }

    static Iterator<IntegerReference> iterator(EncodedArray encodedArray) {
        return new IterableIterator<DexValueBlock<?>, IntegerReference>(encodedArray.iterator()) { // from class: com.reandroid.dex.model.DexIntegerVisitor.4
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(DexValueBlock<?> dexValueBlock) {
                return DexIntegerVisitor.iterator(dexValueBlock);
            }
        };
    }

    static Iterator<IntegerReference> iterator(DexValueBlock<?> dexValueBlock) {
        if (dexValueBlock instanceof IntValue) {
            return SingleIterator.of((IntValue) dexValueBlock);
        }
        if (dexValueBlock instanceof ArrayValue) {
            return iterator((ArrayValue) dexValueBlock);
        }
        if (dexValueBlock instanceof AnnotationValue) {
            return iterator(((AnnotationValue) dexValueBlock).get());
        }
        return EmptyIterator.of();
    }

    private static Iterator<IntegerReference> iterator(ArrayValue arrayValue) {
        return new IterableIterator<DexValueBlock<?>, IntegerReference>(arrayValue.iterator()) { // from class: com.reandroid.dex.model.DexIntegerVisitor.5
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(DexValueBlock<?> dexValueBlock) {
                return DexIntegerVisitor.iterator(dexValueBlock);
            }
        };
    }

    private static Iterator<IntegerReference> iterator(AnnotationItem annotationItem) {
        return new IterableIterator<AnnotationElement, IntegerReference>(annotationItem.iterator()) { // from class: com.reandroid.dex.model.DexIntegerVisitor.6
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IntegerReference> iterator(AnnotationElement annotationElement) {
                return DexIntegerVisitor.iterator(annotationElement.getValue());
            }
        };
    }
}
