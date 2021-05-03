package com.example.leddesignexercise

import com.example.leddesignexercise.data.model.Letter.*
import com.example.leddesignexercise.ui.match.MatchViewEvent.*
import com.example.leddesignexercise.ui.match.MatchViewIntent.*
import com.example.leddesignexercise.ui.match.MatchViewState.*
import com.example.leddesignexercise.data.Match
import com.example.leddesignexercise.data.MatchRepository
import com.example.leddesignexercise.ui.match.MatchViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MatchViewModelTest {

    @MockK
    private lateinit var viewModel: MatchViewModel

    val repository = mockk<MatchRepository>() {
        every { create() } returns Unit
    }

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = MatchViewModel(repository)
    }

    @Test
    fun `loadMatch idle viewState`() = runBlocking {
        val match = Match(listOf(A, B, A))
        every { repository.match } returns match

        viewModel.handle(ViewOnCreated)

        assert(repository.match != null)
        assert(viewModel.state.value is Idle)
    }

    @Test
    fun `loadMatch button press attempt viewState`() {
        val match = Match(listOf(A, B, A))
        match.handleButtonPress(A)

        every { repository.match } returns match

        viewModel.handle(ViewOnCreated)

        assert(repository.match != null)
        assert(viewModel.state.value is Attempt)
    }

    @Test
    fun `pressButton updates viewState`() = runBlocking {
        every { repository.match } returns Match(listOf(A, B, A))

        viewModel.handle(ViewOnCreated)
        assert(viewModel.state.value is Idle)

        viewModel.handle(ButtonPress(A))

        assert(viewModel.state.value is Attempt)
    }

    @Test
    fun `pressButton match correct dialog prompt`() = runBlockingTest(dispatcher) {
        every { repository.match } returns Match(listOf(A, B, A))

        viewModel.handle(ViewOnCreated)

        val actual = async {
            viewModel.events.take(1).single()
        }

        viewModel.handle(ButtonPress(A))
        viewModel.handle(ButtonPress(B))


        viewModel.handle(ButtonPress(A))

        assert(actual.await() is NewMatchDialog)
    }

    @Test
    fun `newMatch idle viewState`() {
        val match = Match(listOf(A, B, A))

        every { repository.delete() } just Runs
        every { repository.match } returns match

        viewModel.handle(NewMatch)

        assert(viewModel.state.value is Idle)
    }

}