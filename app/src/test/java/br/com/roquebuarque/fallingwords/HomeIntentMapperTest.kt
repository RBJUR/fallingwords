package br.com.roquebuarque.fallingwords

import br.com.roquebuarque.fallingwords.feature.home.presentation.*
import org.assertj.core.api.Java6Assertions.assertThat

import org.junit.Test

class HomeIntentMapperTest {

   @Test
   fun `should map from HomeIntent LOAD Initial to HomeAction`(){
       val expected = HomeAction.Load

       val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.START))

       assertThat(result).usingRecursiveComparison().isEqualTo(expected)
   }

    @Test
    fun `should map from HomeIntent CommonIntent INITIAL to HomeAction`(){
        val expected = HomeAction.CommonAction(HomeAction.INITIAL)

        val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.INITIAL))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent CommonIntent ANSWER_RESULT to HomeAction`(){
        val expected = HomeAction.CommonAction(HomeAction.ANSWER_RESULT)

        val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.START))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent CommonIntent NEXT to HomeAction`(){
        val expected = HomeAction.CommonAction(HomeAction.NEXT)

        val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.START))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent CommonIntent FINISH to HomeAction`(){
        val expected = HomeAction.CommonAction(HomeAction.FINISH)

        val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.START))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent CommonIntent GAME_RESULT to HomeAction`(){
        val expected = HomeAction.CommonAction(HomeAction.GAME_RESULT)

        val result = HomeIntentMapper(HomeIntent.CommonIntent(HomeIntent.FINISH))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent SelectLevel GAME_RESULT to HomeAction`(){
        val expected = HomeAction.SelectLevel(levelId = 1)

        val result = HomeIntentMapper(HomeIntent.SelectLevelIntent(levelId = 1))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from HomeIntent SelectAnswerIntent GAME_RESULT to HomeAction`(){
        val expected = HomeAction.SelectAnswer(option = 1)

        val result = HomeIntentMapper(HomeIntent.SelectAnswerIntent(option = 1))

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }


}