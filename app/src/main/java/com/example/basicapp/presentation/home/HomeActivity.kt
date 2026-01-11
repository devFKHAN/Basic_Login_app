package com.example.basicapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.basicapp.R
import com.example.basicapp.databinding.ActivityHomeBinding
import com.example.basicapp.databinding.ItemQuizDayBinding
import com.example.basicapp.databinding.ItemStatusCardBinding
import com.example.basicapp.databinding.PerfromanceTopicCardBinding
import com.example.basicapp.domain.model.OverallAccuracy
import com.example.basicapp.domain.model.PerformanceByTopic
import com.example.basicapp.domain.model.QuizStreak
import com.example.basicapp.domain.model.Student
import com.example.basicapp.domain.model.TodaySummary
import com.example.basicapp.domain.model.UserData
import com.example.basicapp.domain.model.WeeklyOverview
import com.example.basicapp.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private val model: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        model.userData.observe(this) { data ->
            setupUIWithData(data)
            binding.container.visibility = View.VISIBLE

        }
        binding.logout.setOnClickListener {
            model.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupUIWithData(data: UserData) {
        updateHeader(data.student)
        updateStatCard(data.student)
        updateTodaySummary(data.todaySummary)
        updateWeeklyOverview(data.weeklyOverview)
    }

    private fun updateHeader(student: Student) {
        binding.apply {
            name.text = student.name
            classNo.text = student.className + " Class"
        }
    }

    private fun updateTodaySummary(todaySummary: TodaySummary) {
        binding.todaySummary.apply {
            mood.text = todaySummary.mood
            summaryDescription.text = todaySummary.description
            btnWatchVideo.text = todaySummary.recommendedVideoTitle
        }

    }

    private fun updateWeeklyOverview(weeklyOverview: WeeklyOverview) {
        updateQuizStreak(weeklyOverview.quizStreak)
        updateAccuracyProgress(weeklyOverview.overallAccuracy)
        updatePerformanceTopics(weeklyOverview.performanceByTopic)
    }

    private fun updatePerformanceTopics(performanceByTopic: List<PerformanceByTopic>) {
        binding.weeklyOverview.performanceByTopicContainer.removeAllViews()
        performanceByTopic.forEach { item ->

            val topicView = PerfromanceTopicCardBinding.inflate(
                layoutInflater,
                binding.weeklyOverview.performanceByTopicContainer,
                false
            )


            topicView.topicName.text = item.topic
            topicView.imgTrend.setImageResource(
                if (item.trend == "up") R.drawable.up else R.drawable.down
            )

            binding.weeklyOverview.performanceByTopicContainer.addView(topicView.root)
        }
    }

    private fun updateAccuracyProgress(accuracy: OverallAccuracy) {
        binding.weeklyOverview.accuracyStatusCard.progressAccuracy.progress = accuracy.percentage
        binding.weeklyOverview.accuracyStatusCard.tvAccuracyLabel.text =
            "${accuracy.percentage}% Completed"
    }

    private fun updateQuizStreak(quizStreak: List<QuizStreak>) {

        quizStreak.forEachIndexed { index, item ->

            val dayView: ItemQuizDayBinding = when (index) {
                0 -> binding.weeklyOverview.quizStreakCard.day1
                1 -> binding.weeklyOverview.quizStreakCard.day2
                2 -> binding.weeklyOverview.quizStreakCard.day3
                3 -> binding.weeklyOverview.quizStreakCard.day4
                4 -> binding.weeklyOverview.quizStreakCard.day5
                5 -> binding.weeklyOverview.quizStreakCard.day6
                6 -> binding.weeklyOverview.quizStreakCard.day7
                else -> {
                    throw IndexOutOfBoundsException("Invalid index for quiz streak days")
                }
            }


            dayView.tvDay.text = item.day

            val icon = when {
                item.status == "done" -> R.drawable.streak_ok
                index == 3 -> R.drawable.thursday
                index == 4 -> R.drawable.friday
                index == 5 -> R.drawable.sat
                else -> R.drawable.sat
            }

            dayView.imgStatus.setImageResource(icon)

        }

    }


    private fun updateStatCard(student: com.example.basicapp.domain.model.Student) {
        setupStatusCard(
            binding.availabilityCard,
            R.drawable.icon1frame,
            R.drawable.icon1,
            "Availability",
            student.status
        )
        setupStatusCard(
            binding.quizCard,
            R.drawable.iconframe2,
            R.drawable.icon2,
            "Quiz",
            "${student.quizAttempts} Attempt${if (student.quizAttempts > 1) "s" else ""}"
        )
        setupStatusCard(
            binding.accuracyCard,
            R.drawable.icon3frame,
            R.drawable.icon3,
            "Accuracy",
            student.accuracy
        )
    }


    private fun setupStatusCard(
        card: ItemStatusCardBinding,
        backgroundRes: Int,
        iconRes: Int,
        titleText: String,
        descriptionText: String
    ) {
        card.root.setBackgroundResource(backgroundRes)
        card.icon.setImageResource(iconRes)
        card.title.text = titleText
        card.description.text = descriptionText
    }

}