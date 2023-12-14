package com.suatzengin.iloveanimals.ui.advertisementdetail.commentbottomsheet

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.databinding.FragmentAdDetailCommentBottomSheetBinding
import com.suatzengin.iloveanimals.ui.advertisementdetail.AdDetailUiEvent
import com.suatzengin.iloveanimals.ui.advertisementdetail.AdDetailViewModel
import com.suatzengin.iloveanimals.ui.advertisementdetail.AdvertisementDetailFragment
import com.suatzengin.iloveanimals.ui.advertisementdetail.AdvertisementDetailFragment.Companion.COMMENTS_BUNDLE_KEY
import com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.AdDetailCommentAdapter
import com.suatzengin.iloveanimals.util.extension.EMPTY_STRING
import com.suatzengin.iloveanimals.util.extension.getParcelableList
import com.suatzengin.iloveanimals.util.extension.showToast
import com.suatzengin.iloveanimals.util.jwtdecode.JwtDecoder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdDetailCommentBottomSheet :
    BottomSheetDialogFragment(R.layout.fragment_ad_detail_comment_bottom_sheet) {

    private val binding by viewBinding(FragmentAdDetailCommentBottomSheetBinding::bind)

    private val viewModel by viewModels<AdDetailViewModel>(
        ownerProducer = {
            requireParentFragment()
        }
    )

    private var adapter: AdDetailCommentAdapter? = null

    @Inject
    lateinit var ilaAuthHandler: IlaAuthHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        setupRecyclerView()

        setFragmentResultListener()

        btnPostComment.setOnClickListener {
            viewModel.postAdvertisementComment(etComment.editText?.text.toString())
        }

        collectData()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout?>(com.google.android.material.R.id.design_bottom_sheet)

            val behavior = BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams

            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams

            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        }

        return dialog
    }

    private fun setupRecyclerView() {
        val currentUserId = JwtDecoder.decode(ilaAuthHandler.accessToken).userId

        val recyclerView = binding.rvComments

        adapter = AdDetailCommentAdapter(
            currentUserId = currentUserId,
            onItemDeleteClick = ::onCommentDeleteClick
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration())

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), VERTICAL).apply {
                isLastItemDecorated = false
            }

        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setFragmentResultListener() {
        setFragmentResultListener(
            AdvertisementDetailFragment.COMMENTS_REQUEST_KEY
        ) { _, bundle ->
            val commentList = bundle.getParcelableList<AdCommentApiModel>(COMMENTS_BUNDLE_KEY)

            initComments(list = commentList.toList())
        }
    }

    private fun initComments(list: List<AdCommentApiModel>) {
        binding.rvComments.isVisible = list.isNotEmpty()
        binding.tvEmptyState.isVisible = list.isEmpty()

        adapter?.submitList(list)
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        initComments(it.comments)
                    }
                }

                launch {
                    viewModel.uiEvent.collectLatest { event ->
                        when (event) {
                            is AdDetailUiEvent.ShowMessage -> {
                                showToast(event.message)
                            }

                            AdDetailUiEvent.Success -> {
                                clearInput()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onCommentDeleteClick(commentId: String) {
        viewModel.deleteAdvertisementComment(commentId = commentId)
    }

    private fun clearInput() {
        binding.etComment.apply {
            editText?.setText(EMPTY_STRING)
            clearFocus()
        }
    }
}
