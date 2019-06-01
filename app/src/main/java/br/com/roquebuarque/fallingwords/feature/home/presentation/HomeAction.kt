package br.com.roquebuarque.fallingwords.feature.home.presentation

sealed class HomeAction {

    object Load : HomeAction()
    object Start : HomeAction()
    data class SelectLevel(val levelId: Int) : HomeAction()
    data class SelectAnswer(val answerId: Int, val isRight:Boolean) : HomeAction()
}