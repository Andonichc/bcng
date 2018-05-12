package com.andonichc.bcng.domain


interface Zipper <in FROM1, in FROM2, out TO> {
    fun zip(from1: FROM1, from2: FROM2): TO
}