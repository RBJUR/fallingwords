package br.com.roquebuarque.fallingwords.feature.home.presentation


object HomeIntentMapper {

    operator fun invoke(homeIntent: HomeIntent) =
        when (homeIntent) {
            is HomeIntent.CommonIntent -> {
                if (homeIntent.intentKey == HomeIntent.START) {
                    HomeAction.Load
                } else {
                    HomeAction.CommonAction(homeIntent.intentKey)
                }
            }
            is HomeIntent.SelectLevelIntent -> HomeAction.SelectLevel(homeIntent.levelId)
            is HomeIntent.SelectAnswerIntent -> HomeAction.SelectAnswer(homeIntent.option)

        }
}