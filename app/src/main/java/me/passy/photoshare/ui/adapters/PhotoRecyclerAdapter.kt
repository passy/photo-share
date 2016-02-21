package me.passy.photoshare.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.parse.ParseQuery
import com.parse.ParseQueryAdapter
import me.passy.photoshare.R
import me.passy.photoshare.data.parse.Photo
import javax.inject.Inject

class PhotoRecyclerAdapter @Inject constructor(
        val layoutInflater: LayoutInflater,
        val glideManager: RequestManager) :
        ParseRecyclerQueryAdapter<Photo, PhotoViewHolder>(PhotoQueryAdapterFactory()) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder? {
        val view = layoutInflater.inflate(R.layout.view_photo, parent, false)
        return PhotoViewHolder(itemView = view)
    }

    override fun onBindViewHolder(vh: PhotoViewHolder, position: Int) {
        val photo: Photo = getItem(position)
        glideManager
                .load(photo.image.url)
                .centerCrop()
                .into(vh.image)
        vh.title.text = photo.title
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).objectId.hashCode().toLong()
    }
}

internal class PhotoQueryAdapterFactory : ParseQueryAdapter.QueryFactory<Photo> {
    override fun create(): ParseQuery<Photo>? {
        val query = ParseQuery<Photo>("Photo")
        query.include("user")
        query.whereExists("image")
        return query
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @Bind(R.id.photo_title)
    lateinit var title: TextView

    @Bind(R.id.photo_image)
    lateinit var image: ImageView

    init {
        ButterKnife.bind(this, itemView)
    }
}
