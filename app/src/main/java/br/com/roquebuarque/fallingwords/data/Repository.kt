package br.com.roquebuarque.fallingwords.data

import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor() {

    fun fetchWords() =
        Observable.just(createMock())

    private fun createMock() =
        listOf(
            WordResponse(eng = "abc", spa = "def"),
            WordResponse(eng = "ghi", spa = "jkl"),
            WordResponse(eng = "mno", spa = "pqr"),
            WordResponse(eng = "hallo", spa = "hello"),
            WordResponse(eng = "house", spa = "haus")
        )

}