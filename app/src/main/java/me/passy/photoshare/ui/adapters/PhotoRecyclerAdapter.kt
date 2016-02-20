package me.passy.photoshare.ui.adapters

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.parse.ParseQuery
import com.parse.ParseQueryAdapter
import me.passy.photoshare.R
import me.passy.photoshare.data.parse.Photo
import org.jetbrains.anko.imageURI
import javax.inject.Inject

class PhotoRecyclerAdapter @Inject constructor(val layoutInflater: LayoutInflater) :
        ParseRecyclerQueryAdapter<Photo, PhotoViewHolder>(PhotoQueryAdapterFactory(), true) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder? {
        val view = layoutInflater.inflate(R.layout.view_photo, parent)
        return PhotoViewHolder(itemView = view)
    }

    override fun onBindViewHolder(vh: PhotoViewHolder, position: Int) {
        val photo: Photo = getItem(position)
        vh.image.imageURI = Uri.parse(photo.image.url)
        vh.title.text = photo.title
    }
}

internal class PhotoQueryAdapterFactory : ParseQueryAdapter.QueryFactory<Photo> {
    override fun create(): ParseQuery<Photo>? {
        val query = ParseQuery(Photo::class.java)
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
