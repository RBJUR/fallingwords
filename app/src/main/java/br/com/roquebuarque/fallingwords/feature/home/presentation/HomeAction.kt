package br.com.roquebuarque.fallingwords.feature.home.presentation


sealed class HomeAction {

    object Load : HomeAction()
    object Start : HomeAction()
    object Result : HomeAction()
    object Finish : HomeAction()
    data class SelectLevel(val levelId: Int) : HomeAction()
    data class SelectAnswer(val option:Int) : HomeAction()
}