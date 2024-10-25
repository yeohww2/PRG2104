package ch.makery.PetShop

import ch.makery.PetShop.view.LoginController
import ch.makery.PetShop.view.DashboardController
import ch.makery.PetShop.model.Pet
import scalafx.application.JFXApp
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.Includes._
import scalafxml.core.{FXMLView, NoDependencyResolver, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer


object MainApp extends JFXApp {

  /* The data as an observable list of Pets.*/
  val PetData = new ObservableBuffer[Pet]()

  PetData += new Pet("Cat", "Siamese", "Female", 2, 30.0)
  PetData += new Pet("Cat", "Persian", "Female", 3, 40.0)
  PetData += new Pet("Cat", "Ragdoll", "Female", 4, 45.0)
  PetData += new Pet("Cat", "Maine Coon", "Male", 2, 35.0)
  PetData += new Pet("Cat", "Sphynx", "Male", 1, 50.0)
  PetData += new Pet("Dog", "Labrador", "Male", 3, 50.0)
  PetData += new Pet("Dog", "Poodle", "Male", 4, 60.0)
  PetData += new Pet("Dog", "German Shepherd", "Male", 5, 70.0)
  PetData += new Pet("Dog", "Golden Retriever", "Female", 2, 55.0)
  PetData += new Pet("Dog", "Beagle", "Female", 1, 40.0)

  // Transform path of 1LoginPage.fxml to URI for resource location.
  val rootResource = getClass.getResource("/main/resource/1LoginPage.fxml")
  // Initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  val stackPaneRoot = loader.load[jfxs.layout.StackPane]()

  // Initialize stage
  stage = new PrimaryStage {
    title = "Pet Shop Management System"
    scene = new Scene {
      root = stackPaneRoot
    }
  }


  // Actions for displaying LoginPage window
  def show1LoginPage(): Unit = {
    val resource = getClass.getResource("/main/resource/1LoginPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val stackPaneRoot = loader.load[jfxs.layout.StackPane]()
    stage.scene().setRoot(stackPaneRoot)
  }

  // Actions for displaying DashboardPage window
  def showDashboardPage(): Unit = {
    val resource = getClass.getResource("/main/resource/2DashboardPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val stackPaneRoot: jfxs.layout.StackPane = loader.load[jfxs.layout.StackPane]()
    stage.scene().setRoot(stackPaneRoot)
  }

  // Action to close the application
  def closeApplication(): Unit = {
    // Properly shut down JavaFX application
    javafx.application.Platform.exit()
    // Exit the entire application, including shutting down the JVM
    Platform.exit
  }
  // Call to display 1LoginPage when the app starts
  show1LoginPage()

  // Actions for minimizing the stage
  def minimizeStage(): Unit = {
    stage.delegate.setIconified(true)
  }




}

