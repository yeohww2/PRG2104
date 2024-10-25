package ch.makery.PetShop.model


import scalafx.beans.property.{StringProperty, ObjectProperty}


class Pet(
           petTypeS: String,
           breedS: String,
           genderS: String,
           ageS: Int,
           priceS: Double,
         ) {
  var petType = new StringProperty(petTypeS)
  var breed = new StringProperty(breedS)
  var gender = new StringProperty(genderS)
  var age = new ObjectProperty[Int](this, "age", ageS)
  var price = new ObjectProperty[Double](this, "price", priceS)
}
