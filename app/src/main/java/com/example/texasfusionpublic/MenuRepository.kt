package com.example.texasfusionpublic

import com.squareup.square.models.CatalogObject

class MenuRepository() {

    var menuItems: List<MenuItem>? = listOf()
    var currentItem: MenuItem? = null
    fun setMenu(list: List<CatalogObject>?){
        menuItems = parseCatalog(list)
    }
    var cart = mutableListOf<MenuItem>()

    data class MenuItem(
        val category: String,
        val item: CatalogObject,
        val variations: List<CatalogObject>,
        val image: String,
        val modifiers: List<CatalogObject>?,
        val count: Int
    )

    private fun parseCatalog(list: List<CatalogObject>?): List<MenuItem>{
        val catalog: MutableList<MenuItem> = mutableListOf()
        list?.let {
            val categories: MutableMap<String, String> = mutableMapOf()
            val items: MutableMap<String, CatalogObject> = mutableMapOf()
            val itemVars: MutableMap<CatalogObject, String> = mutableMapOf()
            val itemImgs: MutableMap<String, String> = mutableMapOf()
            val itemMods: MutableMap<CatalogObject, String> = mutableMapOf()
            list.forEach {
                when(it.type){
                    "CATEGORY" -> categories.put(it.id, it.categoryData.name)
                    "ITEM" -> items.put(it.id, it)
                    "ITEM_VARIATION" -> itemVars.put(it, it.itemVariationData.itemId)
                    "IMAGE" -> itemImgs.put(it.id, it.imageData.url)
                    "MODIFIER" -> itemMods.put(it, it.modifierData.modifierListId)
                }
            }
            items.forEach{ item ->
                catalog.add(
                    MenuItem(
                        category = categories.getValue(item.value.itemData.categoryId),
                        item = item.value,
                        variations = itemVars.filter { it.value == item.key }.keys.toList(),
                        image = itemImgs.getOrElse(item.value.imageId) { "" },
                        modifiers = itemMods.filter { it.value == item.value.itemData.modifierListInfo?.first()?.modifierListId }.keys.toList(),
                        count = 0
                    )
                )
            }
        }
        return catalog
    }

    fun updateSelectedItem(item: MenuItem){
        currentItem = item
    }

    companion object {
        @Volatile private var instance: MenuRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: MenuRepository()
                        .also { instance = it }
            }
    }
}