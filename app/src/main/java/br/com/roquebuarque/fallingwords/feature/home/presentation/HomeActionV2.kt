package br.com.roquebuarque.fallingwords.feature.home.presentation


sealed class HomeActionV2 {

    companion object {
        const val INITIAL = 0
        const val ANSWER_RESULT = 1
        const val NEXT = 2
        const val GAME_RESULT = 3
        const val FINISH = 4
    }
    data class CommonAction(var type:Int = 0) : HomeActionV2()


    object Load : HomeActionV2()
    data class SelectLevel(val levelId: Int) : HomeActionV2()
    data class SelectAnswer(val option:Int) : HomeActionV2()

}