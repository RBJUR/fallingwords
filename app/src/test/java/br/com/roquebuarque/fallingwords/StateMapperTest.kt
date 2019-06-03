package br.com.roquebuarque.fallingwords

import br.com.roquebuarque.fallingwords.data.WordResponse
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeState
import br.com.roquebuarque.fallingwords.feature.home.presentation.StateMapper
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before

import org.junit.Test

class StateMapperTest {

    private lateinit var previousState: HomeState

    @Before
    fun setup() {
        previousState = HomeState.idle()
    }

    @Test
    fun `should map from HomeResult Failure to HomeState`() {
        val error = IllegalArgumentException("something went wrong")
        val param = HomeResult.Failure(
            error = error
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(error = result.error)
        assertThat(result).isEqualTo(previousState)
    }

    @Test
    fun `should map from HomeResult Success LOAD to HomeState`() {
        val param = HomeResult.Success(
            type = HomeResult.LOAD,
            data = mockData()
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(
            type = HomeState.LEVEL,
            data = result.data
        )

        assertThat(result).isEqualTo(previousState)
    }


    @Test
    fun `should map from HomeResult Success START to HomeState`() {
        val param = HomeResult.Success(
            type = HomeResult.START
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(
            type = HomeState.START
        )

        assertThat(result).isEqualTo(previousState)
    }

   @Test
    fun `should map from HomeResult Success RUNNING_GAME to HomeState`() {
        val param = HomeResult.Success(
            type = HomeResult.RUNNING_GAME,
            time = 11,
            size = 2
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(
            type = HomeState.RUNNING,
            time = result.time,
            size = result.size,
            data = previousState.data.take(previousState.data.size/result.size)
        )

        assertThat(result).isEqualTo(previousState)
    }

    @Test
    fun `should map from HomeResult Success NEXT to HomeState`() {
        val param = HomeResult.Success(
            type = HomeResult.NEXT
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(type = if (previousState.index < previousState.data.size) HomeState.RUNNING else HomeState.FINISH)

        assertThat(result).isEqualTo(previousState)
    }

    @Test
    fun `should map from HomeResult Success FINISH to HomeState`() {
        val param = HomeResult.Success(
            type = HomeResult.FINISH
        )

        val result = StateMapper(previousState = previousState, result = param)

        previousState = previousState.copy(
            type = HomeState.START,
            countRight = 0,
            countWrong = 0,
            index = 0
        )
        assertThat(result).isEqualTo(previousState)
    }


    @Test
    fun `should map from HomeResult InFlight to HomeState`() {

        val result = StateMapper(previousState = previousState, result = HomeResult.InFlight)

        previousState = previousState.copy(type = HomeState.INFLIGHT)
        assertThat(result).isEqualTo(previousState)
    }


    private fun mockData() = listOf(
        WordResponse(eng = "primary school", spa = "escuela primaria"),
        WordResponse(eng = "teacher", spa = "profesor / profesora")
    )

}