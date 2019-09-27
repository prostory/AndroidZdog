package com.zdog.demo.extended

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.zdog.demo.R
import com.zdog.demo.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_basic_shapes.*

class ExtendedShapesFragment : BaseFragment() {
    companion object {
        fun newInstance() = ExtendedShapesFragment()
    }

    private val adapter by lazy { ExtendedShapesAdapter() }

    override fun layoutId() = R.layout.fragment_basic_shapes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        shapeList.adapter = adapter
        shapeList.layoutManager = GridLayoutManager(requireContext(), 3)
        rotate.setOnClickListener {
            adapter.rotate = !adapter.rotate
            renderRotateText(adapter.rotate)
        }
        renderRotateText(adapter.rotate)
    }

    private fun renderRotateText(rotating: Boolean) {
        rotate.text = if (rotating) {
            getString(R.string.text_stop_rotating)
        } else {
            getString(R.string.text_rotate)
        }
    }
}