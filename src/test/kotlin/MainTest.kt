import com.example.calcRPN
import com.example.formRPN
import com.example.toStack
import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {
    // DO not forget JaCoCo

    @Test
    fun sumTest() {
        val input = "2 + 2"

        val rpnForm = listOf("2", "2", "+")
        val answer = 4
        assertEquals(rpnForm, formRPN(input))
        assertEquals(4, calcRPN(rpnForm.toStack()))
    }

    @Test
    fun complicatedTest() {
        val input = "3 ^ 2 - 4 * ( 5 + 5 ) / 2"

        val rpnForm = listOf("3", "2", "^", "4", "5", "5", "+", "2", "/", "*", "-")
        val answer = -11

        assertEquals(rpnForm, formRPN(input))
        assertEquals(answer, calcRPN(rpnForm.toStack()))
    }

    @Test
    fun wrongBraceTest() {
        val input = ") + 2 + 2"

        assertEquals(listOf<String>(), formRPN(input))
        assertEquals(0, calcRPN(formRPN(input).toStack()))
    }

    @Test
    fun wrongBraceInMidTest() {
        val input = "2 + ) + 2"

        assertEquals(listOf<String>(), formRPN(input))
        assertEquals(0, calcRPN(formRPN(input).toStack()))
    }
}
