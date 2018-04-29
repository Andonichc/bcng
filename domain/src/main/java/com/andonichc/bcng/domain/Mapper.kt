package com.andonichc.bcng.domain


interface Mapper<in FROM, out TO> {
    fun map(from: FROM): TO
}