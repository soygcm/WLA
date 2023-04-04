import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResearcherTest {

    @Mock
    lateinit var mockPatient: Patient

    @Test
    fun `given a patient, when they respond to open questions via the mobile app's home screen, then the responses should be saved for future analysis`() {
        // Given
        val researcher = Researcher()
        val openQuestions = listOf("What is your name?", "What is your age?")
        
        Mockito.`when`(mockPatient.respondToQuestions(openQuestions)).thenReturn(true)
        
        // When
        val isSaved = researcher.saveResponses(mockPatient, openQuestions)
        
        // Then
        assert(isSaved)
        Mockito.verify(mockPatient).respondToQuestions(openQuestions)
        Mockito.verify(mockPatient).saveResponses()
    }
}