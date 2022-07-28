package com.example.a220727_motasemesaefan_nycschools.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a220727_motasemesaefan_nycschools.R
import com.example.a220727_motasemesaefan_nycschools.data.model.ScoreItem
import com.example.a220727_motasemesaefan_nycschools.data.util.UIState
import com.example.a220727_motasemesaefan_nycschools.databinding.FragmentDetailBinding

class DetailFragment : SharedViewModel() {
    private var _binding : FragmentDetailBinding? = null
    private val binding : FragmentDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        configureObserver()
        return  binding.root
    }
    private fun configureObserver() {
        viewModel.scoreLiveData.observe(viewLifecycleOwner) { state ->
            val school = viewModel.currentSchool
            when(state) {
                is UIState.Success<*> -> {
                    val score: ScoreItem? = (state.response as List<ScoreItem>).firstOrNull()
                    if (score == null) {
                        binding.apply {
                            pbScoreLoading.visibility = View.GONE
                            tvScoreLoadingText.visibility = View.GONE
                        }
                    } else {
                        binding.apply {
                            pbScoreLoading.visibility = View.GONE
                            tvScoreLoadingText.visibility = View.GONE
                            tvScoreTakers.text = resources.getString(R.string.score_takers, score.numOfSatTestTakers)
                            tvScoreMath.text = resources.getString(R.string.score_math, score.satMathAvgScore)
                            tvScoreReading.text = resources.getString(R.string.score_reading, score.satCriticalReadingAvgScore)
                            tvScoreWriting.text = resources.getString(R.string.score_writing, score.satWritingAvgScore)
                            llScores.visibility = View.VISIBLE
                        }

                    }
                    binding.apply {
                        tvScoreSchoolName.text = school?.schoolName
                        tvScoreAddress.text = resources.getString(R.string.score_address, school?.primaryAddressLine1)
                        tvScoreEmail.text = resources.getString(R.string.score_email, school?.schoolEmail)
                        tvScoreStudents.text = resources.getString(R.string.score_students, school?.totalStudents)
                        tvScoreOverview.text = resources.getString(R.string.score_overview,school?.overviewParagraph)
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbScoreLoading.visibility = View.GONE
                        tvScoreLoadingText.text = state.exception.message
                    }
                }
                is UIState.Loading -> {
                    viewModel.getScore(viewModel.currentSchool?.dbn ?: "")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}