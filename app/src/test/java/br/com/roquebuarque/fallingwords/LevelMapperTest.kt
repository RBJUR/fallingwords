package br.com.roquebuarque.fallingwords

import br.com.roquebuarque.fallingwords.feature.home.domain.LevelMapper
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult
import br.com.roquebuarque.fallingwords.feature.home.presentation.IntentKey
import org.assertj.core.api.Java6Assertions.assertThat

import org.junit.Test

class LevelMapperTest {

   @Test
   fun `should map from IntentKey EASY to HomeResult`(){
       val expected = HomeResult.Success(
           type = HomeResult.RUNNING_GAME,
           time = 16,
           size = 4)

       val result = LevelMapper(IntentKey.EASY)

       assertThat(result).usingRecursiveComparison().isEqualTo(expected)
   }

    @Test
    fun `should map from IntentKey MEDIUM to HomeResult`(){
        val expected = HomeResult.Success(
            type = HomeResult.RUNNING_GAME,
            time = 11,
            size = 2)

        val result = LevelMapper(IntentKey.MEDIUM)

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should map from IntentKey HARD to HomeResult`(){
        val expected = HomeResult.Success(
            type = HomeResult.RUNNING_GAME,
            time = 6,
            size = 1)

        val result = LevelMapper(IntentKey.HARD)

        assertThat(result).usingRecursiveComparison().isEqualTo(expected)
    }

}