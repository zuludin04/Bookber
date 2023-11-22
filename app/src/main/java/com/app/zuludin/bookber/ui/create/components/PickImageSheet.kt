package com.app.zuludin.bookber.ui.create.components

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.app.zuludin.bookber.databinding.SheetPickImageBinding
import com.app.zuludin.bookber.util.getImageUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PickImageSheet : BottomSheetDialogFragment() {

    private lateinit var binding: SheetPickImageBinding
    private lateinit var selectedImageListener: SelectedImageListener
    private var selectedImageUri: Uri? = null

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                selectedImageListener.showImage(uri)
                dismiss()
            }
        }

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                selectedImageListener.showImage(selectedImageUri)
                dismiss()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SheetPickImageBinding.inflate(inflater, container, false)

        selectedImageListener = activity as SelectedImageListener
        binding.tvPickGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.tvPickCamera.setOnClickListener {
            selectedImageUri = getImageUri(requireContext())
            launcherCamera.launch(selectedImageUri)
        }

        return binding.root
    }
}

interface SelectedImageListener {
    fun showImage(uri: Uri?)
}