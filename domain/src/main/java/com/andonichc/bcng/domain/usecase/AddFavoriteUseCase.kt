package com.andonichc.bcng.domain.usecase

import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.domain.repository.FavoriteRepository
import javax.inject.Inject


class AddFavoriteUseCase
@Inject constructor(private val favoriteRepository: FavoriteRepository){

    fun execute(favorite: FavoriteModel) {
        favoriteRepository.addFavorite(favorite)
    }
}