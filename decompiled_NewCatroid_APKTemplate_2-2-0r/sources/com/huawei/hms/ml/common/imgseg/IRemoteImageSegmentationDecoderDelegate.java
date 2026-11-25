package com.huawei.hms.ml.common.imgseg;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteImageSegmentationDecoderDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate";

    public static class Default implements IRemoteImageSegmentationDecoderDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
        public ImageSegmentationDetectorParcel detect(Bundle bundle, ImageSegmentationDetectorFrameParcel imageSegmentationDetectorFrameParcel, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
        public int initial(IObjectWrapper iObjectWrapper, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    int destroy() throws RemoteException;

    ImageSegmentationDetectorParcel detect(Bundle bundle, ImageSegmentationDetectorFrameParcel imageSegmentationDetectorFrameParcel, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException;

    int initial(IObjectWrapper iObjectWrapper, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteImageSegmentationDecoderDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initial = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
        }

        public static IRemoteImageSegmentationDecoderDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteImageSegmentationDecoderDelegate)) {
                return (IRemoteImageSegmentationDecoderDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                int iInitial = initial(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? ImageSegmentationOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitial);
                return true;
            }
            if (i != 2) {
                if (i == 3) {
                    parcel.enforceInterface(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                    int iDestroy = destroy();
                    parcel2.writeNoException();
                    parcel2.writeInt(iDestroy);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
            ImageSegmentationDetectorParcel imageSegmentationDetectorParcelDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ImageSegmentationDetectorFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ImageSegmentationOptionsParcel.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            if (imageSegmentationDetectorParcelDetect != null) {
                parcel2.writeInt(1);
                imageSegmentationDetectorParcelDetect.writeToParcel(parcel2, 1);
            } else {
                parcel2.writeInt(0);
            }
            return true;
        }

        private static class Proxy implements IRemoteImageSegmentationDecoderDelegate {
            public static IRemoteImageSegmentationDecoderDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
            public int initial(IObjectWrapper iObjectWrapper, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (imageSegmentationOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        imageSegmentationOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initial(iObjectWrapper, imageSegmentationOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
            public ImageSegmentationDetectorParcel detect(Bundle bundle, ImageSegmentationDetectorFrameParcel imageSegmentationDetectorFrameParcel, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (imageSegmentationDetectorFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        imageSegmentationDetectorFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (imageSegmentationOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        imageSegmentationOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, imageSegmentationDetectorFrameParcel, imageSegmentationOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? ImageSegmentationDetectorParcel.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageSegmentationDecoderDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteImageSegmentationDecoderDelegate iRemoteImageSegmentationDecoderDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteImageSegmentationDecoderDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteImageSegmentationDecoderDelegate;
            return true;
        }

        public static IRemoteImageSegmentationDecoderDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
