package com.andonichc.bcng.data.datasource

import com.andonichc.bcng.data.dao.FavoriteDao
import com.andonichc.bcng.data.mapper.FavoriteLocalMapper
import com.andonichc.bcng.data.mapper.FavoriteModelMapper
import com.andonichc.bcng.domain.model.FavoriteModel
import com.andonichc.bcng.domain.repository.FavoriteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class FavoritesLocalDataSource
@Inject constructor(private val favoriteDao: FavoriteDao,
                    private val localMapper: FavoriteLocalMapper,
                    private val mapper: FavoriteModelMapper) : FavoriteRepository {

    override fun getFavorites(): Single<List<FavoriteModel>> =
            favoriteDao.getFavorites()
                    .map(localMapper::map)

    override fun addFavorite(favorite: FavoriteModel): Completable =
            Completable.fromAction {
                favoriteDao.insertFavorite(mapper.map(favorite))
            }

    override fun deleteFavorite(favorite: FavoriteModel): Completable =
        Completable.fromAction {
            favoriteDao.deleteFavorite(mapper.map(favorite))
        }

}