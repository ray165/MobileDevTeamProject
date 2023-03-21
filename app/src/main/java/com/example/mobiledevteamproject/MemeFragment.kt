package com.example.mobiledevteamproject

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //---------------------------- FOR FIRESTORE DATABASE ----------------------------//

        // Get a reference to the Firebase Storage bucket
        val storageRef = FirebaseStorage.getInstance().reference
        // Get a reference to the images folder in the bucket
        val imagesRef = storageRef.child("memes")
        // Download a list of all the images in the folder
        imagesRef.listAll().addOnSuccessListener { listResult ->
            // Get a list of all the image names by mapping each item with it.name
            val imageNames = listResult.items.map { it.name }

            // Randomly select an image name from the list
            val randomImageName = imageNames.random()

            // Get a reference to the randomly selected image
            val randomImageRef = imagesRef.child(randomImageName)

            // Download the randomly selected image
            randomImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                // Display the image in your app
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                view?.findViewById<ImageView>(R.id.imageView_meme_fragment)?.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meme, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MemeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}