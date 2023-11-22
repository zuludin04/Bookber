package com.app.zuludin.bookber.ui.create.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.zuludin.bookber.databinding.SheetPickImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PickImageSheet : BottomSheetDialogFragment() {

    private lateinit var binding: SheetPickImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SheetPickImageBinding.inflate(inflater, container, false)
        return binding.root
    }
}