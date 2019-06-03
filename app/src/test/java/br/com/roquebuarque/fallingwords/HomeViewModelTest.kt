package br.com.roquebuarque.fallingwords

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.roquebuarque.fallingwords.data.Repository
import br.com.roquebuarque.fallingwords.data.WordResponse
import br.com.roquebuarque.fallingwords.feature.home.domain.RetrieveWords
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeIntent
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeState
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val ruleForLiveData = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testScheduleRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var observer: Observer<HomeState>

    private lateinit var viewModel: HomeViewModel

    private lateinit var homeState: HomeState

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        homeState = HomeState.idle()
        viewModel = HomeViewModel(RetrieveWords(repository))
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `should process from CommonIntent Initial and return HomeState`() {

        whenever(repository.fetchWords()).thenReturn(Observable.just(mockData()))

        viewModel.processIntents(Observable.just(HomeIntent.CommonIntent(HomeIntent.INITIAL)))

        verify(observer).onChanged(
            homeState.copy(
                type = HomeState.START
            )
        )
    }

    @Test
    fun `should process from SelectLevelIntent and return HomeState`() {

        viewModel.processIntents(Observable.just(HomeIntent.SelectLevelIntent(levelId = 1)))

        //Just verifying if my live data is changed, the fields are testing into MapperTest
        verify(observer).onChanged(
            viewModel.state.value
        )
    }

    @Test
    fun `should process from SelectAnswerIntent and return HomeState`() {

        viewModel.processIntents(Observable.just(HomeIntent.SelectAnswerIntent(option = 1)))

        //Just verifying if my live data is changed, the fields are testing into MapperTest
        verify(observer).onChanged(
            viewModel.state.value
        )
    }

    private fun mockData() = listOf(
        WordResponse(eng = "primary school", spa = "escuela primaria"),
        WordResponse(eng = "teacher", spa = "profesor / profesora")
    )

}