package com.andonichc.bcng.presentation.model

data class StationPresentationModel(val id: Int,
                                    val type: Int,
                                    val latitude: Double,
                                    val longitude: Double,
                                    val name: String,
                                    val slots: String,
                                    val bikes: String,
                                    val operative: Boolean,
                                    val status: Int,
                                    val distance: String,
                                    val favoriteId: Int,
                                    val favoriteIcon: String)