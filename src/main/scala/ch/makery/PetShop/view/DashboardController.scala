package ch.makery.PetShop.view

import ch.makery.PetShop.MainApp
import ch.makery.PetShop.model.Pet
import scalafx.scene.control.{Alert, Button, TextField}
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Label, ComboBox, TableView, TableColumn}
import scalafx.collections.ObservableBuffer
import scalafx.Includes._
import scalafx.scene.layout.AnchorPane
import java.text.{ SimpleDateFormat, ParseException }


@sfxml
class DashboardController(
                           // Dashboard buttons
                           private val Close_button: Button,
                           private val Minimize_button: Button,
                           private val Home_button: Button,
                           private val AddPets_button: Button,
                           private val Purchase_button: Button,
                           private val Logout_button: Button,

                           // Home form
                           private val Home_form: AnchorPane,
                           private val Home_income: Label,

                           // AddPets form
                           private val AddPets_form: AnchorPane,
                           private val AddPets_petType: TextField,
                           private val AddPets_breed: TextField,
                           private val AddPets_gender: ComboBox[String],
                           private val AddPets_age: TextField,
                           private val AddPets_price: TextField,
                           private val AddPets_delete: Button,
                           private val AddPets_clear: Button,
                           private val AddPets_add: Button,
                           private val AddPets_tableView: TableView[Pet],
                           private val AddPets_co_petType: TableColumn[Pet, String],
                           private val AddPets_co_breed: TableColumn[Pet, String],
                           private val AddPets_co_gender: TableColumn[Pet, String],
                           private val AddPets_co_age: TableColumn[Pet, Int],
                           private val AddPets_co_price: TableColumn[Pet, Double],

                           // Purchase form
                           private val Purchase_form: AnchorPane,
                           private val Purchase_petType: ComboBox[String],
                           private val Purchase_breed: ComboBox[String],
                           private val Purchase_price: ComboBox[String],
                           private val Purchase_amount: TextField,
                           private val Purchase_total: Label,
                           private val Purchase_balance: Label,
                           private val Purchase_date: TextField,
                           private val Purchase_add: Button,
                           private val Purchase_pay: Button,
                           private val Purchase_clear: Button,
                           private val Purchase_tableView: TableView[Pet],
                           private val Purchase_co_petType: TableColumn[Pet, String],
                           private val Purchase_co_breed: TableColumn[Pet, String],
                           private val Purchase_co_gender: TableColumn[Pet, String],
                           private val Purchase_co_age: TableColumn[Pet, Int],
                           private val Purchase_co_price: TableColumn[Pet, Double],
                         ) {

  // Initialize Table View display contents model
  AddPets_tableView.items = MainApp.PetData

  // Initialize column's cell values
  AddPets_co_petType.cellValueFactory = {
    _.value.petType
  }
  AddPets_co_breed.cellValueFactory = {
    _.value.breed
  }
  AddPets_co_gender.cellValueFactory = {
    _.value.gender
  }
  AddPets_co_age.cellValueFactory = {
    _.value.age
  }
  AddPets_co_price.cellValueFactory = {
    _.value.price
  }

  // Initialize Table View display contents model
  Purchase_tableView.items = MainApp.PetData
  // Initialize column's cell values
  Purchase_co_petType.cellValueFactory = {
    _.value.petType
  }
  Purchase_co_breed.cellValueFactory = {
    _.value.breed
  }
  Purchase_co_gender.cellValueFactory = {
    _.value.gender
  }
  Purchase_co_age.cellValueFactory = {
    _.value.age
  }
  Purchase_co_price.cellValueFactory = {
    _.value.price
  }

  //** Event handler for the close button
  def handleCloseButtonClick(): Unit = {
    MainApp.closeApplication()
  }

  //** Event handler for the minimize button
  def handleMinimizeButtonClick(): Unit = {
    MainApp.minimizeStage
  }

  //** Event handler for the home button
  def handleHomeButtonClick(): Unit = {
    Home_form.setVisible(true)
    AddPets_form.setVisible(false)
    Purchase_form.setVisible(false)
  }

  //** Event handler for the add pets button
  def handleAddPetsButtonClick(): Unit = {
    Home_form.setVisible(false)
    AddPets_form.setVisible(true)
    Purchase_form.setVisible(false)
  }

  //** Event handler for the purchase button
  def handlePurchaseButtonClick(): Unit = {
    Home_form.setVisible(false)
    AddPets_form.setVisible(false)
    Purchase_form.setVisible(true)
  }

  //** Event handler for the logout button
  def handleLogoutButtonClick(): Unit = {
    MainApp.show1LoginPage()
  }

  //** Event handler for the delete button
  def handleDeleteButton(): Unit = {
    val selectedItem = AddPets_tableView.getSelectionModel.getSelectedItem
    if (selectedItem != null) {
      MainApp.PetData.remove(selectedItem)
      AddPets_tableView.selectionModel().clearSelection()
    } else {
      val alert = new Alert(AlertType.Warning) {
        title = "No Selection"
        headerText = "No Pet Selected"
        contentText = "Please select a pet to delete."
      }
      alert.showAndWait()
    }
  }

  //** Event handler for the clear button
  def handleClearButton(): Unit = {
    AddPets_petType.setText("")
    AddPets_breed.setText("")
    AddPets_gender.setValue(null)
    AddPets_age.setText("")
    AddPets_price.setText("")
  }

  //** Event handler for the addPets button
  def handleAddPet(): Unit = {
    val petType = AddPets_petType.text.value.trim.toLowerCase
    val petBreed = AddPets_breed.text.value.trim.toLowerCase

    if (isPetInputValid()) {
      val ageText = AddPets_age.text.value
      val priceText = AddPets_price.text.value

      if (isValidNumber(ageText) && isValidNumber(priceText) && !containsNumbers(petType) && !containsNumbers(petBreed)) {
        val newPet = new Pet(
          petType,
          petBreed,
          AddPets_gender.value.value,
          ageText.toInt,
          priceText.toDouble
        )
        MainApp.PetData.add(newPet)

        val alert = new Alert(AlertType.Information) {
          title = "Pet Added"
          headerText = "Pet successfully added!"
          contentText = s"${newPet.petType.value} has been added to the list."
        }
        alert.showAndWait()
      } else {
        // Show an error alert for invalid age, price, petType, or petBreed
        val alert = new Alert(AlertType.Error) {
          title = "Invalid Input"
          headerText = "Please check your input"
          contentText = "Age and Price should be valid numbers. Pet Type and Pet Breed cannot contain numbers."
        }
        alert.showAndWait()
      }
    } else {
      // Show an error alert if the input is not valid
      val alert = new Alert(AlertType.Error) {
        title = "Invalid Input"
        headerText = "Please check your input"
        contentText = "Ensure all fields are filled correctly."
      }
      alert.showAndWait()
    }
  }

  /** Checks if a string contains numbers */
  def containsNumbers(input: String): Boolean = {
    input.exists(_.isDigit)
  }

  //** Event handler for the Purchase_add button
  def handlePurchaseAdd(): Unit = {
    val selectedPetType = Purchase_petType.value.value
    val selectedBreed = Purchase_breed.value.value
    val selectedPriceText = Purchase_price.value.value

    if (selectedPetType != null && selectedBreed != null && selectedPriceText != null) {
      val selectedPrice = selectedPriceText.toDouble
      val currentTotalText = Purchase_total.text.value.stripPrefix("RM")
      val currentTotal = if (isValidNumber(currentTotalText)) currentTotalText.toDouble else 0.0

      val newTotal = currentTotal + selectedPrice
      Purchase_total.text = f"RM$newTotal%.2f"
    } else {
      // Show an error alert for incomplete input
      val alert = new Alert(AlertType.Error) {
        title = "Invalid Input"
        headerText = "Please check your input"
        contentText = "Please make sure all choices are selected."
      }
      alert.showAndWait()
    }
  }



  //** Event handler for the Purchase_pay button
  def handlePurchasePay(): Unit = {
    val amountText = Purchase_amount.text.value
    val dateText = Purchase_date.text.value

    if (!isValidNumber(amountText) || !isValidDate(dateText)) {
      // Show an error alert for invalid amount and/or date
      val errorAlert = new Alert(AlertType.Error) {
        title = "Invalid Input"
        headerText = "Please check your input"
        if (!isValidNumber(amountText) && !isValidDate(dateText)) {
          contentText = "Amount should be a valid number and date should be in DD/MM/YYYY format."
        } else if (!isValidNumber(amountText)) {
          contentText = "Amount should be a valid number."
        } else {
          contentText = "Date should be in DD/MM/YYYY format."
        }
      }
      errorAlert.showAndWait()
    } else {
      val amount = amountText.toDouble
      val currentTotalText = Purchase_total.text.value.stripPrefix("RM")
      val currentTotal = if (isValidNumber(currentTotalText)) currentTotalText.toDouble else 0.0

      if (amount < currentTotal) {
        // Show an error alert for insufficient amount
        val insufficientAmountAlert = new Alert(AlertType.Error) {
          title = "Insufficient Amount"
          headerText = "Please enter an amount greater than or equal to the current total"
          contentText = s"Current Total: RM$currentTotal"
        }
        insufficientAmountAlert.showAndWait()
      } else {
        // Calculate the balance
        val balance = amount - currentTotal

        // Update the Purchase_balance label
        Purchase_balance.text = f"RM$balance%.2f"

        // Update the Home_income label
        val currentHomeIncomeText = Home_income.text.value.stripPrefix("RM")
        val currentHomeIncome = if (isValidNumber(currentHomeIncomeText)) currentHomeIncomeText.toDouble else 0.0
        val newHomeIncome = currentHomeIncome + currentTotal
        Home_income.text = f"RM$newHomeIncome%.2f"

        // Insert the purchase with date, amount, and balance
        insertPurchase(dateText, amount, balance)


        // Display a confirmation message for successful payment
        val successAlert = new Alert(AlertType.Information) {
          title = "Payment Successful"
          headerText = "Payment successfully processed!"
          contentText = s"Amount: RM${amount} \nDate: $dateText \nBalance: RM${balance}"
        }
        successAlert.showAndWait()
      }
    }
  }

  //** Event handler for the Purchase_clear button
  def handleClearPurchaseFields(): Unit = {
    Purchase_petType.setValue(null)
    Purchase_breed.setValue(null)
    Purchase_price.setValue(null)
    Purchase_total.setText("RM0.00")
    Purchase_amount.clear()
    Purchase_date.clear()
    Purchase_balance.setText("RM0.00")

  }

  ////////////////////////////////////////////////////////////////////
  // Initialize the gender options for the AddPets form
  AddPets_gender.items = ObservableBuffer("Male", "Female")

  Purchase_petType.items = MainApp.PetData.map(_.petType.value).distinct
  Purchase_breed.items = MainApp.PetData.map(_.breed.value).distinct
  Purchase_price.items = MainApp.PetData.map(pet => pet.price.value.toString).distinct

  // Validation check for ensuring that the input fields for adding a new pet in AddPets form
  private def isPetInputValid(): Boolean = {
    !AddPets_petType.text.value.isEmpty &&
      !AddPets_breed.text.value.isEmpty &&
      AddPets_gender.value.value != null &&
      !AddPets_age.text.value.isEmpty &&
      !AddPets_price.text.value.isEmpty
  }


  // Event handler for Purchase_petType ComboBox selection change
  Purchase_petType.onAction = handle {
    updateBreedAndPriceChoices()
  }

  // Event handler for Purchase_breed ComboBox selection change
  Purchase_breed.onAction = handle {
    updatePriceChoices()
  }

  // Function to update choices in Purchase_breed and Purchase_price based on selected Purchase_petType
  private def updateBreedAndPriceChoices(): Unit = {
    val selectedPetType = Purchase_petType.value.value
    val filteredPets = MainApp.PetData.filter(_.petType.value == selectedPetType)

    // Update Purchase_breed choices
    val breedChoices = filteredPets.map(_.breed.value).distinct
    Purchase_breed.items = ObservableBuffer(breedChoices)
    Purchase_breed.setValue(null)

    // Update Purchase_price choices
    updatePriceChoices()
  }

  // Function to update choices in Purchase_price based on selected Purchase_breed
  private def updatePriceChoices(): Unit = {
    val selectedBreed = Purchase_breed.value.value
    val filteredPets = MainApp.PetData.filter(pet =>
      pet.petType.value == Purchase_petType.value.value &&
        pet.breed.value == selectedBreed
    )

    val priceChoices = filteredPets.map(_.price.value.toString).distinct
    Purchase_price.items = ObservableBuffer(priceChoices)
    Purchase_price.setValue(null)
  }

  // Function to check if a string is a valid number
  private def isValidNumber(text: String): Boolean = {
    try {
      text.toDouble
      true
    } catch {
      case _: NumberFormatException => false
    }
  }

  // Method to insert the purchase with date, amount, and balance
  private def insertPurchase(date: String, amount: Double, balance: Double): Unit = {
    println(s"Purchase Date: $date, Amount: $amount, Balance: $balance")
  }

  // Function to check if a string is a valid date in DD/MM/YYYY format
  private def isValidDate(dateText: String): Boolean = {
    try {
      val dateFormat = new SimpleDateFormat("dd/MM/yyyy")
      dateFormat.setLenient(false)
      dateFormat.parse(dateText)
      true
    } catch {
      case _: ParseException => false
    }
  }



}
