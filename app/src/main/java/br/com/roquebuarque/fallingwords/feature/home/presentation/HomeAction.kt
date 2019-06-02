package br.com.roquebuarque.fallingwords.feature.home.presentation


sealed class HomeAction {

    companion object {
        const val INITIAL = 0
        const val ANSWER_RESULT = 1
        const val NEXT = 2
        const val GAME_RESULT = 3
        const val FINISH = 4
    }
    data class CommonAction(var type:Int = 0) : HomeAction()


    object Load : HomeAction()
    data class SelectLevel(val levelId: Int) : HomeAction()
    data class SelectAnswer(val option:Int) : HomeAction()

}