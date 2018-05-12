package com.andonichc.bcng.domain.usecase

import com.andonichc.bcng.domain.AppSchedulers
import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.domain.repository.FavoriteRepository
import io.reactivex.Completable
import javax.inject.Inject


class AddFavoriteUseCase
@Inject constructor(private val favoriteRepository: FavoriteRepository,
                    private val schedulers: AppSchedulers) {

    fun execute(favorite: FavoriteModel): Completable =
            favoriteRepository.addFavorite(favorite)
                    .observeOn(schedulers.main)
                    .subscribeOn(schedulers.io)

}