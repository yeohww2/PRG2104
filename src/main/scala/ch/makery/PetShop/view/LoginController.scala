package ch.makery.PetShop.view

import ch.makery.PetShop.MainApp
import scalafx.scene.control.{Alert, Button, PasswordField, TextField}
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml



@sfxml
class LoginController(
                       private val UsernameButton: TextField,
                       private val PasswordButton: PasswordField,
                       private val LoginButton: Button,
                       private val CloseButton: Button
                     ) {

  //** Event handler for the login button
  def handleLogin(): Unit = {
    val username = UsernameButton.text.value
    val password = PasswordButton.text.value

    if (username == "admin" && password == "password") {
      // Your login handling code here
      println("Login successful!")
      MainApp.showDashboardPage()

    } else {
      // Show an alert for invalid login
      val alert = new Alert(AlertType.Error) {
        title = "Invalid Login"
        headerText = "Invalid username or password"
        contentText = "Please enter valid credentials."
      }
      alert.showAndWait()
    }
  }

  //** Event handler for the close button
  def handleClose(): Unit = {
    MainApp.closeApplication()
  }

}
