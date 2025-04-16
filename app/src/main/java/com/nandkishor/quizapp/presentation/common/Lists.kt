package com.nandkishor.quizapp.presentation.common

object Lists {
    val amounts = (1..50).map { it.toString() }

    val categories = listOf(
        "Any Category" to 0,
        "General Knowledge" to 9,
        "Entertainment: Books" to 10,
        "Entertainment: Films" to 11,
        "Entertainment: Musics" to 12,
        "Entertainment: Musical & Theaters" to 13,
        "Entertainment: Television" to 14,
        "Entertainment: Video Games" to 15,
        "Entertainment: Boards Games" to 16,
        "Science & Nature" to 17,
        "Science: Computers" to 18,
        "Science: Mathematics" to 19,
        "Mythology" to 20,
        "Sports" to 21,
        "Geography" to 22,
        "History" to 23,
        "Politics" to 24,
        "Art" to 25,
        "Celebrities" to 26,
        "Animals" to 27,
        "Vehicles" to 28,
        "Entertainment: Comics" to 29,
        "Science: Gadgets" to 30,
        "Entertainment: Japanese Anime & Manga" to 31,
        "Entertainment: Cartoon & Animations" to 32
    )

    val difficulties = listOf("Easy", "Medium", "Hard")

    val type = listOf("Multiple Choice", "True/False")
}

object AdService {
    val appId = "ca-app-pub-3940256099942544~3347511713"
    val adId = "ca-app-pub-3940256099942544/6300978111"
}