package br.com.roquebuarque.fallingwords.data

import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor() {

    fun fetchWords() =
        Observable.just(createMock())

    private fun createMock() =
        listOf(
            WordResponse(eng = "abc", spa = "def", isRight = true),
            WordResponse(eng = "ghi", spa = "jkl", isRight = true),
            WordResponse(eng = "mno", spa = "pqr", isRight = false),
            WordResponse(eng = "hallo", spa = "hello", isRight = false),
            WordResponse(eng = "house", spa = "haus", isRight = true)
        )

}