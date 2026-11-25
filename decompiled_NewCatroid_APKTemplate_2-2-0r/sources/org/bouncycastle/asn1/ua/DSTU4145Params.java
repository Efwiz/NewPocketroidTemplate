package org.bouncycastle.asn1.ua;

import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class DSTU4145Params extends ASN1Object {
    private static final byte[] DEFAULT_DKE = {Opcodes.OPC_ret, -42, -21, 69, MidiConstants.MIDI_TIME_CODE, 60, 112, Opcodes.OPC_ixor, -128, Opcodes.OPC_wide, Opcodes.OPC_fcmpg, 123, 35, 31, 94, Opcodes.OPC_lreturn, -10, 88, -21, Opcodes.OPC_if_icmple, -64, 55, 41, 29, 56, -39, 107, -16, 37, -54, 78, 23, -8, -23, 114, 13, Opcodes.OPC_ifnull, 21, Opcodes.OPC_getfield, 58, 40, Opcodes.OPC_dcmpl, 95, 11, Opcodes.OPC_instanceof, WasmBinary.ARRAY_TYPE, Opcodes.OPC_if_icmpgt, 100, 56, Opcodes.OPC_putfield, 100, -22, 44, 23, Opcodes.OPC_if_icmpeq, -48, 18, 62, 109, Opcodes.OPC_invokestatic, -6, Opcodes.OPC_multianewarray, 121, 4};
    private byte[] dke;
    private DSTU4145ECBinary ecbinary;
    private ASN1ObjectIdentifier namedCurve;

    public DSTU4145Params(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.dke = DEFAULT_DKE;
        this.namedCurve = aSN1ObjectIdentifier;
    }

    public DSTU4145Params(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        this.dke = DEFAULT_DKE;
        this.namedCurve = aSN1ObjectIdentifier;
        this.dke = Arrays.clone(bArr);
    }

    public DSTU4145Params(DSTU4145ECBinary dSTU4145ECBinary) {
        this.dke = DEFAULT_DKE;
        this.ecbinary = dSTU4145ECBinary;
    }

    public static byte[] getDefaultDKE() {
        return DEFAULT_DKE;
    }

    public static DSTU4145Params getInstance(Object obj) {
        if (obj instanceof DSTU4145Params) {
            return (DSTU4145Params) obj;
        }
        if (obj == null) {
            throw new IllegalArgumentException("object parse error");
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        DSTU4145Params dSTU4145Params = aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier ? new DSTU4145Params(ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0))) : new DSTU4145Params(DSTU4145ECBinary.getInstance(aSN1Sequence.getObjectAt(0)));
        if (aSN1Sequence.size() == 2) {
            byte[] octets = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
            dSTU4145Params.dke = octets;
            if (octets.length != DEFAULT_DKE.length) {
                throw new IllegalArgumentException("object parse error");
            }
        }
        return dSTU4145Params;
    }

    public byte[] getDKE() {
        return this.dke;
    }

    public DSTU4145ECBinary getECBinary() {
        return this.ecbinary;
    }

    public ASN1ObjectIdentifier getNamedCurve() {
        return this.namedCurve;
    }

    public boolean isNamedCurve() {
        return this.namedCurve != null;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Encodable aSN1Encodable = this.namedCurve;
        if (aSN1Encodable == null) {
            aSN1Encodable = this.ecbinary;
        }
        aSN1EncodableVector.add(aSN1Encodable);
        if (!Arrays.areEqual(this.dke, DEFAULT_DKE)) {
            aSN1EncodableVector.add(new DEROctetString(this.dke));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
