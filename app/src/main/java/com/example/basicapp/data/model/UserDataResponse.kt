package com.example.basicapp.data.model

import com.example.basicapp.domain.model.UserData
import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    val student: Student,
    val todaySummary: TodaySummary,
    val weeklyOverview: WeeklyOverview,
) {
    fun toDomainModel(): UserData {
        return UserData(
            student = com.example.basicapp.domain.model.Student(
                name = student.name,
                className = student.class_field,
                status = student.availability.status,
                quizAttempts = student.quiz.attempts.toInt(),
                accuracy = student.accuracy.current,
            ),
            todaySummary = com.example.basicapp.domain.model.TodaySummary(
                mood = todaySummary.mood,
                description = todaySummary.description,
                recommendedVideoTitle = todaySummary.recommendedVideo.title,
                recommendedVideoAction = todaySummary.recommendedVideo.actionText,
                characterImage = todaySummary.characterImage,
            ),
            weeklyOverview = com.example.basicapp.domain.model.WeeklyOverview(
                quizStreak = weeklyOverview.quizStreak.map {
                    com.example.basicapp.domain.model.QuizStreak(
                        day = it.day,
                        status = it.status,
                    )
                },
                overallAccuracy = com.example.basicapp.domain.model.OverallAccuracy(
                    percentage = weeklyOverview.overallAccuracy.percentage.toInt(),
                    label = weeklyOverview.overallAccuracy.label,
                ),
                performanceByTopic = weeklyOverview.performanceByTopic.map {
                    com.example.basicapp.domain.model.PerformanceByTopic(
                        topic = it.topic,
                        trend = it.trend,
                    )
                }
            )
        )
    }
}

data class Student(
    val name: String,
    @SerializedName("class")
    val class_field: String,
    val availability: Availability,
    val quiz: Quiz,
    val accuracy: Accuracy,
)

data class Availability(
    val status: String,
)

data class Quiz(
    val attempts: Long,
)

data class Accuracy(
    val current: String,
)

data class TodaySummary(
    val mood: String,
    val description: String,
    val recommendedVideo: RecommendedVideo,
    val characterImage: String,
)

data class RecommendedVideo(
    val title: String,
    val actionText: String,
)

data class WeeklyOverview(
    val quizStreak: List<QuizStreak>,
    val overallAccuracy: OverallAccuracy,
    val performanceByTopic: List<PerformanceByTopic>,
)

data class QuizStreak(
    val day: String,
    val status: String,
)

data class OverallAccuracy(
    val percentage: Long,
    val label: String,
)

data class PerformanceByTopic(
    val topic: String,
    val trend: String,
)

