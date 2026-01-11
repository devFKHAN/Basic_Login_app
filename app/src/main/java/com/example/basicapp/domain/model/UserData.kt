package com.example.basicapp.domain.model

data class UserData(
    val student: Student,
    val todaySummary: TodaySummary,
    val weeklyOverview: WeeklyOverview
)

data class Student(
    val name: String,
    val className: String,
    val status: String,
    val quizAttempts: Int,
    val accuracy: String
)

data class TodaySummary(
    val mood: String,
    val description: String,
    val recommendedVideoTitle: String,
    val recommendedVideoAction: String,
    val characterImage: String
)

data class WeeklyOverview(
    val quizStreak: List<QuizStreak>,
    val overallAccuracy: OverallAccuracy,
    val performanceByTopic: List<PerformanceByTopic>
)

data class QuizStreak(
    val day: String,
    val status: String
)

data class OverallAccuracy(
    val percentage: Int,
    val label: String
)

data class PerformanceByTopic(
    val topic: String,
    val trend: String
)
