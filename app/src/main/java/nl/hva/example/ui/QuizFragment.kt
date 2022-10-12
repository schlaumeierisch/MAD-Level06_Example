package nl.hva.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import nl.hva.example.R
import nl.hva.example.databinding.FragmentQuizBinding
import nl.hva.example.viewmodel.QuizViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeQuiz()
    }

    private fun observeQuiz() {
        viewModel.quiz.observe(viewLifecycleOwner) {
            val quiz = it
            binding.tvQuizQuestion.text = quiz.question

            binding.btnConfirmAnswer.setOnClickListener {
                if (viewModel.isAnswerCorrect(binding.etQuizAnswer.text.toString())) {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_answer_is_correct),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_answer_is_incorrect, quiz.answer),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}