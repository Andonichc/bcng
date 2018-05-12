package com.andonichc.bcng.domain.usecase

import com.andonichc.bcng.domain.AppSchedulers
import com.andonichc.bcng.domain.repository.FavoriteRepository
import javax.inject.Inject


class GetFavoriteUseCase
@Inject constructor(private val favoriteRepository: FavoriteRepository,
                    private val schedulers: AppSchedulers) {

    fun execute() = favoriteRepository.getFavorites()
            .observeOn(schedulers.main)
            .subscribeOn(schedulers.io)
}