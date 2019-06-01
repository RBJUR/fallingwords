package br.com.roquebuarque.fallingwords.data

import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor() {

    fun fetchWords() =
        Observable.just(listOf(WordResponse(eng = "abc", spa = "def")))

}