import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then

class PatientResponseSteps {

    @Given("the patient is on the home screen")
    fun givenPatientOnHomeScreen() {        
        // code to navigate to the home screen
    }

    @When("the patient selects an open question")
    fun whenPatientSelectsOpenQuestion() {
        // code to select an open question
    }

    @When("enters a response")
    fun whenPatientEntersResponse() {
        // code to enter a response
    }

    @When("saves the response")
    fun whenPatientSavesResponse() {
        // code to save the response
    }

    @Then("the response should be saved for future analysis")
    fun thenResponseShouldBeSaved() {
        // code to verify that the response is saved for future analysis
    }
}