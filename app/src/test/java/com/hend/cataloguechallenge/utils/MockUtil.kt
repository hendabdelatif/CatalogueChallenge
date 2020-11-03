package com.hend.cataloguechallenge.utils

import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.persistance.entities.Price
import com.hend.cataloguechallenge.persistance.entities.Product

object MockUtil {

  // Mock Category
  fun mockCategory() = Category(
    id = "3131",
    name = "Food",
    description = "",
    products = mockProductList()
  )

  fun mockCategoryList() = listOf(mockCategory())

  // Mock Product
  fun mockBreadProduct() = Product(
    id = "12", categoryId = "3131", name = "Bread",
    description = "", url = "", salePrice = mockPrice())

  // Mock Product
  fun mockBeerProduct() = Product(
    id = "12", categoryId = "3131", name = "Beer",
    description = "", url = "", salePrice = mockPrice())

  fun mockProductList() = listOf(mockBreadProduct(), mockBeerProduct())

  //Mock Price
  private fun mockPrice() = Price(id = 0, amount = "1.2", currency = "EUR")

}
