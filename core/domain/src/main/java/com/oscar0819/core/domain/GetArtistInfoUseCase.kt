package com.oscar0819.core.domain

import com.oscar0819.core.data.repo.LookupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetArtistInfoUseCase @Inject constructor(
    private val lookupRepository: LookupRepository
){
    operator fun invoke(artistId: Int): Flow<> = combine() {

    }
}