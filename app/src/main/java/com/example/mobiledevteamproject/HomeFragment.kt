package com.example.mobiledevteamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //---------------------------- FOR FIRESTORE STORAGE ----------------------------//

        // Create an instance of FirebaseStorage
        val storage = Firebase.storage
        // Get a reference to the Firebase Storage bucket
        val storageRef = storage.reference
        // Get a reference to the images folder in the bucket
        var imagesRef: StorageReference? = storageRef.child("memes")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFiles()

//        setupViewPager()
    }

//    private fun setupViewPager(){
//
//        memeDataSet = arrayListOf<MemeData>()
//
//        var imageNames: ArrayList<String> = ArrayList()
//
//        imagesRef.listAll().addOnSuccessListener { listResult ->
//
//            imageNames = listResult.items.map { it.name } as ArrayList<String>
//        }
//
//        for (imageName in imageNames) {
//            val resourceId = resources.getIdentifier(imageName, "drawable", requireContext().packageName)
//            memeDataSet.add(MemeData("something", "date", resourceId))
//        }
//
//        val pager = view?.findViewById<ViewPager2>(R.id.viewPager2_home_fragment_memes)
//
//        if (pager != null) {
//            pager.adapter = PagerAdapter((activity as MainActivity))
//        }
//    }

//    inner class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//
//        override fun createFragment(position: Int): Fragment {
//
//            return MemeFragment.newInstance(memeDataSet[position])
//        }
//
//        override fun getItemCount(): Int {
//            return 3
//        }
//    }

    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {

        val imagesRef = Firebase.storage.reference

        val images = imagesRef.child("memes/").listAll().await()
        val imageUrls = mutableListOf<String>()
        for (image in images.items) {
            val url = image.downloadUrl.await()
            imageUrls.add(url.toString())
        }
        withContext(Dispatchers.Main) {
            val memeAdapter = MemeAdapter(imageUrls)
            val pager = view?.findViewById<ViewPager2>(R.id.viewPager2_home_fragment_memes)
            pager?.apply {
                adapter = memeAdapter
                val layoutManager = LinearLayoutManager((activity as MainActivity))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}