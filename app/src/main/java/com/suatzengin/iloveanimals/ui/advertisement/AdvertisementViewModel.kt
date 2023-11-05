package com.suatzengin.iloveanimals.ui.advertisement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.AdRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.CategoryRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.RecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TitleRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TopRecyclerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val repository: AdvertisementRepository
) : ViewModel() {
    private val _state = MutableStateFlow<List<RecyclerItem>>(emptyList())
    val state = _state.asStateFlow()

    init {
        getAdvertisementList()
    }

    private fun getAdvertisementList() {
        viewModelScope.launch {
            repository.getAdvertisementList().collect { result ->
                when (result) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        val recyclerItemList = fillRecyclerItemList(
                            categories = AdvertisementCategory.entries.toList(),
                            advertisementList = result.data.orEmpty()
                        )

                        _state.emit(recyclerItemList)
                    }
                }
            }
        }
    }

    private fun fillRecyclerItemList(
        categories: List<AdvertisementCategory>,
        advertisementList: List<Advertisement>
    ): List<RecyclerItem> {
        val list = arrayListOf<RecyclerItem>()

        val adRecyclerList = advertisementList.map { advertisement ->
            AdRecyclerItem(
                id = advertisement.id,
                creatorId = advertisement.creatorId,
                title = advertisement.title,
                description = advertisement.description,
                images = advertisement.images,
                address = advertisement.location.address,
                createdAt = advertisement.createdAt
            )
        }

        list.add(TopRecyclerItem())
        list.add(TitleRecyclerItem(title = "Kategori"))
        list.add(CategoryRecyclerItem(categories))
        list.addAll(adRecyclerList)

        return list
    }
}
