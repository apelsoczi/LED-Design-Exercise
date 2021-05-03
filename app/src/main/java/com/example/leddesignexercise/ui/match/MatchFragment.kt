package com.example.leddesignexercise.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.leddesignexercise.data.model.Color.*
import com.example.leddesignexercise.data.model.Letter.*
import com.example.leddesignexercise.ui.match.MatchViewEvent.*
import com.example.leddesignexercise.ui.match.MatchViewIntent.*
import com.example.leddesignexercise.ui.match.MatchViewState.*
import com.example.leddesignexercise.R
import com.example.leddesignexercise.data.model.Color
import com.example.leddesignexercise.data.model.Letter
import com.example.leddesignexercise.databinding.FragmentMatchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MatchFragment : Fragment() {

    val viewModel: MatchViewModel by viewModels()

    private var _binding: FragmentMatchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonA.setOnClickListener { viewModel.handle(ButtonPress(A)) }
            buttonB.setOnClickListener { viewModel.handle(ButtonPress(B)) }
            buttonC.setOnClickListener { viewModel.handle(ButtonPress(C)) }
        }

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is Initial -> {}
                    is Idle -> renderIdleViewState(it.sequence)
                    is Attempt -> renderAttemptViewState(it)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    NewMatchDialog -> promptNewMatch()
                }
            }
        }

        viewModel.handle(ViewOnCreated)
    }

    private fun renderIdleViewState(sequence: List<Letter>) {
        with(binding) {
            sequenceAnswer.text = sequence.toString()
            ledOne.setBackgroundColor(mapColor(DARK_GREY))
            ledOne.text = ""
            ledTwo.setBackgroundColor(mapColor(DARK_GREY))
            ledTwo.text = ""
            ledThree.setBackgroundColor(mapColor(DARK_GREY))
            ledThree.text = ""
        }
    }

    private fun renderAttemptViewState(viewState: Attempt) {
        with(binding) {
            sequenceAnswer.text = viewState.answer.toString()
            ledOne.setBackgroundColor(mapColor(viewState.firstColor))
            ledTwo.setBackgroundColor(mapColor(viewState.secondColor))
            ledThree.setBackgroundColor(mapColor(viewState.thirdColor))
            val buttons = viewState.buttons
            when (buttons.size) {
                1 -> {
                    ledOne.text = ""
                    ledTwo.text = ""
                    ledThree.text = buttons[0].name
                }
                2 -> {
                    ledOne.text = ""
                    ledTwo.text = buttons[0].name
                    ledThree.text = buttons[1].name
                }
                else -> {
                    ledOne.text = buttons[0].name
                    ledTwo.text = buttons[1].name
                    ledThree.text = buttons[2].name
                }
            }
        }
    }

    private fun mapColor(color: Color): Int {
        when (color) {
            RED -> R.color.red
            ORANGE -> R.color.orange
            GREEN -> R.color.green
            DARK_GREY -> R.color.dark_grey
        }.let {
            return resources.getColor(it)
        }
    }

    private fun promptNewMatch() {
        with(AlertDialog.Builder(requireContext())) {
            setTitle(getString(R.string.new_match_title))
            setMessage(getString(R.string.new_match_message))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.handle(NewMatch)
            }
            setNegativeButton(getString(R.string.no)) { _, _ ->
                requireActivity().finish()
            }
            setCancelable(false)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}