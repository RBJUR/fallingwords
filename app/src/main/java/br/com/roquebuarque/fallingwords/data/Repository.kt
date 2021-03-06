package br.com.roquebuarque.fallingwords.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

class Repository {

    fun fetchWords() =
        Observable.just(createMock())

    private fun createMock() =
        Gson().fromJson<List<WordResponse>>(WordJSON.response, (object : TypeToken<List<WordResponse>>() {}.type))

}