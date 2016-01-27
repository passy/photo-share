package me.passy.photoshare.ui.params

import android.os.Parcel
import android.os.Parcelable

import java.io.File

public data class PhotoUploadParams(val photoPath: File) : Parcelable {
    constructor(source: Parcel): this(File(source.readString()))

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.photoPath.absolutePath)
    }

    companion object {
        public val EMPTY: PhotoUploadParams = PhotoUploadParams(File(""))

        @JvmField final val CREATOR: Parcelable.Creator<PhotoUploadParams> = object : Parcelable.Creator<PhotoUploadParams> {
            override fun createFromParcel(source: Parcel): PhotoUploadParams {
                return PhotoUploadParams(source)
            }

            override fun newArray(size: Int): Array<PhotoUploadParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
